package config;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "webdrivers")
@PropertySources({
        @PropertySource(value = "classpath:browser_config.properties"),
        @PropertySource(value = "classpath:selenium.properties")
})
public class SpringTestConfig {
}
