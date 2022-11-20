package bmv.test.com.publicapi;

import static java.time.temporal.ChronoUnit.SECONDS;

import bmv.test.com.publicapi.dto.LatestRateMO;
import bmv.test.com.publicapi.dto.RateHistoryMO;
import bmv.test.com.rest.RestClient;
import bmv.test.com.util.DateUtils;
import java.time.Duration;
import java.time.LocalDateTime;

public class KuehneNagelPublicApiImpl implements PublicApi {

  public static final String GET_LATEST_RATE_URL =
      "https://api.exchangerate.host/latest?base=%s&symbols=%s";

  public static final String GET_RATE_HISTORY_URL =
      "https://api.exchangerate.host/timeseries?start_date=%s&end_date=%s&base=%s&symbols=%s";

  public static final Duration REST_CLIENT_TIMEOUT = Duration.of(10, SECONDS);

  private final RestClient restClient;

  public KuehneNagelPublicApiImpl() {
    this(null);
  }

  public KuehneNagelPublicApiImpl(RestClient restClient) {
    this.restClient = restClient == null ? new RestClient(REST_CLIENT_TIMEOUT) : restClient;
  }

  @Override
  public RateHistoryMO getRateHistory(String base, String symbols, LocalDateTime start,
      LocalDateTime end) {
    String url = String.format(GET_RATE_HISTORY_URL,
        DateUtils.toFormattedString(start),
        DateUtils.toFormattedString(end),
        base, symbols);
    return restClient.doGet(url, RateHistoryMO.class);
  }

  @Override
  public LatestRateMO getLatestRate(String base, String symbols) {
    String url = String.format(GET_LATEST_RATE_URL, base, symbols);
    return restClient.doGet(url, LatestRateMO.class);
  }
}
