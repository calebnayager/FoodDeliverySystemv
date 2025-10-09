package com.fooddelivery;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private static int counter = 1;
    private int orderId;
    private String customerName;//customer name
    private String address; //adress for delivery
    private Map<MenuItem, Integer> items;//store menu items and quantities
    private String status;//order status
    private LocalDateTime orderTime;//when order was placed

    public Order(String customerName) {
        this.orderId = counter++;
        this.customerName = customerName;
        this.items = new HashMap<>();
        this.status = "Received";
        this.orderTime = LocalDateTime.now();
    }

    //address stuff
    public void setAddress(String address) { this.address = address; }
    public String getAddress() { return address; }

    
    public int getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public Map<MenuItem, Integer> getItems() { return items; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    //add items
    public void addItem(MenuItem item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    //calcuate total with tax and delivery
    public double calculateTotal() {
        double sum = 0;
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            sum += entry.getKey().getPrice() * entry.getValue();
        }
        double tax = sum * 0.15;//15 percent tax
        double deliveryFee = 20.0;//delivery fee
        return sum + tax + deliveryFee;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId)
          .append(", Customer: ").append(customerName)
          .append(", Address: ").append(address).append("\n")
          .append("Status: ").append(status).append("\nItems:\n");
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            sb.append(" - ").append(entry.getKey().getName())
              .append(" x").append(entry.getValue())
              .append(" = R").append(entry.getKey().getPrice() * entry.getValue())
              .append("\n");
        }
        sb.append("Total (with tax & delivery): R").append(calculateTotal()).append("\n");
        return sb.toString();
    }
}
