package com.flatangle.rubbishsearch.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.flatangle.rubbishsearch.POJO.entity.AnswerRecord;
import com.flatangle.rubbishsearch.POJO.entity.SearchRecord;
import com.flatangle.rubbishsearch.POJO.entity.User;
import com.flatangle.rubbishsearch.common.WechatException;
import com.flatangle.rubbishsearch.mapper.AnswerRecordMapper;
import com.flatangle.rubbishsearch.mapper.SearchRecordMapper;
import com.flatangle.rubbishsearch.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author DaY1zz
 * @create 2022-04-02-16:54
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AnswerRecordMapper answerRecordMapper;

    @Resource
    private SearchRecordMapper searchRecordMapper;

    private RestTemplate restTemplate = new RestTemplate();

    private final String appID = "wx45b7d8662745a843";

    private final String appSecret = "f7229d3fd8abfb7caa6a6514219840d2";

    public Map<String,String> login(String code, String userName, String avatar) throws WechatException {

        //参数准备
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={code}&grant_type=authorization_code";
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("appid", appID);
        requestMap.put("secret", appSecret);
        requestMap.put("code", code);

        //给微信服务器发送请求，解析响应
        ResponseEntity<String> response = restTemplate.getForEntity(url,String.class,requestMap);
        JSONObject responseBody = JSONUtil.parseObj(response.getBody());
        String openid = responseBody.getStr("openid");
        String session_key = responseBody.getStr("session_key");
        Optional<Integer> errcode = Optional.ofNullable(responseBody.getInt("errcode"));

        //正确请求返回
        if(errcode.isEmpty()) {
            HashMap<String,String> result = new HashMap<>();
            result.put("openid",openid);
            result.put("session_key",session_key);

            Optional<User> user = Optional.ofNullable(userMapper.selectById(openid));
            if(user.isEmpty()) {    //如果为新用户，则保存openID、用户名、头像入库

                User newUser = new User(openid,userName,avatar);
                userMapper.insert(newUser);
            }

            return result;
        }
        //错误请求返回
        else {
            String errmsg = responseBody.getStr("errmsg");
            throw new WechatException(errmsg);
        }

    }

    public Map<String,Object> getUserInfo(String openID) throws WechatException {

        Optional<User> optionalUser = Optional.ofNullable(userMapper.selectById(openID));
        Map<String, Object> result = new HashMap<>();
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            result.put("user",user);

            Optional<AnswerRecord> optionalAnswerRecord = Optional.ofNullable(answerRecordMapper.selectById(openID));
            optionalAnswerRecord.ifPresent(o -> {
                Map<String, Object> answerRecordInfo = getAnswerRecordInfo(o);
                result.put("answerRecordInfo",answerRecordInfo);
            });

            return result;
        }
        else{
            throw new WechatException("用户不存在");     //基本不可能
        }

    }

    private Map<String,Object> getAnswerRecordInfo(AnswerRecord answerRecord) {

        double recycleFalseCount = (double) answerRecord.getRecycleFalseCount();
        double harmfulFalseCount = (double) answerRecord.getHarmfulFalseCount();
        double kitchenFalseCount = (double) answerRecord.getKitchenFalseCount();
        double otherFalseCount = (double) answerRecord.getOtherFalseCount();

        double sum = recycleFalseCount + harmfulFalseCount + kitchenFalseCount + otherFalseCount;

        double recyclePercent = new BigDecimal(100 * recycleFalseCount / sum).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double harmfulPercent = new BigDecimal(100 * harmfulFalseCount / sum).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double kitchenPercent = new BigDecimal(100 * kitchenFalseCount / sum).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double otherPercent = new BigDecimal(100 * otherFalseCount / sum).setScale(1, RoundingMode.HALF_UP).doubleValue();

        Map<String,Object> map = new HashMap<>();
        map.put("recyclePercent",recyclePercent);
        map.put("harmfulPercent",harmfulPercent);
        map.put("kitchenPercent",kitchenPercent);
        map.put("otherPercent",otherPercent);
        map.put("score",answerRecord.getScore());

        return map;


    }

}
