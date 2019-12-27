
# 配置委托过滤器
```java
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}
```

# 安全配置
1. 创建一个配置类
2. 让spring 容器识别此配置,建议用import的方式
3. 在安全配置类上添加EnableWebSecurity注解
4. 让配置类继承WebSecurityConfigurerAdapter
5. 重写2个方法
    5.1 一个用来配置验证管理器
    5.2 一个用来配置http
6. 配置一个密码加密的东西

```java
@Configuration
@EnableWebSecurity
public class SecurityConfigHello extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                    .withUser("user")
                    .password(passwordEncoder().encode("123"))
                    .authorities("ROLE_USER");       
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
            .and()
            .authorizeRequests()
                .antMatchers("/admin").authenticated()
                .antMatchers("/").permitAll()
      ;
    }
}
```    

作业:
1.让user角色可以访问/user地址
2.让admin角色可以访问/admin地址