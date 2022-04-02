package com.flatangle.rubbishsearch;

import com.flatangle.rubbishsearch.common.WechatException;
import com.flatangle.rubbishsearch.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author DaY1zz
 * @create 2022-04-02-17:26
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    UserService userService;

    @Test
    public void testLogin() {
        try {
            Map<String, String> result = userService.login("0010bD1w3mMzhY2jaG1w3QCUtf40bD11");
            System.out.println(result);
        } catch (WechatException e) {
            e.printStackTrace();
        }
    }
}
