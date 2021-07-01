package si.asovic;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value = "si.asovic.backend.data.repository")
@PropertySource("classpath:application.properties")
public class NFConfig {

}
