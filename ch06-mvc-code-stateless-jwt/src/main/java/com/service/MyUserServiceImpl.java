package com.service;

import org.springframework.stereotype.Service;

/**
 * @author cj
 * @date 2020/1/3
 */

@Service
public class MyUserServiceImpl  {

    /**
     * 依据用户名加载属于此用户的所有授权角色信息.
     * @param username
     * @return
     */
    public String[] loadRolesByUsername(String username) {
        String[] result = new String[]{"xxx", "yyy"};
        return result;
    }
}
