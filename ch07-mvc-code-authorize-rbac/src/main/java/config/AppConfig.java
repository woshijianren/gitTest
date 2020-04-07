package config;

import config.security.DynamicRoleSecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author cj
 * @date 2019/11/25
 */

@Configuration
@ComponentScan({"com.service"})
//@Import(StaticRoleSecurityConfig.class)
@Import(DynamicRoleSecurityConfig.class)
public class AppConfig {


}
