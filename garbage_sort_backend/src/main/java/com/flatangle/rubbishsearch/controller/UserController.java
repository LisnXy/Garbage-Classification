package com.flatangle.rubbishsearch.controller;

import com.flatangle.rubbishsearch.common.Result;
import com.flatangle.rubbishsearch.common.WechatException;
import com.flatangle.rubbishsearch.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author DaY1zz
 * @create 2022-04-02-16:49
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping
    public Result<?> login(@RequestBody Map<String,Object> requestBody) {

        String code = (String) requestBody.get("code");

        try {
            Map<String, String> resultMap = userService.login(code);
            return Result.success(resultMap);

        } catch (WechatException e) {
            return Result.error("-1",e.getMessage());
        }
    }


}
