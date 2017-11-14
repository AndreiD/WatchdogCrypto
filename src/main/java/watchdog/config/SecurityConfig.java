package watchdog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@EnableWebSecurity
@ComponentScan("watchdog.config")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AccessDeniedHandler accessDeniedHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/get_stats").access("hasIpAddress(' your ip here ')") //TODO: YOU HAVE TO MODIFY THIS!
        .antMatchers("/").access("hasIpAddress(' your ip here ')") //TODO: YOU HAVE TO MODIFY THIS!
        .and()
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
  }
}