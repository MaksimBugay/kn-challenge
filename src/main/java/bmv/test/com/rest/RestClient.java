package bmv.test.com.rest;

import bmv.test.com.rest.exception.RestClientException;
import bmv.test.com.rest.serialization.JsonSerializer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public class RestClient {

  public static final int SUCCESS_STATUS_CODE = 200;

  private final Duration timeOut;

  private final HttpClient httpClient;

  private final JsonSerializer jsonSerializer = new JsonSerializer();

  public RestClient(Duration timeOut) {
    this(timeOut, null);
  }

  public RestClient(Duration timeOut, HttpClient httpClient) {
    this.timeOut = timeOut;
    this.httpClient = httpClient == null ? HttpClient.newHttpClient() : httpClient;
  }

  public <T> T doGet(String url, Class<T> responseType) {
    T result;
    int responseStatusCode;
    String responseBody;
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(new URI(url))
          .version(HttpClient.Version.HTTP_2)
          .timeout(timeOut)
          .GET()
          .build();
      HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
      responseStatusCode = response.statusCode();
      responseBody = response.body();
      if (SUCCESS_STATUS_CODE == responseStatusCode) {
        result = jsonSerializer.fromJson(responseBody, responseType);
        return result;
      }
    } catch (Exception ex) {
      throw new RestClientException(url, ex);
    }
    throw new RestClientException(url, responseStatusCode, responseBody);
  }
}
