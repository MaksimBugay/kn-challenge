package bmv.test.com.publicapi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public record LatestRateMO(Boolean success, String base, String date,
                           Map<String, Double> rates) {

  @JsonCreator
  public LatestRateMO(
      @JsonProperty("success") Boolean success,
      @JsonProperty("base") String base,
      @JsonProperty("date") String date,
      @JsonProperty("rates") Map<String, Double> rates) {
    this.success = success;
    this.base = base;
    this.date = date;
    this.rates = rates;
  }
}
