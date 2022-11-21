package code.challenge.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MarketHolidayService {
    Optional<List<String>> getMarketHolidays(String filePath) throws IOException;
}
