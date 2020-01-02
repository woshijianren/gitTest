package config;

import config.security.SessionFixationSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author cj
 * @date 2019/11/25
 */

@Configuration
//@ComponentScan({"com.service"})
@Import(SessionFixationSecurityConfig.class)
public class AppConfig {


}
