# DelegatingFilterProxy
作用是从spring容器中找出一个其它的过滤器来干活
查找的方式是getBean("名字"),这个名字就是你配置
委托过滤器时指定的filterName

委托过滤器本质作用是把自己的工作交给一个被spring管理的过滤器来做
这样可以本来由tomcat容器管理filter的执行,变成spring容器管理
过滤器的初始化.

# FilterChainProxy
此过滤器由spring容器管理,是DelegatingFilterProxy委托到的一个过滤器
此过滤器就是被称之为springSecurityFilterChain
这个过滤器管理着所有的spring安全过滤器链.


# HttpSecurity构建器
此构建器的配置,是用来配置某一个安全链的,配置的方式主要有两种方式
- 配置器:调用csrf和formLogin等方法返回的都是一个个的配置器
- addFilter方法

比如:http.csrf.disable() 就表示从过滤链中移除进行csrf出来的一个过滤器
http.formLogin().loginPage():表示:
1. 往过滤链里面添加了至少一个过滤器:UsernamePasswordAuthenticationFilter
2. 调整了UsernamePasswordAuthenticationFilter的登录url值

> 有些配置器进行配置时会影响其它配置器的一些设定,
>比如对登录配置器进行配置时会影响异常配置器对AuthenticationEntryPoint的设置

## 单条安全链的配置
安全链的配置,主要是靠一系列的配置器的配置以及直接调用HttpSecurity对象的addFilter开头相关方法来处理的
1. 通过调用disable方法可以移除某些安全链的过滤器,比如http.csrf.disable()
2. 通过调用addFilter开头的4个方法,可以直接往安全链中添加过滤器
    调用addFilter和addFilterAt方法时,添加的过滤器要求是spring security提供的过滤器或是其子类,
    因为这2个方法是用来添加Spring Security提供的过滤链中的过滤器及其子类的,添加的时候不是替换,是增加.
    而其它2个方法就没有这个要求.
见案例中的singlechain包下的内容
## 多条安全链的配置
多条链主要实现步骤是:
1. 创建一个配置类(***可以不用加Configuration注解***),继承WebSecurityConfigurerAdapter
2. 在此类上添加Order注解,控制多个WebSecurityConfigurerAdapter类型的顺序
3. 在对HttpSecurity进行配置时,调用其antMatcher方法设置链能处理的地址模式
4. 已经在其它配置类添加了EnableWebSecurity注解或者在上面2步中建立的配置类上添加EnableWebSecurity注解

见multichain包
做法一:
```java

@Configuration
@EnableWebSecurity(debug = true)
@Order(99)
public class AChainSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/bar/**");
    }
}


@Configuration
@EnableWebSecurity(debug = true)
@Order(99)
public class BChainSecurityConfig extends WebSecurityConfigurerAdapter {
      @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/foo/**");
        }
}
```

第二种方法:利用内部类的形式:
```java
@Configuration

public class MultiChainSecurityConfig {


    @Order(98)
    @Configuration
    @EnableWebSecurity(debug = true)
    public static class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/foo/**");
        }

    }
    @Configuration
    @EnableWebSecurity(debug = true)
    @Order(99)
    public static class  MobileSecurityConfig extends WebSecurityConfigurerAdapter{


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/bar/**");
        }
      
    }

}
```
具体的见multichain包下的2个配置类AChainSecurityConfig与BChainSecurityConfig.
# WebSecurity
此对象是一个构建器(builder),它是用来配置FilterChainProxy,而此过滤器控制着所有安全链的执行,
所以对其进行配置会影响所有的安全链,它常用来配置一个项目,就是忽略静态文件,代码如下:
```java
@Override
public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/static/**");
}
```

所以:WebSecurity用来构建(build) FilterChainProxy
HttpSecurity用来构建spring security的整个安全链中的各个过滤器

# spring security的安全链

## SecurityContextPersistenceFilter
用来读取与存储SecurityContext.其作用是
1.如果能读取用到有效的SecurityContext,一般意味着
你以前验证过,就不需要重复验证
2. 验证通过之后,会把SecurityContext保存起来,默认是保存到会话中

## UsernamePasswordAuthenticationFilter
这个过滤器就是用来做验证的.主要是靠FormLogin来配置它

## ExceptionTranslationFilter

主要是处理整个安全链的异常的,重点关注AuthenticationException
和AuthorizeException.
## FilterSecurityInterceptor
这个是重中之重,它是专门处理你的验证表达式与授权表达式


## 访问一个需要验证资源的过程
访问一个需要验证的地址,如果没有登录
1. 经过SecurityContextPersistenceFilter过滤器,从会话中读取不到SecurityContext
2. 会叫FilterSecurityInterceptor做权限处理,然后因为没有验证,所以抛出异常
3. ExceptionTranslationFilter会捕获异常,就交给AuthenticationEntryPoint的commence
方法发起验证处理,默认就是跳转到登录页面
4. 登录之后交给UsernamePasswordAuthenticationFilter去验证,而验证就交给AuthenticationManager
去验证
5. FilterSecurityInterceptor验证通过
6.回到SecurityContextPersistenceFilter把SecurityContext对象保存到http session
7.下次再访问要验证的url地址,经过第一个过滤器SecurityContextPersistenceFilter
此时可以从会话读取到SecurityContext对象,所以就不需要再验证


# cors 
方法一:只在安全过滤层面进行配置,不在mvc中进行任何配置
```java
 http.cors().configurationSource(configurationSource())


private UrlBasedCorsConfigurationSource configurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://127.0.0.1:8848");

        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("HEAD");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
```

方法二: 在安全层面配置http.cors(),在mvc层面配置具体的跨域设置
```java
http.cors();
```

mvc:
```java
  
    public void addCorsMappings(CorsRegistry registry) {
     /*   registry.addMapping("/**")
                .allowedMethods("GET","POST","OPTIONS","HEAD","DELETE","PUT")
                .allowedOrigins("http://127.0.0.1:8848")
                .allowCredentials(true);*/
    }
```

这种配置的原理是spring security在容器中找跨域相关的bean
找不到会报错,所以这种配置能生效的前提是spring security相关配置
所在的spring容器和spring mvc所在的spring容器是同一个容器或者
spring security的spring容器是mvc的spring容器的子容器

# 术语简介
委托过滤器(DelegatingFilterProxy)
过滤链代理(FilterChainProxy)
构建器(builder)比如HttpSecurity,WebSecurity,AuthenticationManagerBuilder
配置器(configurer) CsrfConfigurer
构建器构建的目标对象,有:filter,authenticationManager


# 作业
1. 如何查看当前配置所使用的过滤链
debug=true

2. 配置http.csrf.disable(),观察过滤链的情况


3. 配置http.csrf.disable().and().csrf(),请问是否添加了csrf的过滤器

4.配置http.formLogin().loginPage().and().formLogin().defaultSuccessUrl()
请问这里配置了2个UsernamePasswordAuthenticationFilter还是一个?
如果是一个,那么是否把loginPage与defaultSuccessUrl都配置到了同一个UsernamePasswordAuthenticationFilter里

5. 自己写一个简单的过滤器,添加到安全链中
提示:addFilterBefore与addFilterAfter()

自己写的过滤器,调用chain.doFilter是什么情况,不调用又会是什么情况?


6. http.authorizeRequest().antMatchers("/admin").authenticated()
.antMatchers("/admin").permitAll();
请求访问/admin地址到底是permitAll这种情况还是authenticated的情况


----------------------------------------

7.实战作业:做一个校验码的过滤器,并添加到安全链中


8. 配置多条链出来.
一条链是在浏览器登录,支持重定向
一条链是在rest客户端可以登录,所有的响应都是json数据


