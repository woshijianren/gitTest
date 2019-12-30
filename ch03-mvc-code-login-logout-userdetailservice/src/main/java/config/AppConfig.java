package config;

import config.security.SpringUserDetailServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author cj
 * @date 2019/11/25
 */

@Configuration
@ComponentScan({"com.service"})
//@Import(SpringLoginConfig.class)
//@Import(SpringLogoutConfig.class)
@Import(SpringUserDetailServiceConfig.class)
public class AppConfig {


}
