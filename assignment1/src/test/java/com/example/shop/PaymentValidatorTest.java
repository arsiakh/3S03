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
   @Test
   public void testPayPalPaymentValidUppercase() {
       assertTrue(paymentValidator.isPaymentMethodValid("PAYPAL"));
   }


   @Test
   public void testPayPalPaymentValidMixedCase() {
       assertTrue(paymentValidator.isPaymentMethodValid("PayPal"));
   }


   @Test
   public void testCryptoPaymentInvalid() {
       assertFalse(paymentValidator.isPaymentMethodValid("crypto"));
   }


   @Test
   public void testCryptoPaymentInvalidUppercase() {
       assertFalse(paymentValidator.isPaymentMethodValid("CRYPTO"));
   }


   @Test
   public void testCryptoPaymentInvalidMixedCase() {
       assertFalse(paymentValidator.isPaymentMethodValid("CrYpTo"));
   }


   @Test
   public void testUnknownPaymentMethodThrowsException() {
       assertThrows(UnsupportedOperationException.class,
           () -> paymentValidator.isPaymentMethodValid("bitcoin"),
           "Unknown payment method should throw UnsupportedOperationException");
   }


   @Test
   public void testEmptyStringPaymentMethodThrowsException() {
       assertThrows(UnsupportedOperationException.class,
           () -> paymentValidator.isPaymentMethodValid(""));
   }


   @Test
   public void testRandomUnknownMethodThrowsException() {
       assertThrows(UnsupportedOperationException.class,
           () -> paymentValidator.isPaymentMethodValid("venmo"));
   }


   @ParameterizedTest
   @ValueSource(strings = {"card", "paypal"})
   public void testValidPaymentMethods(String method) {
       assertTrue(paymentValidator.isPaymentMethodValid(method));
   }


   @ParameterizedTest
   @ValueSource(strings = {"crypto", "CRYPTO"})
   public void testInvalidPaymentMethods(String method) {
       assertFalse(paymentValidator.isPaymentMethodValid(method));
   }

}
