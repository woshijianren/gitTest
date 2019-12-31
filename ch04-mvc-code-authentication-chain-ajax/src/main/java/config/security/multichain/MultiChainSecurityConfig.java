package config.security.multichain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author cj
 * @date 2019/12/31
 */
@Configuration
@EnableWebSecurity(debug = true)
@Order(97)
public class MultiChainSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void init(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Configuration
    @Order(98)
    public static class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{
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
            //这条安全链去掉了csrf
            http.csrf().disable()
                    .antMatcher("/foo/**")
                    .formLogin()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/admin").authenticated();
            // @formatter:on
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }
    }

    @Configuration
    @Order(99)
    public static class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("user")
                    .password(passwordEncoder().encode("123"))
                    .authorities("xxx");
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //这条链没有去掉csrf
            // @formatter:off
            http.antMatcher("/bar/**")
                    .formLogin()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/admin").authenticated();
            // @formatter:on
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }
    }
}
