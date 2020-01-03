package com.security;

import com.service.MyUserServiceImpl;
import com.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** 继承OncePerRequestFilter可以确保自己的过滤器只执行一次
 * @author cj
 * @date 2020/1/3
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private MyUserServiceImpl userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. 读取传递过来的token:此时按要求token是放在请求头部
        //2. Authorization: Bearer token Authorization:
        //3. jWTUtil.parse -> usern
        //4. username ->Authentication (true)
        //5 .SecurityContextHolder.getContext.setAuthentication(

        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authorizationHeader)
                && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            if (StringUtils.hasText(jwtToken)) {

                try {
                    JwtUtil jwtUtil = new JwtUtil();
                    //如果解析报错,比如过期了,直接相当于放行,
                    Claims claims = jwtUtil.parseJWT(jwtToken);
                    String username = claims.getSubject();

                    // 这里不需要传递用户名密码信息,只需要调用这个有3个参数的构造函数创建令牌,
                    // 表明其authories信息以及authenticated=true即可.
                    // 相应的此类也不是必须要利用UserDetailService来加载东西,
                    // 完全可以只需要一个依据用户名信息加载authories信息的对象即可

                    String[] authories = userService.loadRolesByUsername(username);

                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken(null,
                                    null, AuthorityUtils.createAuthorityList(authories));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        filterChain.doFilter(request, response);

    }
}
