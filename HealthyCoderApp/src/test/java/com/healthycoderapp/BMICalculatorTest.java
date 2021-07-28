package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class BMICalculatorTest {

    private String environment = "dev";

    public static final SimpleDateFormat sdf =
            new SimpleDateFormat("HH:mm:ss.SSS");

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all unit tests. - " + sdf.format(new Date()));
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After all unit tests. - " + sdf.format(new Date()));
    }

    @Nested
    class IsDietRecommendedTests {
        @ParameterizedTest(name = "weight={0}, height={1}")
//    @ValueSource(doubles = {89.0, 95.0, 110.0})
//    @CsvSource(value = {"89.0, 1.72", "95.0, 1.75", "110.0, 1.78"})
        // use params = numLinesToSkip, to skip the header line
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
        void should_ReturnTrue_When_DietRecommended(Double coderWeight, Double coderHeight) {
            // given
            double weight = coderWeight;
            double height = coderHeight;

            // when
            boolean recommended = BMICalculator.isDietRecommended(weight, height);

            // then
            assertTrue(recommended);
        }

        @Test
        void should_ReturnFalse_When_DietRecommended() {
            // given
            double weight = 50.0;
            double height = 1.92;

            // when
            boolean recommended = BMICalculator.isDietRecommended(weight, height);

            // then
            assertFalse(recommended);
        }

        @Test
        void should_ThrowArithmeticException_When_HeightZero() {
            // given
            double weight = 50.0;
            double height = 0;

            // when
            Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

            // then
            assertThrows(ArithmeticException.class, executable);
        }
    }

    @Nested
    @DisplayName("{{}} sample inner class display name")
    class FindCoderWithWorstBMITests {
        @Test
        @DisplayName(">>> sample method display name")
//        @Disabled // disable function
        @DisabledOnOs(OS.LINUX) // disable by OS
        void should_ReturnCoderWithWorstBMI_WhenListNotEmpty() {
            // given
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.7));

            // when
            Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

            // then
            assertAll(
                    () -> assertEquals(98.0, coderWorstBMI.getWeight()),
                    () -> assertEquals(1.82, coderWorstBMI.getHeight())
            );
        }

        @Test
        void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas1000Elements() {
            // given
            assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
            ArrayList<Coder> coders = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                coders.add(new Coder(1.0 + i, 10.0 + 1));
            }

            // when
            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

            // then
            assertTimeout(Duration.ofMillis(500), executable);
        }

        @Test
        void should_ReturnNullWithWorstBMI_WhenListEmpty() {
            // given
            List<Coder> coders = new ArrayList<>();

            // when
            Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

            // then
            assertNull(coderWorstBMI);

        }
    }

    @Nested
    class GetBMIScores {
        @Test
        void should_ReturnCorrectBMIScoreArray_When_CoderListsNotEmpty() {
            // given
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.7));
            double[] expected = {18.52 , 29.59, 19.53};
            // when
            double[] bmiScores = BMICalculator.getBMIScores(coders);
            // then
            assertArrayEquals(expected, bmiScores);
        }
    }



}