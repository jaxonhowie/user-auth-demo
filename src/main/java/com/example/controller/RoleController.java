package com.example.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.commons.Result;
import com.example.service.RoleService;

import java.io.IOException;

/**
 * desc: 用户角色相关接口
 *
 * @author : Hongyi Zheng
 * @date : 2022/8/26
 */
public class RoleController extends BaseController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 创建角色
     *
     * @param roleName 角色名称
     * @return 如果角色已存在返回failed
     */
    public Result createRole(String roleName) {
        return roleService.createRole(roleName) ? success() : failed();
    }

    /**
     * 删除角色
     *
     * @param roleName 角色名
     * @return 删除成功返回true 删除角色不存在返回failed
     */
    public Result deleteRole(String roleName) {
        return roleService.deleteRole(roleName) ? success() : failed();
    }

    /**
     * 为用户添加角色
     *
     * @param username 用户名
     * @param roleName 角色名
     */
    public void addRoleToUser(String username, String roleName) {
        roleService.addRoleToUser(username, roleName);
    }

    /**
     * 用户授权
     *
     * @param username 用户名
     * @param password 密码
     * @return 授权成功返回令牌，用户不存在返回error
     */
    public Result authenticate(String username, String password) {
        String token;
        try {
            token = roleService.authenticate(username, password);
        } catch (IOException e) {
            return error();
        }
        return "".equals(token) ? error() : success(token);
    }

    /**
     * token作废，作废成功则token失效，token无效则输出当前token
     *
     * @param token auth token
     */
    public void invalidate(String token) {
        try {
            roleService.invalidateToken(token);
        } catch (JWTVerificationException e) {
            System.out.printf("Invalid token :%s%n", token);
        }
    }

    /**
     * 校验用户是否拥有此角色
     *
     * @param token    令牌
     * @param roleName 角色名称
     * @return 用户已授权角色返回true，否则返回false，token无效返回error
     */
    public Result checkRole(String token, String roleName) {
        try {
            return roleService.checkRole(token, roleName) ? success() : failed();
        } catch (JWTVerificationException e) {
            System.out.printf("Invalid token :%s%n", token);
            return error();
        }
    }

    /**
     * 获取用户所有角色
     *
     * @param token 令牌
     * @return 返回用户的所有角色
     */
    public Result getAllRoles(String token) {
        try {
            return success(roleService.getAllRoles(token));
        } catch (JWTVerificationException e) {
            return error();
        }
    }
}
