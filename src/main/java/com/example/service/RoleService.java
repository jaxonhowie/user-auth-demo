package com.example.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.dao.RoleDao;
import com.example.dao.UserDao;
import com.example.model.Role;
import com.example.model.User;
import com.example.utils.TokenUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * desc: 角色相关业务处理
 * author: Hongyi Zheng
 * date: 2022/8/25
 */
public class RoleService {

    private final RoleDao roleDao;

    private final UserDao userDao;

    public RoleService(RoleDao roleDao, UserDao userDao) {
        this.roleDao = roleDao;
        this.userDao = userDao;
    }


    public boolean createRole(String roleName) {
        if (roleDao.ifRoleExists(roleName)) {
            return false;
        }
        roleDao.createRole(roleName);
        return true;
    }

    public boolean deleteRole(String roleName) {
        if (roleDao.ifRoleExists(roleName)) {
            roleDao.deleteRole(roleName);
            return true;
        }
        return false;
    }

    public void addRoleToUser(String username, String roleName) {
        roleDao.addRoleToUser(username, roleName);

    }

    public void invalidateToken(String token) throws JWTVerificationException {
        User user = TokenUtil.getTokenUser(token);
        String username = user.getUsername();
        if (userDao.ifUserExists(user.getUsername())) {
            userDao.invalidToken(username);
        }
    }

    public boolean checkRole(String token, String roleName) throws JWTVerificationException{
        User user = TokenUtil.getTokenUser(token);
        if (userDao.validateToken(token, user)) {
            return roleDao.ifUserContainsRole(user.getUsername(), roleName);
        }
        return false;
    }

    public String getAllRoles(String token) throws JWTVerificationException{
        User user = TokenUtil.getTokenUser(token);
        userDao.validateToken(token, user);
        return roleDao.getAllRoles(user.getUsername());
    }

    public String authenticate(String username, String password) throws IOException {
        return userDao.genToken(username, password);
    }

    public Map<String, Role> getRoleMap() {
        return roleDao.getRoleMap();
    }

    public Map<String, Set<String>> getRoleUserMap() {
        return roleDao.getRoleUserMap();
    }
}
