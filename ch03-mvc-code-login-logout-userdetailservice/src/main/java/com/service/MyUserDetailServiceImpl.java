package com.service;

import com.dao.UserInfoDao;
import com.entity.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author cj
 * @date 2019/12/30
 */

@Service

public class MyUserDetailServiceImpl implements UserDetailsService {
    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoDao userInfoDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*List<GrantedAuthority> authorities =
                AuthorityUtils.createAuthorityList("xxx", "yyy");

        return new User("user", passwordEncoder.encode("123"), authorities);*/

        UserInfoEntity entity = userInfoDao.getUserInfoByUsername(username);
        if(StringUtils.isEmpty(entity)){
            throw new UsernameNotFoundException("不是合法用户名");
        }
        List<GrantedAuthority> authorities =
                AuthorityUtils.createAuthorityList("xxx", "yyy");
        return new User(entity.getUsername(),
                passwordEncoder.encode(entity.getPassword()), authorities);

    }
}
