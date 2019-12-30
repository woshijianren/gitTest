package config;

import config.security.SecurityAuthenticationFlowConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author cj
 * @date 2019/11/25
 */

@Configuration
//@ComponentScan({"com.service"})
@Import(SecurityAuthenticationFlowConfig.class)
public class AppConfig {


}
