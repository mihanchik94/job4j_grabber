package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import static java.util.Map.entry;

public class SqlRuDateTimeParser implements DateTimeParser {
    private static final String TODAY = "сегодня";
    private static final String YESTERDAY = "вчера";
    private static final Map<String, Integer> MONTHS = Map.ofEntries(
            entry("янв", 1),
            entry("фев", 2),
            entry("мар", 3),
            entry("апр", 4),
            entry("май", 5),
            entry("июн", 6),
            entry("июл", 7),
            entry("авг", 8),
            entry("сен", 9),
            entry("окт", 10),
            entry("ноя", 11),
            entry("дек", 12)
    );


    @Override
    public LocalDateTime parse(String parse) {
        LocalDateTime result = null;
        String[] dateTime = parse.split(", ");
        String[] dateElements = dateTime[0].split(" ");
        String[] timeElements = dateTime[1].split(":");
        if (parse.contains(TODAY)) {
            result = getDateTime(LocalDate.now(), timeElements);
        } else if (parse.contains(YESTERDAY)) {
            result = getDateTime(LocalDate.now().minusDays(1), timeElements);
        } else if (dateElements.length > 2) {
            result = getDateTime(LocalDate.of(2000 + Integer.parseInt(dateElements[2]),
                    MONTHS.get(dateElements[1]),
                    Integer.parseInt(dateElements[0])),
                    timeElements
            );
        }

        return result;
    }

    private LocalDateTime getDateTime(LocalDate date, String[] time) {
        return LocalDateTime.of(
                date,
                LocalTime.of(
                        Integer.parseInt(time[0]),
                        Integer.parseInt(time[1])
                )
        );
    }
}
