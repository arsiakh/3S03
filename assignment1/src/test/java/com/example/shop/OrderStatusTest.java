package com.example.shop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderStatusTest {

    @Test
    public void testOrderStatusEnumValues() {
        assertEquals(3, OrderStatus.values().length);
    }

    @Test
    public void testOrderStatusCreated() {
        assertSame(OrderStatus.CREATED, OrderStatus.CREATED);
    }

    @Test
    public void testOrderStatusPaid() {
        assertSame(OrderStatus.PAID, OrderStatus.PAID);
    }

    @Test
    public void testOrderStatusCancelled() {
        assertSame(OrderStatus.CANCELLED, OrderStatus.CANCELLED);
    }

    @Test
    public void testOrderStatusValueOf() {
        assertEquals(OrderStatus.CREATED, OrderStatus.valueOf("CREATED"));
        assertEquals(OrderStatus.PAID, OrderStatus.valueOf("PAID"));
        assertEquals(OrderStatus.CANCELLED, OrderStatus.valueOf("CANCELLED"));
    }

    @Test
    public void testOrderStatusInvalidValue() {
        assertThrows(IllegalArgumentException.class,
            () -> OrderStatus.valueOf("INVALID"));
    }
}
