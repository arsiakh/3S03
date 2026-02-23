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
@Test
   public void testBlackFridayDiscount() {
       double result = discountService.applyDiscount(100.0, "blackfriday");
       assertEquals(70.0, result, 0.01, "BLACKFRIDAY code should apply 30% discount");
   }


   @Test
   public void testBlackFridayDiscountUppercase() {
       double result = discountService.applyDiscount(100.0, "BLACKFRIDAY");
       assertEquals(70.0, result, 0.01, "BLACKFRIDAY code (uppercase) should apply 30% discount");
   }


   @Test
   public void testInvalidCodeThrowsException() {
       assertThrows(IllegalArgumentException.class,
           () -> discountService.applyDiscount(100.0, "INVALID"),
           "INVALID code should throw IllegalArgumentException");
   }


   @Test
   public void testUnknownDiscountCode() {
       double result = discountService.applyDiscount(100.0, "UNKNOWN");
       assertEquals(100.0, result, 0.01, "Unknown discount code should return original amount");
   }


   @ParameterizedTest
   @ValueSource(doubles = {0.0, 1.5, 50.0, 999.99})
   public void testDiscountsWithVariousAmounts(double amount) {
       double student10Result = discountService.applyDiscount(amount, "STUDENT10");
       assertEquals(amount * 0.9, student10Result, 0.01);
   }


   @ParameterizedTest
   @CsvSource({
       "100.0, STUDENT10, 90.0",
       "200.0, BLACKFRIDAY, 140.0",
       "50.0, UNKNOWN, 50.0",
       "0.0, STUDENT10, 0.0"
   })
   public void testVariousDiscountScenarios(double amount, String code, double expected) {
       double result = discountService.applyDiscount(amount, code);
       assertEquals(expected, result, 0.01);
   }
}

