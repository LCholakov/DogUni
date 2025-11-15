package com.doguni.service;

import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarService {

    public List<List<LocalDate>> buildMonthGrid(YearMonth yearMonth, DayOfWeek firstDayOfWeek) {
        LocalDate firstOfMonth = yearMonth.atDay(1);
        int shift = (7 + (firstOfMonth.getDayOfWeek().getValue() - firstDayOfWeek.getValue())) % 7;
        LocalDate gridStart = firstOfMonth.minusDays(shift);

        List<List<LocalDate>> weeks = new ArrayList<>(6);
        LocalDate cursor = gridStart;

        for (int w = 0; w < 6; w++) {
            List<LocalDate> week = new ArrayList<>(7);
            for (int d = 0; d < 7; d++) {
                week.add(cursor);
                cursor = cursor.plusDays(1);
            }
            weeks.add(week);
        }
        return weeks;
    }

    public YearMonth previous(YearMonth ym) {
        return ym.minusMonths(1);
    }

    public YearMonth next(YearMonth ym) {
        return ym.plusMonths(1);
    }
}