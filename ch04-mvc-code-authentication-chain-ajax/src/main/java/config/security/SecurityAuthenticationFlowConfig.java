package config.security;

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
 * @date 2019/12/27
 */
@Configuration
@EnableWebSecurity
public class SecurityAuthenticationFlowConfig extends WebSecurityConfigurerAdapter {

    /**
     * 类似于xml中的AuthenticationManager的配置
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //@formatter:off
        auth.inMemoryAuthentication()
                .withUser("user")
                    .password(passwordEncoder().encode("123"))
                    .authorities("ROLE_USER")
            .and()
                .withUser("admin")
                    .password(passwordEncoder().encode("123"))
                    .authorities("ROLE_ADMIN");
        //@formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 这块的配置类似于xml中的http块的配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.formLogin()
            .and()
            .authorizeRequests()
                .antMatchers("/admin").authenticated()
                .antMatchers("/").anonymous();
        // @formatter:on
    }
}
