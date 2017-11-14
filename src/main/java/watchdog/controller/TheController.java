package watchdog.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class TheController {

  int resetCounter = 0;
  HashMap<String, List<Double>> hashMapDb = new HashMap<>();

  @RequestMapping(value = "/api/upload_stats", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  protected ResponseEntity<String> uploadStats(final HttpServletRequest req, @RequestBody List<Object> payload) throws IOException {

    log.debug("PAYLOAD: " + payload.toString());

    String rigName = String.valueOf(payload.get(0));

    //TODO: remake the logic here...
    //Reset it in case are inactive rigs
    resetCounter = resetCounter + 1;
    if (resetCounter > 100) {
      resetCounter = 0;
      hashMapDb.clear();
    }

    if (payload.size() < 2) {
      log.info(">>>> do you send any temperatures ? <<<");
      return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    //skip the first one, that is the rig name
    ArrayList<Double> tempArrayList = new ArrayList<>();
    for (int i = 1; i < payload.size(); i++) {
      tempArrayList.add((Double) payload.get(i));
    }

    hashMapDb.put(rigName, tempArrayList);
    return new ResponseEntity<>("", HttpStatus.OK);
  }

  @RequestMapping(value = "/api/get_stats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  protected ResponseEntity<HashMap> getStats() {

    return new ResponseEntity<>(hashMapDb, HttpStatus.OK);
  }
}
