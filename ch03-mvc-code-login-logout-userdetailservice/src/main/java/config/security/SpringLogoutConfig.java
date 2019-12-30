package config.security;


import com.security.MyLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author cj
 * @date 2019/12/30
 */
@Configuration
@EnableWebSecurity
public class SpringLogoutConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("123"))
                .authorities("asdf")
                .and()
                .withUser("admin")
                .password(passwordEncoder().encode("123"))
                .authorities("zxcv");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // @formatter:off
        http
                .formLogin()
                .and()
                .logout()
                    //.logoutUrl()
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    //.addLogoutHandler() // 真正做登出操作,比如删除会话,cookie等操作
                    .logoutSuccessHandler(new MyLogoutSuccessHandler()) // 这个是登出成功之后的后续处理
                .and()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/admin").authenticated();
        // @formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

}
