package com.doguni.web;

import com.doguni.service.CalendarService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.*;

@Controller
public class TrainerController {

    private final CalendarService calendarService;

    public TrainerController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @PreAuthorize("@accountGuard.isTrainer(authentication)")
    @GetMapping("/trainer/home")
    public String trainerHome(@RequestParam(required = false) Integer year,
                              @RequestParam(required = false) Integer month,
                              Model model) {

        YearMonth target = (year != null && month != null)
                ? YearMonth.of(year, month)
                : YearMonth.now();

        model.addAttribute("targetMonth", target);
        model.addAttribute("prevMonth", calendarService.previous(target));
        model.addAttribute("nextMonth", calendarService.next(target));
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("weeks", calendarService.buildMonthGrid(target, DayOfWeek.MONDAY));

        return "trainerHome";
    }
}