package bmv.test.com.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private DateUtils() {
  }

  public static String toFormattedString(LocalDateTime date) {
    return date.format(DATE_FORMAT);
  }
}
