package ru.job4j.grabber.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class SqlRuDateTimeParserTest {

    @Test
    public void whenYesterday() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "вчера, 19:23";
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(19, 23));
        assertEquals(parser.parse(date), expected);
    }

    @Test
    public void whenToday() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "сегодня, 02:30";
        LocalDateTime expected = LocalDateTime.of(LocalDate.now(), LocalTime.of(2, 30));
        assertEquals(parser.parse(date), expected);
    }

    @Test
    public void whenSomeDate() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "2 дек 19, 22:29";
        LocalDateTime expected = LocalDateTime.of(2019, 12, 2, 22, 29);
        assertEquals(parser.parse(date), expected);

    }
}