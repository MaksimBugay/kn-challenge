package bmv.test.com.publicapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import bmv.test.com.publicapi.dto.RateStatMO;
import bmv.test.com.publicapi.exception.UnknownSymbolsException;
import bmv.test.com.rest.RestClient;
import bmv.test.com.rest.exception.RestClientException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KuehneNagelPublicApiImplTest {

  static final String LATEST_RATE_RESPONSE_BODY =
      "{\"success\":true,\"base\":\"BTC\",\"date\":\"2022-11-19\",\"rates\":{\"USD\":16000.25}}";

  static final String RATE_HISTORY_RESPONSE_BODY =
      "{\"success\":true,\"timeseries\":true,\"base\":\"BTC\",\"start_date\":\"2022-08-23\",\"end_date\":\"2022-11-20\",\"rates\":{\"2022-08-23\":{\"USD\":21685.804348},\"2022-08-24\":{\"USD\":21000.1},\"2022-08-25\":{\"USD\":21694.5},\"2022-08-26\":{\"USD\":15770.79},\"2022-08-27\":{\"USD\":19956.94}}}";

  static final Duration TIME_OUT = Duration.ofSeconds(1L);

  @Test
  void restClientExceptionTest() throws Exception {
    String base = "BTC";
    String symbols = "USD";

    HttpClient httpClientMock = mock(HttpClient.class);

    HttpResponse<Object> badStatusResponse = mock(HttpResponse.class);
    when(badStatusResponse.statusCode()).thenReturn(308);
    when(badStatusResponse.body())
        .thenReturn("Redirecting to https://api.exchangerate.host/latest?base=BTC&symbols=USD");
    when(httpClientMock.send(
        argThat(new HttpRequestWithUri(new URI("https://api.exchangerate.host/latest"))),
        any()))
        .thenReturn(badStatusResponse);

    RestClient restClient = new RestClient(TIME_OUT, httpClientMock);
    KuehneNagelPublicApiImpl publicApi = new KuehneNagelPublicApiImpl(restClient);

    Assertions
        .assertThrowsExactly(RestClientException.class,
            () -> publicApi.getRateStatistic(base, symbols));
  }

  @Test
  void unknownSymbolsTest() throws Exception {
    String base = "BTC";
    String symbols = "XXXXXX";

    HttpClient httpClientMock = mock(HttpClient.class);

    HttpResponse<Object> latestRateResponse = mock(HttpResponse.class);
    when(latestRateResponse.statusCode()).thenReturn(200);
    when(latestRateResponse.body()).thenReturn(LATEST_RATE_RESPONSE_BODY);
    when(httpClientMock.send(
        argThat(new HttpRequestWithUri(new URI("https://api.exchangerate.host/latest"))),
        any()))
        .thenReturn(latestRateResponse);

    RestClient restClient = new RestClient(TIME_OUT, httpClientMock);
    KuehneNagelPublicApiImpl publicApi = new KuehneNagelPublicApiImpl(restClient);

    Assertions
        .assertThrowsExactly(UnknownSymbolsException.class,
            () -> publicApi.getRateStatistic(base, symbols));
  }

  @Test
  void getRateStatisticTest() throws Exception {
    String base = "BTC";
    String symbols = "USD";

    HttpClient httpClientMock = mock(HttpClient.class);

    HttpResponse<Object> latestRateResponse = mock(HttpResponse.class);
    when(latestRateResponse.statusCode()).thenReturn(200);
    when(latestRateResponse.body()).thenReturn(LATEST_RATE_RESPONSE_BODY);
    when(httpClientMock.send(
        argThat(new HttpRequestWithUri(new URI("https://api.exchangerate.host/latest"))),
        any()))
        .thenReturn(latestRateResponse);

    HttpResponse<Object> rateHistoryResponse = mock(HttpResponse.class);
    when(rateHistoryResponse.statusCode()).thenReturn(200);
    when(rateHistoryResponse.body()).thenReturn(RATE_HISTORY_RESPONSE_BODY);
    when(httpClientMock.send(
        argThat(new HttpRequestWithUri(new URI("https://api.exchangerate.host/timeseries"))),
        any()))
        .thenReturn(rateHistoryResponse);

    RestClient restClient = new RestClient(TIME_OUT, httpClientMock);
    KuehneNagelPublicApiImpl publicApi = new KuehneNagelPublicApiImpl(restClient);
    RateStatMO rateStatistic = publicApi.getRateStatistic(base, symbols);
    Assertions.assertEquals(16000.25, rateStatistic.currentRate());
    Assertions.assertEquals(15770.79, rateStatistic.minRate());
    Assertions.assertEquals(21694.5, rateStatistic.maxRate());
  }
}
