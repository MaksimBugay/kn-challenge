package bmv.test.com;

import bmv.test.com.publicapi.KuehneNagelPublicApiImpl;
import bmv.test.com.publicapi.PublicApi;
import bmv.test.com.publicapi.dto.RateStatMO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) throws IOException {
    String message = "Please provide currency ";
    String currency = readLine(message);
    if (currency == null || currency.isEmpty()) {
      currency = "USD";
    }
    System.out.println("Selected currency: " + currency);
    PublicApi publicApi = new KuehneNagelPublicApiImpl();

    RateStatMO rateStatMO = publicApi.getRateStatistic("BTC", currency);
    System.out.println(rateStatMO);
  }

  private static String readLine(String message, Object... args) throws IOException {
    if (System.console() != null) {
      return System.console().readLine(message, args);
    }
    System.out.println(message);
    BufferedReader reader = new BufferedReader(new InputStreamReader(
        System.in));
    return reader.readLine();
  }
}
