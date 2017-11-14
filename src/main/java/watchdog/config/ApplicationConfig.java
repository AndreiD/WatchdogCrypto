package watchdog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@Configuration
@ComponentScan("watchdog.config")
@EnableAsync
@SpringBootApplication
public class ApplicationConfig {

}