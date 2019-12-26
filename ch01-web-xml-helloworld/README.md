# 基本用法
1. 配置过滤器
2. 创建spring容器,并在此容器关联的元数据文件中进行安全相关的设置
3. 在xml元数据文件中进行安全相关的设置
## 配置过滤器
在web.xml中实现
```xml
<filter>
    <filter-name>springSecurityFilterChain</filter-name>
    <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
</filter>
<filter-mapping>
    <filter-name>springSecurityFilterChain</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

> 注意:类是DelegatingFilterProxy,名字必须是springSecurityFilterChain

## 创建spring容器
利用ContextLoaderListener完成
```xml
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>


<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>
        classpath:spring-config.xml
    </param-value>
</context-param>
```

原因是DelegatingFilterProxy过滤器会在spring容器中查找名字为`springSecurityFilterChain`
的过滤器(此名字就是DelegatingFilterProxy配置的过滤器名),以便把安全工作交给此过滤器去处理

## 基本的安全配置
在xml中进行安全配置主要是配置http与authentication-manager两块

### Http的配置
 
```xml
<security:http  >
    <security:intercept-url pattern="/" access="isAnonymous()"/>
    <security:intercept-url pattern="/home" access="isAuthenticated()"/>
    <security:form-login/>
</security:http>
``` 
isAnonymous表示不需要验证就可以访问
isAuthenticated表示验证之后才可以访问

> spring 4.0 之后配置access="ROLE_USER"这样的内容,
> 如果要与后面验证管理器配置的authorities匹配生效,需要设置use-expressions="false" 

### AuthenticationManager的设置
```xml
<security:authentication-manager>
    <security:authentication-provider>
        <security:user-service  >
            <security:user  name="admin" password="{noop}123" authorities="xxx"/>
        </security:user-service>
    </security:authentication-provider>
</security:authentication-manager>
```

这里还没有用到角色的东西,所以authorities是可以随便设置的.密码前的前缀{noop}表示不对密码进行加密操作

# 加密处理
BCryptPasswordEncoder这个加密方式有2个显著的特点
1. 有加盐(salt)处理
2. 对同样的明文加密多次结果不一样

# 作业(理解用)
1. 删掉监听器配置会出什么错误?
2. DelegatingFilterProxy的过滤器名字不设置为springSecurityFilterChain会出现什么情况
3. spring元数据文件,如何去掉security前缀?
4. 如何获取BCryptPasswordEncoder的加密结果?
5. 思考http的auto-config有什么作用?
6. use-expressions的作用是什么?
7. 登录页是如何产生的?如何研究
8. 为什么不需要反复验证
9. 如何理解配置的user的authorities的值的作用?
10. AuthenticationManager的作用?
11. authenticationProvider的作用
12. user-service作用?
13. http协议的400,404,401,403分别是什么含义?

