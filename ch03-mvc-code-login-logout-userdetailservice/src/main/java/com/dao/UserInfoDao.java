package com.dao;

import com.entity.UserInfoEntity;
import org.springframework.stereotype.Repository;

/**
 * @author cj
 * @date 2019/12/30
 */

@Repository
public class UserInfoDao {
    public UserInfoEntity getUserInfoByUsername(String username){
        ////操作数据库

        return new UserInfoEntity();
    }
}
