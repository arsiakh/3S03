package com.example.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order();
    }

    @Test
    public void testOrderInitialStatus() {
        assertEquals(OrderStatus.CREATED, order.getStatus());
    }

    @Test
    public void testOrderInitiallyEmpty() {
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    public void testAddItemToCreatedOrder() {
        OrderItem item = new OrderItem("Product", 1, 100.0);
        order.addItem(item);
        assertEquals(1, order.getItems().size());
        assertTrue(order.getItems().contains(item));
    }

    @Test
    public void testAddMultipleItems() {
        OrderItem item1 = new OrderItem("Product1", 1, 100.0);
        OrderItem item2 = new OrderItem("Product2", 2, 50.0);
        order.addItem(item1);
        order.addItem(item2);
        assertEquals(2, order.getItems().size());
    }

    @Test
    public void testCannotAddItemAfterPaymentStatus() {
        OrderItem item1 = new OrderItem("Product", 1, 100.0);
        order.addItem(item1);
        order.setStatus(OrderStatus.PAID);

        OrderItem item2 = new OrderItem("Product2", 1, 50.0);
        assertThrows(IllegalStateException.class,
            () -> order.addItem(item2),
            "Cannot add items to PAID order");
    }

    @Test
    public void testCannotAddItemAfterCancelledStatus() {
        OrderItem item1 = new OrderItem("Product", 1, 100.0);
        order.addItem(item1);
        order.setStatus(OrderStatus.CANCELLED);

        OrderItem item2 = new OrderItem("Product2", 1, 50.0);
        assertThrows(IllegalStateException.class,
            () -> order.addItem(item2),
            "Cannot add items to CANCELLED order");
    }

    @Test
    public void testSetStatusToPaid() {
        order.setStatus(OrderStatus.PAID);
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    public void testSetStatusToCancelled() {
        order.setStatus(OrderStatus.CANCELLED);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    public void testGetItemsReturnsCorrectList() {
        OrderItem item1 = new OrderItem("Product1", 1, 100.0);
        OrderItem item2 = new OrderItem("Product2", 2, 50.0);
        order.addItem(item1);
        order.addItem(item2);
        
        var items = order.getItems();
        assertEquals(2, items.size());
        assertEquals(item1, items.get(0));
        assertEquals(item2, items.get(1));
    }

    @Test
    public void testCanTransitionFromCreatedToAnyStatus() {
        // CREATED -> PAID
        order.setStatus(OrderStatus.PAID);
        assertEquals(OrderStatus.PAID, order.getStatus());

        // PAID -> CREATED (should be possible since no validation)
        order.setStatus(OrderStatus.CREATED);
        assertEquals(OrderStatus.CREATED, order.getStatus());

        // CREATED -> CANCELLED
        order.setStatus(OrderStatus.CANCELLED);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }
}
