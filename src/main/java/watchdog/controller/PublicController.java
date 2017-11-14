package watchdog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class PublicController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  protected String publicIndex() {
    return "/public/index";
  }
}