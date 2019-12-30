

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

