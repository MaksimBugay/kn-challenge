package bmv.test.com.publicapi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public record RateHistoryMO(boolean success, boolean timeseries, String base,
                            @JsonProperty("start_date") String startDate,
                            @JsonProperty("end_date") String endDate,
                            Map<String, Map<String, Double>> rates) {

  @JsonCreator
  public RateHistoryMO(
      @JsonProperty("success") boolean success,
      @JsonProperty("timeseries") boolean timeseries,
      @JsonProperty("base") String base,
      @JsonProperty("start_date") String startDate,
      @JsonProperty("end_date") String endDate,
      @JsonProperty("rates") Map<String, Map<String, Double>> rates) {
    this.success = success;
    this.timeseries = timeseries;
    this.base = base;
    this.startDate = startDate;
    this.endDate = endDate;
    this.rates = rates;
  }
}
