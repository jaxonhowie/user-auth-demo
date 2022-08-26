package com.example.dao;

import com.example.model.Role;

import java.util.*;

/**
 * desc:
 * author: Hongyi Zheng
 * date: 2022/8/25
 */
public class RoleDao {

    private final Map<String, Role> roleMap = new HashMap<>(4);

    /**
     * K - username,V - roleNames
     */
    private final Map<String, Set<String>> roleUserMap = new HashMap<>();

    public boolean ifRoleExists(String roleName) {
        return roleMap.containsKey(roleName);
    }


    public void createRole(String roleName) {
        roleMap.put(roleName, new Role(roleName));
    }

    public void deleteRole(String roleName) {
        roleMap.remove(roleName);
    }

    public void addRoleToUser(String username, String roleName) {
        //角色存在才分配
        if (ifRoleExists(roleName)) {
            if (roleUserMap.containsKey(username)) {
                Set<String> roles = roleUserMap.get(username);
                Set<String> newRoleSet = new HashSet<>(roles);
                newRoleSet.add(roleName);
                roleUserMap.put(username, newRoleSet);
            } else {
                roleUserMap.put(username, Collections.singleton(roleName));
            }
        }
    }

    public boolean ifUserContainsRole(String username, String roleName) {
        Set<String> set = roleUserMap.get(username);
        if (set != null) {
            return set.contains(roleName);
        }
        return false;
    }

    public String getAllRoles(String username) {
        Set<String> strings = roleUserMap.get(username);
        return String.join(",", strings);
    }

    public Map<String, Role> getRoleMap() {
        return roleMap;
    }

    public Map<String, Set<String>> getRoleUserMap() {
        return roleUserMap;
    }
}
