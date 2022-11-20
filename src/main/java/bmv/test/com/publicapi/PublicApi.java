package bmv.test.com.publicapi;

import bmv.test.com.publicapi.dto.LatestRateMO;
import bmv.test.com.publicapi.dto.RateHistoryMO;
import bmv.test.com.publicapi.dto.RateStatMO;
import bmv.test.com.publicapi.exception.UnknownSymbolsException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

public interface PublicApi {

  int DEFAULT_HISTORY_WINDOW = 90;

  RateHistoryMO getRateHistory(String base, String symbols, LocalDateTime start, LocalDateTime end);

  default RateHistoryMO getRateHistory(String base, String symbols) {
    LocalDateTime end = LocalDateTime.now();
    LocalDateTime start = end.minusDays(DEFAULT_HISTORY_WINDOW);
    return getRateHistory(base, symbols, start, end);
  }

  LatestRateMO getLatestRate(String base, String symbols);

  default RateStatMO getRateStatistic(String base, String symbols) {
    Double currentRate = Optional.ofNullable(getLatestRate(base, symbols).rates().get(symbols))
        .orElseThrow(() -> new UnknownSymbolsException(symbols));
    RateHistoryMO rateHistory = getRateHistory(base, symbols);
    Double minRate = rateHistory.rates().values()
        .stream().filter(map -> map.get(symbols) != null).map(map -> map.get(symbols))
        .min(Comparator.naturalOrder()).orElseThrow(() -> new UnknownSymbolsException(symbols));
    Double maxRate = rateHistory.rates().values()
        .stream().filter(map -> map.get(symbols) != null).map(map -> map.get(symbols))
        .max(Comparator.naturalOrder()).orElseThrow(() -> new UnknownSymbolsException(symbols));

    return new RateStatMO(base, symbols, currentRate, minRate, maxRate);
  }
}
