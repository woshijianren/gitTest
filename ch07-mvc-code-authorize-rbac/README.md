# Authorities 和Role



# antMatchers配置



# 内置授权表达式


# 引用bean方法(动态授权)


# 授权架构

## AccessDecisionManager

- AffirmativeBased(默认): 一票通过
- ConsensusBased : 少数服从多数
- UnanimousBased: 一票否决

## AccessDecisionVoter

WebExpressionVoter
# 参考资料
https://docs.spring.io/spring-security/site/docs/5.2.1.RELEASE/reference/htmlsingle/#el-access
https://www.baeldung.com/role-and-privilege-for-spring-security-registration
https://www.baeldung.com/spring-security-granted-authority-vs-role
