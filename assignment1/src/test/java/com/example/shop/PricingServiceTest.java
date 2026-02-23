package com.example.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class PricingServiceTest {

    private PricingService pricingService;

    @BeforeEach
    public void setUp() {
        pricingService = new PricingService();
    }

    @Test
    public void testCalculateSubtotalEmptyOrder() {
        Order order = new Order();
        double subtotal = pricingService.calculateSubtotal(order);
        assertEquals(0.0, subtotal, 0.01);
    }

    @Test
    public void testCalculateSubtotalSingleItem() {
        Order order = new Order();
        OrderItem item = new OrderItem("Product", 2, 50.0);
        order.addItem(item);
        
        double subtotal = pricingService.calculateSubtotal(order);
        assertEquals(100.0, subtotal, 0.01);
    }

    @Test
    public void testCalculateSubtotalMultipleItems() {
        Order order = new Order();
        order.addItem(new OrderItem("Product1", 2, 50.0));
        order.addItem(new OrderItem("Product2", 3, 30.0));
        
        double subtotal = pricingService.calculateSubtotal(order);
        assertEquals(190.0, subtotal, 0.01);
    }

    @Test
    public void testCalculateSubtotalManyItems() {
        Order order = new Order();
        for (int i = 0; i < 10; i++) {
            order.addItem(new OrderItem("Product" + i, 1, 10.0));
        }
        
        double subtotal = pricingService.calculateSubtotal(order);
        assertEquals(100.0, subtotal, 0.01);
    }

    @Test
    public void testCalculateTaxZeroSubtotal() {
        double tax = pricingService.calculateTax(0.0);
        assertEquals(0.0, tax, 0.01);
    }

    @Test
    public void testCalculateTaxPositiveSubtotal() {
        double tax = pricingService.calculateTax(100.0);
        assertEquals(20.0, tax, 0.01, "Tax should be 20% of subtotal");
    }

    @Test
    public void testCalculateTaxVariousAmounts() {
        double tax1 = pricingService.calculateTax(50.0);
        assertEquals(10.0, tax1, 0.01);

        double tax2 = pricingService.calculateTax(250.0);
        assertEquals(50.0, tax2, 0.01);

        double tax3 = pricingService.calculateTax(1000.0);
        assertEquals(200.0, tax3, 0.01);
    }

    @Test
    public void testCalculateTaxNegativeSubtotalThrowsException() {
        assertThrows(IllegalArgumentException.class,
            () -> pricingService.calculateTax(-10.0),
            "Negative subtotal should throw IllegalArgumentException");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 100.0, 500.0, 1000.0})
    public void testCalculateTaxWithVariousSubtotals(double subtotal) {
        double tax = pricingService.calculateTax(subtotal);
        assertEquals(subtotal * 0.2, tax, 0.01);
    }

    @Test
    public void testCalculateTaxDecimalAmount() {
        double tax = pricingService.calculateTax(123.45);
        assertEquals(24.69, tax, 0.01);
    }

    @Test
    public void testCalculateSubtotalWithDecimalPrices() {
        Order order = new Order();
        order.addItem(new OrderItem("Item1", 1, 19.99));
        order.addItem(new OrderItem("Item2", 2, 14.99));
        
        double subtotal = pricingService.calculateSubtotal(order);
        assertEquals(49.97, subtotal, 0.01);
    }

    @Test
    public void testCalculateSubtotalFractionResult() {
        Order order = new Order();
        order.addItem(new OrderItem("Item1", 3, 10.0));
        order.addItem(new OrderItem("Item2", 1, 1.0));
        
        double subtotal = pricingService.calculateSubtotal(order);
        assertEquals(31.0, subtotal, 0.01);
    }
}
