package code.challenge.service;

import code.challenge.service.impl.MarketHolidayServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

class MarketHolidayServiceImplTest {

    @Test
    void givenMarketQuotes_WhenRequestedForHoliday_shouldReturnListOfHolidays() throws IOException {
        //Given
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("test_data.csv");

        //When
        MarketHolidayService marketHolidayService = new MarketHolidayServiceImpl();
        Optional<List<String>> marketHolidays = marketHolidayService.getMarketHolidays(resource.getPath());

        //Then
        final List<String> expectedHolidays = List.of("04/07/2022", "07/07/2022", "08/07/2022");
        Assertions.assertEquals(expectedHolidays, marketHolidays.get());
    }

    @Test
    void givenNoCSVFile_WhenRequestedForHoliday_ShouldThrowException() {
        //Given
        MarketHolidayService marketHolidayService = new MarketHolidayServiceImpl();

        //Then
        Assertions.assertThrows(IOException.class, () -> marketHolidayService.getMarketHolidays("no_file.csv"));
    }
}