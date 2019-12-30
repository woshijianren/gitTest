package config.security;


import com.security.MyAuhtenticationFailHandler;
import com.security.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author cj
 * @date 2019/12/30
 */
@Configuration
@EnableWebSecurity
public class SpringLoginConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth.inMemoryAuthentication()
                .withUser("user")
                    .password(passwordEncoder().encode("123"))
                    .authorities("asdf")
                .and()
                .withUser("admin")
                    .password(passwordEncoder().encode("123"))
                    .authorities("zxcv");
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
               // .csrf().disable()   //在表单里添加了csrf的相关信息之后就不需要关闭csrf了
                .formLogin()
                    .loginPage("/loginview")  //这个设置是打开登录页面的url
                    .usernameParameter("uname") //默认是username
                    .passwordParameter("pwd") //默认是password
                    .loginProcessingUrl("/dologin") //配置的是登录的处理url,就是自定义表单的action的值
                    .defaultSuccessUrl("/loginsuccess")
                    .failureUrl("/loginfail")
                    .successHandler(new MyAuthenticationSuccessHandler())
                    .failureHandler(new MyAuhtenticationFailHandler())
                .and()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/admin").authenticated();
        // @formatter:on
    }

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
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
