package com.example.model;

/**
 * desc: 角色
 *
 * @author : Hongyi Zheng
 * @date : 2022/8/25
 */
public class Role {
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
