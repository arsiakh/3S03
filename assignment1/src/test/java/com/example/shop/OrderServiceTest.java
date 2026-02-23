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

    
}
