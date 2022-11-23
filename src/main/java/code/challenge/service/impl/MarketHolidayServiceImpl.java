package code.challenge.service.impl;

import code.challenge.service.MarketHolidayService;
import code.challenge.utility.DateFormatsAndPatterns;
import code.challenge.utility.Delimiters;
import code.challenge.utility.WeekEndsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MarketHolidayServiceImpl implements MarketHolidayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketHolidayServiceImpl.class);

    @Override
    public Optional<List<String>> getMarketHolidays(final String filePath) throws IOException {
        LOGGER.info("Computing holidays from file: {}", filePath);

        final Optional<List<LocalDate>> marketDatesOptional = readMarketDatesFromCsv(filePath);

        if (marketDatesOptional.isEmpty()) {
            return Optional.empty();
        }

        List<LocalDate> unsortedMarketDates = marketDatesOptional.get();

        if(unsortedMarketDates.isEmpty()){
            return Optional.empty();
        }

        final List<LocalDate> sortedMarketDates = unsortedMarketDates.stream()
                .sorted().collect(Collectors.toList());

        LocalDate candidateHoliday = sortedMarketDates.get(0);
        final LocalDate latestDate = sortedMarketDates.get(sortedMarketDates.size() - 1);

        final List<String> holidays = new ArrayList<>();

        while (!candidateHoliday.isEqual(latestDate)) {
            if (!sortedMarketDates.contains(candidateHoliday) &&
                    !WeekEndsEnum.getWeekEnds().contains(candidateHoliday.getDayOfWeek().name())) {

                holidays.add(candidateHoliday.format(DateTimeFormatter.ofPattern(DateFormatsAndPatterns.MARKET_QUOTE_DD_MM_YYYY_DATE_FORMAT)));
            }

            candidateHoliday = candidateHoliday.plusDays(1);
        }

        return Optional.ofNullable(holidays);
    }

    private Optional<List<LocalDate>> readMarketDatesFromCsv(final String filePath) throws IOException {
        LOGGER.info("Parsing file: {}", filePath);
        final File file = new File(filePath);
        List<LocalDate> marketDates;

        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

            marketDates = bufferedReader.lines().skip(1).map(line -> {
                final String[] marketData = line.split(Delimiters.COMMA_DELIMITER);

                String dateFormat = "";

                if (marketData[0].matches(DateFormatsAndPatterns.DD_MM_YYYY_DATE_PATTERN)) {
                    dateFormat = DateFormatsAndPatterns.MARKET_QUOTE_DD_MM_YYYY_DATE_FORMAT;
                } else if (marketData[0].matches(DateFormatsAndPatterns.YYYY_MM_DD_DATE_PATTERN)) {
                    dateFormat = DateFormatsAndPatterns.MARKET_QUOTE_YYYY_MM_DD_DATE_FORMAT;
                }

                return LocalDate.parse(marketData[0], DateTimeFormatter.ofPattern(dateFormat));
            }).collect(Collectors.toList());

        }

        LOGGER.info("file :{} parsed successfully", filePath);
        return Optional.ofNullable(marketDates);
    }
}
