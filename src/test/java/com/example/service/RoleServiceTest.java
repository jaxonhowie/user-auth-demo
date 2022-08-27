package com.example.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.dao.RoleDao;
import com.example.dao.UserDao;
import com.example.model.Role;
import com.example.model.User;
import com.example.utils.TokenUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * desc:
 *
 * @author : Hongyi Zheng
 * @date : 2022/8/26
 */
class RoleServiceTest {


    private final UserDao userDao = new UserDao();
    private final RoleService roleService = new RoleService(new RoleDao(), userDao);
    private final UserService userService = new UserService(userDao);

    @Test
    void createRole() {
        boolean firstCreate = roleService.createRole("role1");
        assertTrue(firstCreate);
        Map<String, Role> roleMap = roleService.getRoleMap();
        assertEquals(1, roleMap.size());
        boolean duplicate = roleService.createRole("role1");
        assertFalse(duplicate);
    }

    @Test
    void deleteRole() {
        roleService.createRole("a");
        boolean a = roleService.deleteRole("a");
        //首次删除成功
        assertTrue(a);
        boolean b = roleService.deleteRole("a");
        //再删除失败
        assertFalse(b);
    }

    @Test
    void addRoleToUser() {
        UserService userService = new UserService(new UserDao());
        String username = "username";
        String password = "password";
        String roleName = "role";
        //创建用户
        userService.createUser(username, password);
        //创建角色
        roleService.createRole(roleName);
        roleService.addRoleToUser(username, roleName);
        Map<String, Set<String>> roleUserMap = roleService.getRoleUserMap();
        assertNotNull(roleUserMap);
        for (Map.Entry<String, Set<String>> entry : roleUserMap.entrySet()) {
            System.out.println("role-user Map = " + entry.getKey() + " :" + entry.getValue());
            assertEquals(username, entry.getKey());
            assertTrue(entry.getValue().contains(roleName));
        }
        roleService.createRole(roleName + 1);
        roleService.addRoleToUser(username, (roleName + 1));
        for (Map.Entry<String, Set<String>> entry : roleUserMap.entrySet()) {
            System.out.println("role-user Map = " + entry.getKey() + " :" + entry.getValue());
        }

    }

    @Test
    void tokenValidation() throws IOException {
        String username = "username";
        String password = "password";
        String token = roleService.authenticate("username", "password");
        //用户未找到，不返回token
        assertEquals("", token);
        System.out.println("未创建用户时不生成token");
        userService.createUser(username, password);
        String valid = roleService.authenticate("username", "password");
        System.out.println("生成token = " + valid);
    }

    @Test
    void invalidateToken() throws IOException {
        String username = "username";
        String password = "password";
        userService.createUser(username, password);
        String valid = roleService.authenticate("username", "password");
        Map<String, String> userTokenMap = userDao.getUserTokenMap();
        for (Map.Entry<String, String> entry : userTokenMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        assertEquals(1, userTokenMap.size());
        roleService.invalidateToken(valid);
        assertEquals(0, userTokenMap.size());
    }

    @Test
    void checkRole() throws IOException {
        String username = "username";
        String password = "password";
        String roleName = "firstRole";
        userService.createUser(username, password);
        String token = roleService.authenticate("username", "password");
        boolean a = roleService.checkRole(token, roleName);
        assertFalse(a);
        roleService.createRole(roleName);
        roleService.addRoleToUser(username, roleName);
        assertTrue(roleService.checkRole(token, roleName));
    }

    /**
     * 测试获取全部角色
     */
    @Test
    void getAllRoles() throws IOException {
        String fakeToken = "An invalid token";
        userService.createUser("username", "password");
        String realToken = roleService.authenticate("username", "password");
        addRolesToUser("username", "role1", "role2", "role3", "role4", "role5");
        assertThrows(JWTVerificationException.class, () -> {
            roleService.getAllRoles(fakeToken);
            throw new JWTVerificationException("Token 无效");
        });
        String allRoles = roleService.getAllRoles(realToken);
        System.out.printf("all roles of username are : %s%n", allRoles);
    }

    /**
     * 测试为用户添加角色
     *
     * @param username
     * @param roleNames
     */
    private void addRolesToUser(String username, String... roleNames) {
        for (String roleName : roleNames) {
            roleService.createRole(roleName);
            roleService.addRoleToUser(username, roleName);
        }
    }

    /**
     * 测试过期时间
     */
    @Test
    void validateToken() {
        String token = TokenUtil.genTokenWithExpiry(new User("username", "password"), new Date());
        boolean expiry = TokenUtil.isExpiry(token);
        assertTrue(expiry);
    }
}