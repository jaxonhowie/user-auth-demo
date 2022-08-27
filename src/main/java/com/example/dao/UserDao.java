package com.example.dao;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.model.User;
import com.example.utils.EncryptUtil;
import com.example.utils.TokenUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * desc:
 * author: Hongyi Zheng
 * date: 2022/8/25
 */
public class UserDao {

    private final Map<String, User> userMap = new HashMap<>(4);

    private final Map<String, String> userTokenMap = new HashMap<>(4);

    public boolean ifUserExists(String username) {
        return userMap.containsKey(username);
    }

    public void insertNewUser(String username, String password) {
        User user = new User(username, password);
        userMap.put(username, user);
    }

    public void deleteUser(String username) {
        userMap.remove(username);
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public boolean checkUserInfo(User user) throws IOException {
        String username = user.getUsername();
        if (ifUserExists(username)) {
            User memUser = userMap.get(username);
            memUser.setPassword(EncryptUtil.decrypt(memUser.getPassword()));
            return Objects.equals(user, memUser);
        }
        return false;
    }

    public boolean validateToken(String token, User user) throws JWTVerificationException {
        return !TokenUtil.isExpiry(token) && Objects.equals(TokenUtil.getTokenUser(token), user);
    }

    public void invalidToken(String username) {
        userTokenMap.remove(username);
    }

    public String genToken(String username, String password) throws IOException {
        User user = new User(username, password);
        if (!checkUserInfo(user)) {
            return "";
        }
        String token = TokenUtil.genToken(user);
        userTokenMap.put(username, token);
        return token;
    }

    public Map<String, String> getUserTokenMap() {
        return userTokenMap;
    }
}
