package com.healthycoderapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ActivityCalculatorTest {

    @Test
    void should_ReturnBad_When_AvgBelow20() {
        int weeklyCardioMins = 40;
        int weeklyWorkouts = 1;

        String actual = ActivityCalculator.rateActivityLevel(
                weeklyCardioMins, weeklyWorkouts
        );

        assertEquals("bad", actual);
    }

    @Test
    void should_ReturnAverage_When_AvgBetween20And40() {
        int weeklyCardioMins = 40;
        int weeklyWorkouts = 3;

        String actual = ActivityCalculator.rateActivityLevel(
                weeklyCardioMins, weeklyWorkouts
        );

        assertEquals("average", actual);
    }

    @Test
    void should_ReturnGood_When_AvgAbove40() {
        int weeklyCardioMins = 40;
        int weeklyWorkouts = 7;

        String actual = ActivityCalculator.rateActivityLevel(
                weeklyCardioMins, weeklyWorkouts
        );

        assertEquals("good", actual);
    }

    @Test
    void should_ThrowException_When_InputBelowZero() {
        int weeklyCardioMins = -40;
        int weeklyWorkouts = 7;

        Executable executable = () ->
                ActivityCalculator.rateActivityLevel(
                        weeklyCardioMins, weeklyWorkouts
                );

        assertThrows(RuntimeException.class, executable);
    }

}