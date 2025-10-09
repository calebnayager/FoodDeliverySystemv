package com.fooddelivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OrderManager {
    private List<Order> orders = new ArrayList<>();//list to store orders

    public void placeOrder(Order order) {
        orders.add(order);
        System.out.println("Order placed successfully!\n" + order);//shows order confirmation
    }
    //code to show all current orders
    public void showActiveOrders() {
        if (orders.isEmpty()) { //checks if app has any orders
            System.out.println("No orders yet.");//shows if there are no orders
            return;
        }
        System.out.println("\n--- Current Orders ---");
        for (Order o : orders) { //loop through all orders 
            System.out.println(o);//print each order
        }
    }

    //status update code
    public void startAutoStatusUpdate() {
        Timer timer = new Timer(true); //timer for status update
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Order o : orders) { //goes through all orders
                    switch (o.getStatus()) { //checks the current status
                        case "Received" -> o.setStatus("Preparing"); // preparing status
                        case "Preparing" -> o.setStatus("Out for delivery"); //out for delivery status
                        case "Out for delivery" -> o.setStatus("Delivered");// delivered status
                    }
                }
                System.out.println("Order statuses updated!"); //tells you if the code ran and the status updated
            }
        }, 60000, 60000); // means start after 1 min, repeat every 1 min
    }
}
