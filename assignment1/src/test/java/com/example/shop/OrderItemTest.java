package com.example.shop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemTest {

    @Test
    public void testValidOrderItem() {
        OrderItem item = new OrderItem("Laptop", 2, 999.99);
        assertEquals(2, item.getQuantity()); // Tests the quantity getter works
    }

    @Test
    public void testOrderItemTotalPrice() {
        OrderItem item = new OrderItem("Book", 3, 15.5);
        assertEquals(46.5, item.getTotalPrice(), 0.01);
    }

    @Test
    public void testOrderItemZeroQuantityThrowsException() {
        assertThrows(IllegalArgumentException.class,
            () -> new OrderItem("Invalid", 0, 100.0),
            "Zero quantity should throw IllegalArgumentException");
    }

    @Test
    public void testOrderItemNegativeQuantityThrowsException() {
        assertThrows(IllegalArgumentException.class,
            () -> new OrderItem("Invalid", -5, 100.0),
            "Negative quantity should throw IllegalArgumentException");
    }

    @Test
    public void testOrderItemNegativePriceThrowsException() {
        assertThrows(IllegalArgumentException.class,
            () -> new OrderItem("Invalid", 1, -10.0),
            "Negative unit price should throw IllegalArgumentException");
    }

    @Test
    public void testOrderItemZeroPriceIsValid() {
        OrderItem item = new OrderItem("Free Item", 1, 0.0);
        assertEquals(0.0, item.getTotalPrice(), 0.01);
    }

    @Test
    public void testOrderItemLargeQuantity() {
        OrderItem item = new OrderItem("Bulk Item", 1000, 10.0);
        assertEquals(10000.0, item.getTotalPrice(), 0.01);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100, 1000})
    public void testOrderItemVariousQuantities(int quantity) {
        OrderItem item = new OrderItem("Item", quantity, 50.0);
        assertEquals(quantity * 50.0, item.getTotalPrice(), 0.01);
    }

    @Test
    public void testOrderItemWithDecimalPrice() {
        OrderItem item = new OrderItem("Item", 3, 19.99);
        assertEquals(59.97, item.getTotalPrice(), 0.01);
    }
}
