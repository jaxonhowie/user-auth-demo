package com.example.service;

import com.example.dao.UserDao;
import com.example.model.User;
import com.example.utils.EncryptUtil;

import java.util.Map;

/**
 * desc:
 * author: Hongyi Zheng
 * date: 2022/8/25
 */
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean createUser(String username, String password) {
        //用户名重复则创建失败
        if (userDao.ifUserExists(username)){
            return false;
        }
        //加密password
        String encryptPwd = EncryptUtil.encrypt(password);
        //新增用户
        userDao.insertNewUser(username, encryptPwd);
        return true;
    }

    public boolean deleteUser(String username) {
        //用户不存在则删除失败
        if (!userDao.ifUserExists(username)) {
            return false;
        }
        userDao.deleteUser(username);
        return true;
    }

    public Map<String, User> getUserMap() {
        return userDao.getUserMap();
    }

}
