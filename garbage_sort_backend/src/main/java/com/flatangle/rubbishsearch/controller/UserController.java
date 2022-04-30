package com.flatangle.rubbishsearch.controller;

import com.flatangle.rubbishsearch.POJO.params.UserLoginParams;
import com.flatangle.rubbishsearch.common.Result;
import com.flatangle.rubbishsearch.common.WechatException;
import com.flatangle.rubbishsearch.service.UserService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public Result<?> login(@RequestBody UserLoginParams userLoginParams) {

        String code = userLoginParams.getCode();
        String userName = userLoginParams.getUserName();
        String avatar = userLoginParams.getAvatar();

        try {
            Map<String, String> resultMap = userService.login(code, userName, avatar);
            return Result.success(resultMap);

        } catch (WechatException e) {
            return Result.error("-1", e.getMessage());
        }
    }

    @GetMapping("/userInfo")
    public Result<?> getUserInfo(@RequestParam String openID) {

        try {
            Map<String, Object> userInfo = userService.getUserInfo(openID);
            return Result.success(userInfo);
        } catch (WechatException e) {
            return Result.error("-1", e.getMessage());
        }

    }
}
