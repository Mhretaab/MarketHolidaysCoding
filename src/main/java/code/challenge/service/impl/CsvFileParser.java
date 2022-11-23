package code.challenge.service.impl;

import code.challenge.service.FileParser;
import code.challenge.utility.Delimiters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CsvFileParser implements FileParser<String, List<String[]>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileParser.class);

    @Override
    public List<String[]> parseFile(final String filePath) throws IOException {
        LOGGER.info("Parsing file: {}", filePath);

        List<String[]> marketData;

        final File file = new File(filePath);
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            marketData = bufferedReader.lines()
                    .skip(1)
                    .map(line -> line.split(Delimiters.COMMA_DELIMITER))
                    .collect(Collectors.toList());
        }

        LOGGER.info("file :{} parsed successfully", filePath);
        return marketData;
    }
}
