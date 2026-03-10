package com.v2.hyodoring.family.core.greeting;

import lombok.Getter;

import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

@Getter
public enum GreetingTimeFormatter {
    JUST_NOW(86400, d -> d.getSeconds() < 60, d -> "방금 전"),
    MINUTES(3600, d -> d.toMinutes() < 60, d -> d.toMinutes() + "분 전"),
    HOURS(60, d -> d.toHours() < 24, d -> d.toHours() + "시간 전"),
    DAYS(0, d -> d.toDays() < 31, d -> d.toDays() + "일 전")

    ;

    private final int threshold;
    private final Predicate<Duration> condition;
    private final Function<Duration, String> converter;

    GreetingTimeFormatter(int threshold, Predicate<Duration> condition, Function<Duration, String> converter) {
        this.threshold = threshold;
        this.condition = condition;
        this.converter = converter;
    }

    public static String format(Duration duration) {
        return Arrays.stream(values())
                .sorted(Comparator.comparingLong(GreetingTimeFormatter::getThreshold).reversed())
                .filter(unit -> unit.condition.test(duration))
                .findFirst()
                .map(unit -> unit.converter.apply(duration))
                .orElse("오래 전");
    }
}
