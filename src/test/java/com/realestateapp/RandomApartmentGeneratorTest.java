package com.realestateapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RandomApartmentGeneratorTest {

    private static final double MAX_MULTIPLIER = 4;

    @Nested
    class GenerateApartmentWithDefaultParamsTests {
        private RandomApartmentGenerator generator;

        @BeforeEach
        void setup() {
            this.generator = new RandomApartmentGenerator();
        }

        @RepeatedTest(10)
        void should_GenerateCorrectApartment_When_DefaultMinAreaMinPrice() {
            //given
            double minArea = 30;
            double maxArea = minArea * MAX_MULTIPLIER;
            BigDecimal minPricePerSquareMeter = new BigDecimal(3000);
            BigDecimal maxPricePerSquareMeter = minPricePerSquareMeter.multiply(new BigDecimal(MAX_MULTIPLIER));

            //when
            Apartment apartment = generator.generate();

            //then
            BigDecimal maxApartmentPrice = new BigDecimal(apartment.getArea()).multiply(maxPricePerSquareMeter);
            BigDecimal minApartmentPrice = new BigDecimal(apartment.getArea()).multiply(minPricePerSquareMeter);
            assertAll(
                    () -> assertTrue(apartment.getArea() >= minArea),
                    () -> assertTrue(apartment.getArea() <= maxArea),
                    () -> assertTrue(apartment.getPrice().compareTo(minApartmentPrice) >= 0),
                    () -> assertTrue(apartment.getPrice().compareTo(maxApartmentPrice) <= 0)
            );
        }
    }

    @Nested
    class GenerateApartmentWithCustomParamsTests {
        private RandomApartmentGenerator generator;

        @BeforeEach
        void setup() {
            double minArea = 40;
            BigDecimal minPrice = new BigDecimal(4000);

            this.generator = new RandomApartmentGenerator(minArea, minPrice);
        }

        @RepeatedTest(10)
        void should_GenerateCorrectApartment_When_CustomMinAreaMinPrice() {
            //given
            double minArea = 40;
            double maxArea = minArea * MAX_MULTIPLIER;
            BigDecimal minPricePerSquareMeter = new BigDecimal(4000);
            BigDecimal maxPricePerSquareMeter = minPricePerSquareMeter.multiply(new BigDecimal(MAX_MULTIPLIER));

            //when
            Apartment apartment = generator.generate();

            //then
            BigDecimal maxApartmentPrice = new BigDecimal(apartment.getArea()).multiply(maxPricePerSquareMeter);
            BigDecimal minApartmentPrice = new BigDecimal(apartment.getArea()).multiply(minPricePerSquareMeter);
            assertAll(
                    () -> assertTrue(apartment.getArea() >= minArea),
                    () -> assertTrue(apartment.getArea() <= maxArea),
                    () -> assertTrue(apartment.getPrice().compareTo(minApartmentPrice) >= 0),
                    () -> assertTrue(apartment.getPrice().compareTo(maxApartmentPrice) <= 0)
            );
        }
    }

    @Nested
    class GenerateApartmentWithIncorrectParamsTests {
        private RandomApartmentGenerator generator;

        @BeforeEach
        void setup() {
            double minArea = 40;
            BigDecimal minPrice = new BigDecimal(4000);

            this.generator = new RandomApartmentGenerator(minArea, minPrice);
        }

        @RepeatedTest(10)
        void should_GenerateIncorrectApartment_When_IncorrectMinArea() {
            //given
            double minArea = 10;
            BigDecimal price = new BigDecimal(4000);
            Apartment actual = new RandomApartmentGenerator(minArea, price).generate();

            //when
            Apartment expected = generator.generate();

            //then
            assertFalse(actual.getArea() >= expected.getArea());
        }
    }
}