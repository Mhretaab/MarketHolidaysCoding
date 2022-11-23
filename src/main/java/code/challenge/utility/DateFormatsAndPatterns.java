package code.challenge.utility;

import java.util.HashMap;
import java.util.Map;

public final class DateFormatsAndPatterns {
    public static final String MARKET_QUOTE_DD_MM_YYYY_DATE_FORMAT = "dd/MM/yyyy";
    public static final String MARKET_QUOTE_YYYY_MM_DD_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DD_MM_YYYY_DATE_PATTERN = "^(\\d+){2}/(\\d+){2}/(\\d+){4}$";
    public static final String YYYY_MM_DD_DATE_PATTERN = "^(\\d+){4}-(\\d+){2}-(\\d+){2}$";

    public static final Map<String, String> datePatternsAndFormats;

    static {
        datePatternsAndFormats = new HashMap<>();
        datePatternsAndFormats.put(DD_MM_YYYY_DATE_PATTERN, MARKET_QUOTE_DD_MM_YYYY_DATE_FORMAT);
        datePatternsAndFormats.put(YYYY_MM_DD_DATE_PATTERN, MARKET_QUOTE_YYYY_MM_DD_DATE_FORMAT);
    }

    private DateFormatsAndPatterns() {

    }
}
