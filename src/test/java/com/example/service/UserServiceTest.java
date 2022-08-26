package com.example.service;

import com.example.dao.UserDao;
import com.example.model.User;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * desc:
 * author: Hongyi Zheng
 * date: 2022/8/26
 */
class UserServiceTest {

    private String username = "Hongyi Zheng";
    private String password = "zhy123456";

    private final UserService userService = new UserService(new UserDao());

    @Test
    void createUser() {
        boolean firstCreate = userService.createUser(username, password);
        assertTrue(firstCreate, "首次创建用户失败");
        Map<String, User> userMap = userService.getUserMap();
        assertNotNull(userMap);
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            System.out.println("userMap entry |" + entry.getKey() + ":" + entry.getValue());
            assertEquals(username, entry.getValue().getUsername());
        }
        boolean duplicate = userService.createUser(username, password);
        assertFalse(duplicate, "重复创建则失败");
        assertEquals(1, userMap.size(), "如果创建失败，不应该新增用户");
    }

    @Test
    void deleteUser() {
        userService.createUser(username, password);
        boolean b = userService.deleteUser(username);
        System.out.println("删除用户成功");
        assertTrue(b);
        assertFalse(userService.deleteUser(username), "重复删除用户时不能返回成功");
        System.out.println("重复删除返回false");
    }
}