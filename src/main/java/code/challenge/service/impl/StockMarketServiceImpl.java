package code.challenge.service.impl;

import code.challenge.service.FileParser;
import code.challenge.service.StockMarketService;
import code.challenge.utility.DateFormatsAndPatterns;
import code.challenge.utility.WeekEndsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StockMarketServiceImpl implements StockMarketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockMarketServiceImpl.class);

    private final FileParser<String, List<String[]>> fileParser;

    public StockMarketServiceImpl(FileParser<String, List<String[]>> fileParser) {
        this.fileParser = fileParser;
    }

    @Override
    public Optional<List<String>> getMarketHolidays(final String filePath) throws IOException {
        LOGGER.info("Computing holidays from file: {}", filePath);

        List<LocalDate> marketDates = fileParser.parseFile(filePath)
                .stream()
                .map(data ->
                        DateFormatsAndPatterns.datePatternsAndFormats.entrySet().stream()
                                .filter(pattern -> data[0].matches(pattern.getKey()))
                                .limit(1)
                                .map(pattern -> LocalDate.parse(data[0], DateTimeFormatter.ofPattern(pattern.getValue())))
                                .findFirst().get()
                ).sorted()
                .collect(Collectors.toList());


        LocalDate candidateHoliday = marketDates.get(0);
        final LocalDate latestDate = marketDates.get(marketDates.size() - 1);

        final List<String> holidays = new ArrayList<>();

        while (!candidateHoliday.isEqual(latestDate)) {
            if (!marketDates.contains(candidateHoliday) &&
                    !WeekEndsEnum.getWeekEnds().contains(candidateHoliday.getDayOfWeek().name())) {

                holidays.add(candidateHoliday.format(DateTimeFormatter.ofPattern(DateFormatsAndPatterns.MARKET_QUOTE_DD_MM_YYYY_DATE_FORMAT)));
            }

            candidateHoliday = candidateHoliday.plusDays(1);
        }

        return Optional.of(holidays);
    }
}
