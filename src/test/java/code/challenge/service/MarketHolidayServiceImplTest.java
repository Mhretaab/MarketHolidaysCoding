package code.challenge.service;

import code.challenge.service.impl.CsvFileParser;
import code.challenge.service.impl.StockMarketServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

class MarketHolidayServiceImplTest {

    private FileParser<String, List<String[]>> fileParser;

    @BeforeEach
    public void setUp(){
        fileParser = new CsvFileParser();
    }

    @Test
    void givenMarketQuotes_WhenRequestedForHoliday_shouldReturnListOfHolidays() throws IOException {
        //Given
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("test_data.csv");

        //When
        StockMarketService marketHolidayService = new StockMarketServiceImpl(fileParser);
        Optional<List<String>> marketHolidays = marketHolidayService.getMarketHolidays(resource.getPath());

        //Then
        final List<String> expectedHolidays = List.of("04/07/2022", "07/07/2022", "08/07/2022");
        Assertions.assertEquals(expectedHolidays, marketHolidays.get());
    }

    @Test
    void givenEPAMMarketQuotes_WhenRequestedForHoliday_shouldReturnListOfHolidays() throws IOException {
        //Given
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("EPAM.csv");

        //When
        StockMarketService marketHolidayService = new StockMarketServiceImpl(fileParser);
        Optional<List<String>> marketHolidays = marketHolidayService.getMarketHolidays(resource.getPath());

        //Then
        final List<String> expectedHolidays = List.of("17/01/2022", "21/02/2022", "15/04/2022", "30/05/2022", "20/06/2022", "04/07/2022", "05/09/2022");
        Assertions.assertEquals(expectedHolidays, marketHolidays.get());
    }

    @Test
    void givenNoCSVFile_WhenRequestedForHoliday_ShouldThrowException() {
        //Given
        StockMarketService marketHolidayService = new StockMarketServiceImpl(fileParser);

        //Then
        Assertions.assertThrows(IOException.class, () -> marketHolidayService.getMarketHolidays("no_file.csv"));
    }
}