package bmv.test.com.publicapi;

import java.net.URI;
import java.net.http.HttpRequest;
import org.mockito.ArgumentMatcher;

public class HttpRequestWithUri implements ArgumentMatcher<HttpRequest> {

  private final URI uri;

  public HttpRequestWithUri(URI uri) {
    this.uri = uri;
  }

  @Override
  public boolean matches(HttpRequest httpRequest) {
    if (httpRequest == null) {
      return false;
    }
    return httpRequest.uri().toString().startsWith(this.uri.toString());
  }
}
