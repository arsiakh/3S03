package com.example.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountServiceTest {

    private DiscountService discountService;

    @BeforeEach
    public void setUp() {
        discountService = new DiscountService();
    }

    @Test
    public void testNullDiscountCode() {
        double result = discountService.applyDiscount(100.0, null);
        assertEquals(100.0, result, 0.01, "Null discount code should return original amount");
    }

    @Test
    public void testBlankDiscountCode() {
        double result = discountService.applyDiscount(100.0, "   ");
        assertEquals(100.0, result, 0.01, "Blank discount code should return original amount");
    }

    @Test
    public void testStudent10Discount() {
        double result = discountService.applyDiscount(100.0, "student10");
        assertEquals(90.0, result, 0.01, "STUDENT10 code should apply 10% discount");
    }

    @Test
    public void testStudent10DiscountUppercase() {
        double result = discountService.applyDiscount(100.0, "STUDENT10");
        assertEquals(90.0, result, 0.01, "STUDENT10 code (uppercase) should apply 10% discount");
    }

    @Test
    public void testStudent10DiscountMixedCase() {
        double result = discountService.applyDiscount(100.0, "StUdEnT10");
        assertEquals(90.0, result, 0.01, "STUDENT10 code (mixed case) should apply 10% discount");
    }

}