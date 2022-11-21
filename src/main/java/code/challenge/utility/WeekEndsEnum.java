package code.challenge.utility;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum WeekEndsEnum {
    SATURDAY,
    SUNDAY;

    public static Set<String> getWeekEnds() {

        return Arrays.stream(WeekEndsEnum.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
