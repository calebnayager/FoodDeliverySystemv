package com.fooddelivery;

import javax.swing.SwingUtilities;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // ----------------- Launch GUI -----------------
        SwingUtilities.invokeLater(() -> {
            FoodDeliveryGUI gui = new FoodDeliveryGUI();
            gui.setVisible(true); // Make sure your GUI class extends JFrame
        });

        // ----------------- Keep existing console features -----------------
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(); 
        OrderManager orderManager = new OrderManager(); 

        // Start automatic order status updates (runs in background)
        orderManager.startAutoStatusUpdate();

        System.out.println("Welcome to FastEat Delivery System!"); // greet the user

        boolean exit = false; // flag to control main loop
        while (!exit) {
            // show options to the user
            System.out.println("\n1. Show Menu");
            System.out.println("2. Place Order");
            System.out.println("3. Show Active Orders");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim(); // get user input

            switch (choice) {
                case "1" -> menu.showMenu();
                case "2" -> {
                    System.out.print("Enter your name: ");
                    String customerName = scanner.nextLine().trim();

                    Order order = new Order(customerName);

                    boolean addingItems = true;
                    while (addingItems) {
                        menu.showMenu();
                        System.out.print("Select item number (0 to finish): ");
                        int itemNo = Integer.parseInt(scanner.nextLine());

                        if (itemNo == 0) break;

                        MenuItem item = menu.getItem(itemNo);
                        if (item == null) {
                            System.out.println("Invalid item number.");
                            continue;
                        }

                        System.out.print("Enter quantity: ");
                        int qty = Integer.parseInt(scanner.nextLine());

                        order.addItem(item, qty);
                    }

                    orderManager.placeOrder(order);
                }
                case "3" -> orderManager.showActiveOrders();
                case "4" -> {
                    exit = true;
                    System.out.println("Thanks for using FastEat Delivery System. Goodbye!");
                }
                default -> System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}
