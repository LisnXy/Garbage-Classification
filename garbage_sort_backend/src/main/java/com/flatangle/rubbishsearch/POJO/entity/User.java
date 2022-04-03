package com.flatangle.rubbishsearch.POJO.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author DaY1zz
 * @create 2022-03-30-14:49
 */
@Data
@TableName("user")
public class User {

    @TableId(value = "userID")
    private String userID;

    private String userName;

    private String avatar;

    public User(){

    }
    public User(String userID, String userName, String avatar) {
        this.userID = userID;
        this.userName = userName;
        this.avatar = avatar;
    }
}
