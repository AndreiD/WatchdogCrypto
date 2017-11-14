package watchdog;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {

  public static void main(String[] args) throws IOException {

    SpringApplication.run(Application.class, args);

    log.info(":::::::::::::::::::: Spring " + org.springframework.core.SpringVersion.getVersion() + " ::::::::::::::::::::");
    log.info(":::::::::::::::::::: Running on http://server_ip:9999 ::::::::::::::::::::");
    log.info(":::::::::::::::::::: Running on http://server_ip:9999/api/get_stats ::::::::::::::::::::");
  }
}
