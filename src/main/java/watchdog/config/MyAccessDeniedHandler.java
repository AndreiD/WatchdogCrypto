package watchdog.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      AccessDeniedException e) throws IOException, ServletException {

    log.debug(">>>>>> MyAccessDeniedHandler <<<<<<");

    Authentication auth
        = SecurityContextHolder.getContext().getAuthentication();

    //TODO: improve this!
    if (auth != null) {
      log.info("User '" + auth.getName()
          + "' attempted to access the protected URL: "
          + httpServletRequest.getRequestURI());
    }

    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/errors/not_authorized");
  }
}