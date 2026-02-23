package com.example.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService = new OrderService();
    }

    @Test
    public void testProcessOrderInvalidPaymentMethod() {
        Order order = new Order();
        order.addItem(new OrderItem("Product", 1, 100.0));

        assertThrows(UnsupportedOperationException.class,
            () -> orderService.processOrder(order, null, "bitcoin"),
            "Unknown payment method should throw UnsupportedOperationException");
    }

    @Test
    public void testProcessOrderInvalidPaymentMethodNull() {
        Order order = new Order();
        order.addItem(new OrderItem("Product", 1, 100.0));

        double total = orderService.processOrder(order, null, null);
        assertEquals(0.0, total);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    public void testProcessOrderValidCardPayment() {
        Order order = new Order();
        order.addItem(new OrderItem("Product", 1, 100.0));

        double total = orderService.processOrder(order, null, "card");
        // Subtotal: 100, Discount: 100 (no code), Tax: 20% of 100 = 20
        assertEquals(120.0, total, 0.01);
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    public void testProcessOrderValidPayPalPayment() {
        Order order = new Order();
        order.addItem(new OrderItem("Product", 2, 50.0));

        double total = orderService.processOrder(order, null, "paypal");
        // Subtotal: 100, Discount: 100 (no code), Tax: 20% of 100 = 20
        assertEquals(120.0, total, 0.01);
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    public void testProcessOrderWithStudent10Discount() {
        Order order = new Order();
        order.addItem(new OrderItem("Product", 1, 100.0));

        double total = orderService.processOrder(order, "STUDENT10", "card");
        // Subtotal: 100, After discount: 90, Tax: 20% of 90 = 18
        assertEquals(108.0, total, 0.01);
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    public void testProcessOrderWithBlackFridayDiscount() {
        Order order = new Order();
        order.addItem(new OrderItem("Product", 1, 100.0));

        double total = orderService.processOrder(order, "BLACKFRIDAY", "card");
        // Subtotal: 100, After discount: 70, Tax: 20% of 70 = 14
        assertEquals(84.0, total, 0.01);
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    public void testProcessOrderMultipleItems() {
        Order order = new Order();
        order.addItem(new OrderItem("Product1", 2, 50.0));
        order.addItem(new OrderItem("Product2", 1, 30.0));

        double total = orderService.processOrder(order, "STUDENT10", "card");
        // Subtotal: 130, After STUDENT10: 117, Tax: 20% of 117 = 23.4
        assertEquals(140.4, total, 0.01);
        assertEquals(OrderStatus.PAID, order.getStatus());
    }
@Test
   public void testProcessOrderEmptyOrder() {
       Order order = new Order();


       double total = orderService.processOrder(order, null, "card");
       // Subtotal: 0, After discount: 0, Tax: 0
       assertEquals(0.0, total, 0.01);
       assertEquals(OrderStatus.PAID, order.getStatus());
   }


   @Test
   public void testProcessOrderInvalidDiscountCodeThrowsException() {
       Order order = new Order();
       order.addItem(new OrderItem("Product", 1, 100.0));


       assertThrows(IllegalArgumentException.class,
           () -> orderService.processOrder(order, "INVALID", "card"));
   }


   @Test
   public void testProcessOrderCryptoPaymentInvalid() {
       Order order = new Order();
       order.addItem(new OrderItem("Product", 1, 100.0));


       double total = orderService.processOrder(order, null, "crypto");
       assertEquals(0.0, total, "Crypto payment should return 0");
       assertEquals(OrderStatus.CANCELLED, order.getStatus(), "Order should be cancelled for invalid payment");
   }


   @Test
   public void testProcessOrderCardPaymentCaseInsensitive() {
       Order order = new Order();
       order.addItem(new OrderItem("Product", 1, 100.0));


       double total = orderService.processOrder(order, null, "CARD");
       assertEquals(120.0, total, 0.01);
       assertEquals(OrderStatus.PAID, order.getStatus());
   }


   @Test
   public void testProcessOrderUnknownDiscountCodeNoEffect() {
       Order order = new Order();
       order.addItem(new OrderItem("Product", 1, 100.0));


       double total = orderService.processOrder(order, "UNKNOWN", "card");
       // Subtotal: 100, After unknown discount: 100 (no change), Tax: 20
       assertEquals(120.0, total, 0.01);
   }


   @Test
   public void testProcessOrderComplexScenario() {
       Order order = new Order();
       order.addItem(new OrderItem("Laptop", 1, 1000.0));
       order.addItem(new OrderItem("Mouse", 2, 25.0));
       order.addItem(new OrderItem("USB", 5, 10.0));


       double total = orderService.processOrder(order, "BLACKFRIDAY", "paypal");
       // Subtotal: 1000 + 50 + 50 = 1100
       // After BLACKFRIDAY: 1100 * 0.7 = 770
       // Tax: 770 * 0.2 = 154
       assertEquals(924.0, total, 0.01);
       assertEquals(OrderStatus.PAID, order.getStatus());
   }


   @ParameterizedTest
   @CsvSource({
       "100.0, STUDENT10, card, 108.0",
       "100.0, BLACKFRIDAY, card, 84.0",
       "100.0, null, card, 120.0",
       "50.0, STUDENT10, paypal, 54.0"
   })
   public void testProcessOrderVariousScenarios(double itemPrice, String discountCode, String paymentMethod, double expected) {
       Order order = new Order();
       order.addItem(new OrderItem("Product", 1, itemPrice));


       double total = orderService.processOrder(order, discountCode, paymentMethod);
       assertEquals(expected, total, 0.01);
   }

    
}
