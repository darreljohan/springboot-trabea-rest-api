package com.iglo.trabea.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Getter @Setter
public class WeekRange {
    private LocalDate startWeek;
    private LocalDate endWeek;

    public WeekRange(LocalDate activeDate) {
        this.startWeek = activeDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        this.endWeek = startWeek.plusDays(6);
    }
}
