package com.flatangle.rubbishsearch.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.flatangle.rubbishsearch.common.WechatException;
import com.flatangle.rubbishsearch.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
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

    private RestTemplate restTemplate = new RestTemplate();

    private final String appID = "wx45b7d8662745a843";

    private final String appSecret = "f7229d3fd8abfb7caa6a6514219840d2";

    public Map<String,String> login(String code) throws WechatException {

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={code}&grant_type=authorization_code";
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("appid", appID);
        requestMap.put("secret", appSecret);
        requestMap.put("code", code);

        ResponseEntity<String> response = restTemplate.getForEntity(url,String.class,requestMap);
        JSONObject responseBody = JSONUtil.parseObj(response.getBody());
        String openid = responseBody.getStr("openid");
        String session_key = responseBody.getStr("session_key");
        Optional<Integer> errcode = Optional.ofNullable(responseBody.getInt("errcode"));

        if(errcode.isEmpty()) {
            HashMap<String,String> result = new HashMap<>();
            result.put("openid",openid);
            result.put("session_key",session_key);
            return result;
        }
        else {
            String errmsg = responseBody.getStr("errmsg");
            throw new WechatException(errmsg);
        }

    }

}
