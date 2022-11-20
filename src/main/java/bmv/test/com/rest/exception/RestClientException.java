package bmv.test.com.rest.exception;

public class RestClientException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Failed rest communication attempt: url %s";

  private static final String BAD_RESPONSE_STATUS_MESSAGE =
      "Response status is not 200: url %s, status %d";

  private final String url;

  private final String response;

  public RestClientException(String url, int responseStatusCode, String response) {
    super(getBadResponseStatusErrorMessage(url, responseStatusCode));
    this.url = url;
    this.response = response;
  }

  public RestClientException(String url, Throwable cause) {
    super(getErrorMessage(url), cause);
    this.url = url;
    this.response = null;
  }

  public String getUrl() {
    return url;
  }

  public String getResponse() {
    return response;
  }

  private static String getErrorMessage(String url) {
    return String.format(ERROR_MESSAGE, url);
  }

  private static String getBadResponseStatusErrorMessage(String url, int responseStatus) {
    return String.format(BAD_RESPONSE_STATUS_MESSAGE, url, responseStatus);
  }
}
