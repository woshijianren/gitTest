package config.security.singlechain;

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
 * @date 2019/12/31
 */
@Configuration
//debug设置为true,可以输出额外的一些调试用信息,里面就包含着当前设置的安全链
@EnableWebSecurity(debug = true)
public class SingleChainSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("123"))
                .authorities("xxx");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
       // http.csrf().disable().logout().disable().exceptionHandling().disable();
       // http.addFilter()

        // @formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
