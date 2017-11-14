package watchdog.controller;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@SuppressWarnings("unused")
@Controller
public class AppErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

  private static final String PATH = "/error";
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @RequestMapping("/error")
  protected String error(final RedirectAttributes redirectAttributes) throws IOException {
    redirectAttributes.addFlashAttribute("error", true);
    return "/errors/not_authorized";
  }

  @RequestMapping(value = "/errors/not_authorized", method = RequestMethod.GET)
  public String not_authorized() {

    return "/errors/not_authorized";
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }
}
