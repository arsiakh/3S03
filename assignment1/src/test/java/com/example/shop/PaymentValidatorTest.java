package com.example.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentValidatorTest {

    private PaymentValidator paymentValidator;

    @BeforeEach
    public void setUp() {
        paymentValidator = new PaymentValidator();
    }

    @Test
    public void testNullPaymentMethodReturnsFalse() {
        assertFalse(paymentValidator.isPaymentMethodValid(null));
    }

    @Test
    public void testCardPaymentValid() {
        assertTrue(paymentValidator.isPaymentMethodValid("card"));
    }

    @Test
    public void testCardPaymentValidUppercase() {
        assertTrue(paymentValidator.isPaymentMethodValid("CARD"));
    }

    @Test
    public void testCardPaymentValidMixedCase() {
        assertTrue(paymentValidator.isPaymentMethodValid("Card"));
    }

    @Test
    public void testPayPalPaymentValid() {
        assertTrue(paymentValidator.isPaymentMethodValid("paypal"));
    }

}
