# Authorities 和Role
Authority:可以干的事情,比如删除文件,创建文件夹

Role:在spring security里面  ========= Authority
但是实际使用的时候,认为把两者区分:
Role是权限的集合(Authority的集合)
1. 管理员有:删除文件,创建文件夹的权限
2. 认为的认为角色时包含多个用户

在spring security里面配置role的时候需要有ROLE前缀

1. 如果你用Authorities方法来配置角色,那么必须加上ROLE前缀,比如:Authorities("ROLE_ADMIN")
2. 如果你用的方法包含Role这个词汇,你就不需要添加Role前缀,由Spring帮你自动添加,比如hasRole("ADMIN")
# antMatchers配置
比如:
http.authorizeRequest()
    .antMatchers("/api/admin").permitAll() //可以访问
    .antMatchers("xxxxxx").authenticated()
    .antMatchers("/api/admin").denyAll()  :denyAll是拒绝访问的意思
/api/admin到底是可以访问还是不可以访问
如果配置的地址字符串重复,以后面的为准,上面的案例就是denyAll()

下面的结果跟上面一样
http.authorizeRequest()
    .antMatchers("/api/xxx","/api/admin").permitAll() //可以访问
    .antMatchers("xxxxxx").authenticated()
    .antMatchers("/api/yyy","/api/admin").denyAll() 
 
 2 第二种情况   
http.authorizeRequest()
        .antMatchers("/api/**").permitAll() //可以访问
        .antMatchers("/api/admin").denyAll()
问题:当我访问/api/admin的时候,是什么情况
    是可以访问的情况,因为其逻辑是顺序遍历你的配置,只要找到一个就结束
    所以找到api/**的时候,看到是permitAll就结束,表示可以访问
    
    实践建议: 越具体的越要放在前面配置
    
    http.authorizeRequest()
            .antMatchers("/api/admin").permitAll() //可以访问
            .antMatchers("/api/**").denyAll()
# 内置授权表达式
内置的授权表达式就是平时的那些方法
- hasRole
- hasAnyRole
- hasAuthority
- authenticated
- isAnonymous:是否是匿名
- hasIpAddress
- 等等
# 引用bean方法(动态授权)

access("@rbac.xxx(request,authentication)");
上面的参数名必须是request,authentication,但顺序可以调整
# 授权架构

## AccessDecisionManager

- AffirmativeBased(默认): 一票通过,只要有一票认为可以访问的,那么资源就是可以访问的
- ConsensusBased : 少数服从多数
- UnanimousBased: 一票否决

## AccessDecisionVoter

WebExpressionVoter
# 参考资料
https://docs.spring.io/spring-security/site/docs/5.2.1.RELEASE/reference/htmlsingle/#el-access
https://www.baeldung.com/role-and-privilege-for-spring-security-registration
https://www.baeldung.com/spring-security-granted-authority-vs-role
