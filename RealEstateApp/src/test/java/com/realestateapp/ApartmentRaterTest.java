package com.realestateapp;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentRaterTest {

    @Test
    void should_ReturnBad_When_RateApartmentGetAreaZero() {

        // given
        Apartment apartment = new Apartment(0.0, new BigDecimal(1));

        // when
        int actual = ApartmentRater.rateApartment(apartment);

        // then
        assertEquals(-1, actual);

    }



}