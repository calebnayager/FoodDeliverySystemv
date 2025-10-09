package com.fooddelivery;

public class OrderItem {
    private MenuItem item;
    private int quantity;

    public OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public MenuItem getItem() { return item; }
    public int getQuantity() { return quantity; }

    public double getTotalPrice() {
        return item.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return item.getName() + " x" + quantity + " = R" + String.format("%.2f", getTotalPrice());
    }
}
