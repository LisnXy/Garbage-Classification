package com.flatangle.rubbishsearch.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.flatangle.rubbishsearch.POJO.entity.AnswerRecord;
import com.flatangle.rubbishsearch.POJO.entity.SearchRecord;
import com.flatangle.rubbishsearch.POJO.entity.User;
import com.flatangle.rubbishsearch.common.WechatException;
import com.flatangle.rubbishsearch.mapper.AnswerRecordMapper;
import com.flatangle.rubbishsearch.mapper.SearchRecordMapper;
import com.flatangle.rubbishsearch.mapper.UserMapper;
import javafx.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

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


    /**
     * 获取用户openID、session_key，若为新用户则保存入库
     * @param code
     * @param userName
     * @param avatar
     * @return
     * @throws WechatException
     */
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
                AnswerRecord newAnswerRecord = new AnswerRecord(openid,0,0,0,0,0);
                SearchRecord newSearchRecord = new SearchRecord(openid,0,0,0,0);

                userMapper.insert(newUser);
                answerRecordMapper.insert(newAnswerRecord);
                searchRecordMapper.insert(newSearchRecord);
            }

            return result;
        }
        //错误请求返回
        else {
            String errmsg = responseBody.getStr("errmsg");
            throw new WechatException(errmsg);
        }

    }

    /**
     * 获取目标用户在用户中心所需数据
     * @param openID
     * @return
     * @throws WechatException
     */
    public Map<String,Object> getUserInfo(String openID) throws WechatException {

        Optional<User> optionalUser = Optional.ofNullable(userMapper.selectById(openID));
        Map<String, Object> result = new HashMap<>();
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<AnswerRecord> optionalAnswerRecord = Optional.ofNullable(answerRecordMapper.selectById(openID));
            Optional<SearchRecord> optionalSearchRecord = Optional.ofNullable(searchRecordMapper.selectById(openID));

            result.put("user",user);

            Map<String, Object> recordInfo = getRecordInfo(optionalAnswerRecord.get(), optionalSearchRecord.get());
            result.put("recordInfo",recordInfo);


            double targetScore = getRankScore(optionalAnswerRecord.get(), optionalSearchRecord.get());
            double surPassPercent = getSurPassPercent(openID, targetScore);
            result.put("surpassPercent",surPassPercent);

            optionalAnswerRecord.ifPresent(o -> {
                result.put("score",o.getScore());
            });

            return result;
        }
        else{
            throw new WechatException("用户不存在");     //基本不可能
        }

    }

    /**
     * 根据答题得分和查询数计算得到用户加权得分
     * @param answerRecord
     * @param searchRecord
     */
    private double getRankScore(AnswerRecord answerRecord, SearchRecord searchRecord) {
        int answerScore = 0;
        int searchScore = 0;
        if(answerRecord != null) {
            answerScore = answerRecord.getScore();
        }
        if(searchRecord != null) {
            searchScore = (searchRecord.getRecycleCount()+searchRecord.getHarmfulCount()+
                    searchRecord.getKitchenCount()+searchRecord.getOtherCount()) * 10;
        }

        return ( answerScore * 0.7 ) + ( searchScore * 0.3 );
    }

    /**
     * 根据用户ID获取加权得分排名
     * @param userID
     * @return
     */
    private double getSurPassPercent(String userID, double targetScore) {
        List<AnswerRecord> answerRecords = answerRecordMapper.selectList(Wrappers.lambdaQuery());
        List<SearchRecord> searchRecords = searchRecordMapper.selectList(Wrappers.lambdaQuery());
        List<Pair<String,Double>> rankScores = new ArrayList<>(answerRecords.size());

        for(int i = 0; i < answerRecords.size(); i++) {
            double rankScore = getRankScore(answerRecords.get(i), searchRecords.get(i));
            rankScores.add(new Pair<String,Double>(answerRecords.get(i).getUserID(),rankScore));
        }

        rankScores = rankScores.stream().sorted(Comparator.comparingDouble(Pair::getValue)).collect(Collectors.toList());
        //二分查找
        int l = 0;
        int r = rankScores.size()-1;
        while(l < r) {
            int mid = l + r >> 1;
            if(rankScores.get(mid).getValue() >= targetScore) {
                r = mid;
            }
            else
                l = mid + 1;

        }
        //可能有同分的情况
        while(rankScores.get(l).getValue() == targetScore) {
            if(rankScores.get(l).getKey().equals(userID)){
                return (double)(l / (rankScores.size() - 1));   //返回超越的百分比
            }
            else
                l++;
        }

        return 0.0;
    }

    /**
     * 根据答题记录和查询记录得到
     * @param answerRecord
     * @param searchRecord
     * @return
     */
    private Map<String,Object> getRecordInfo(AnswerRecord answerRecord,SearchRecord searchRecord) {

        if(answerRecord==null && searchRecord == null)
            return null;

        double recycle = 0;
        double harmful = 0;
        double kitchen = 0;
        double other = 0;
        
        if(answerRecord != null){
            recycle += answerRecord.getRecycleFalseCount() * 0.7;
            harmful += answerRecord.getHarmfulFalseCount() * 0.7;
            kitchen += answerRecord.getKitchenFalseCount() * 0.7;
            other += answerRecord.getOtherFalseCount() * 0.7;
        }

        if(searchRecord != null){
            recycle += searchRecord.getRecycleCount() * 0.3;
            harmful += searchRecord.getHarmfulCount() * 0.3;
            kitchen += searchRecord.getKitchenCount() * 0.3;
            other += searchRecord.getOtherCount() * 0.3;
        }
        
        double sum = recycle + harmful + kitchen + other;

        double recyclePercent = new BigDecimal(100 * recycle / sum).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double harmfulPercent = new BigDecimal(100 * harmful / sum).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double kitchenPercent = new BigDecimal(100 * kitchen / sum).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double otherPercent = new BigDecimal(100 * other / sum).setScale(1, RoundingMode.HALF_UP).doubleValue();

        Map<String,Object> map = new HashMap<>();
        map.put("recyclePercent",recyclePercent);
        map.put("harmfulPercent",harmfulPercent);
        map.put("kitchenPercent",kitchenPercent);
        map.put("otherPercent",otherPercent);
        return map;


    }

}