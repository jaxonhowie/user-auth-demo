package com.example.controller;

import com.example.commons.Result;
import com.example.service.UserService;

/**
 * desc: 用户信息相关接口
 *
 * @author : Hongyi Zheng
 * @date : 2022/8/25
 */
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 创建用户
     *
     * @param username 用户名
     * @param password 原始密码
     * @return 创建成功返回true, 创建失败返回false
     */
    public Result createUser(String username, String password) {
        return userService.createUser(username, password) ? success() : failed();
    }

    /**
     * 删除用户
     *
     * @param username 用户名
     * @return 删除成功返回true，用户不存在返回false
     */
    public Result deleteUser(String username) {
        return userService.deleteUser(username) ? success() : failed();
    }
}
