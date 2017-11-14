package watchdogcrypto;

import com.google.gson.JsonArray;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Gpu;
import com.profesorfalken.jsensors.model.sensors.Temperature;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

  private static final String CONFIG_FILENAME = "config.properties";
  private static String RIG_NAME = "";
  private static String API_URL = "";
  private static final OkHttpClient client = new OkHttpClient();
  private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


  public static void main(String... args) {

    System.out.println("....  |||||||||||||||  ....  ");
    System.out.println("....  Watchdog Crypto  ....  ");
    System.out.println("....  |||||||||||||||  ....  ");
    System.out.println();
    load_configuration(CONFIG_FILENAME);

    Timer timer = new Timer();
    timer.schedule(new TheLoop(), 0, 10000); //10 seconds
  }

  static class TheLoop extends TimerTask {
    public void run() {
      JsonArray tempsJsonArray = new JsonArray();
      tempsJsonArray.add(RIG_NAME);

      //Get the temperature data into tempsJsonArray
      Components components = JSensors.get.components();
      List<Gpu> gpus = components.gpus;
      if (gpus == null) {
        System.out.println("error: could not get installed GPUs. try to run as admin ?");
        return;
      }
      for (final Gpu gpu : gpus) {
        if (gpu.sensors == null) {
          System.out.println("error: could not get the gpu.sensors values for " + gpu.name + ". try to run as admin ?");
          break;
        }
        List<Temperature> temps = gpu.sensors.temperatures;
        for (final Temperature temp : temps) {
          tempsJsonArray.add(temp.value); //values are in Celsius
        }
      }

      RequestBody body = RequestBody.create(JSON, tempsJsonArray.toString());

      Request request = new Request.Builder()
          .url(API_URL)
          .post(body)
          .build();

      client.newCall(request).enqueue(new Callback() {
        @Override public void onResponse(Response response) throws IOException {
          if (response.isSuccessful()) {
            System.out.println(tempsJsonArray + " > sent successfully");
          } else {
            System.out.println("Ops! failed to get code 200 from the server.");
          }
        }

        @Override public void onFailure(Request request, IOException e) {
          System.out.println("error: failed to send the data to the server. is the server running and correctly configured ?");
        }
      });
    }
  }

  //Load the configuration into those 2 constants...
  private static void load_configuration(String configFilename) {
    try {
      Properties prop = new Properties();

      //for testing use this one.
      //InputStream input = Main.class.getClassLoader().getResourceAsStream(configFilename);

      //the config file must be in the same place with the jar file.
      InputStream input = new FileInputStream(configFilename);
      prop.load(input);
      RIG_NAME = prop.getProperty("RIG_NAME");
      API_URL = prop.getProperty("API_URL");
      System.out.println("Configuration -> " + RIG_NAME + " for " + API_URL);
      System.out.println();
      input.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
