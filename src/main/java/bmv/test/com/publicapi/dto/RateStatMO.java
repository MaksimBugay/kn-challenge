package bmv.test.com.publicapi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RateStatMO(String base, String symbols, Double currentRate, Double minRate,
                         Double maxRate) {

  @JsonCreator
  public RateStatMO(
      @JsonProperty("base") String base,
      @JsonProperty("symbols") String symbols,
      @JsonProperty("currentRate") Double currentRate,
      @JsonProperty("minRate") Double minRate,
      @JsonProperty("maxRate") Double maxRate) {
    this.base = base;
    this.symbols = symbols;
    this.currentRate = currentRate;
    this.minRate = minRate;
    this.maxRate = maxRate;
  }
}
