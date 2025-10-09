package com.fooddelivery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDeliveryGUI extends JFrame {

    private Menu menu;
    private List<Order> ordersList;
    private Order currentOrder;

    private JTextArea orderSummary;
    private JTextField nameField;
    private JTextField addressField;
    private JComboBox<String> menuBox;
    private JTextField qtyField;
    private JLabel statusLabel;

    public FoodDeliveryGUI() {
        menu = new Menu();
        ordersList = new ArrayList<>();

        setTitle("FastEat Delivery System");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Top 
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        nameField = new JTextField(10);
        addressField = new JTextField(15);
        menuBox = new JComboBox<>();
        for (MenuItem item : menu.getItems()) menuBox.addItem(item.toString());
        qtyField = new JTextField(5);
        JButton addButton = new JButton("Add Item");

        topPanel.add(new JLabel("Name:"));
        topPanel.add(nameField);
        topPanel.add(new JLabel("Address:"));
        topPanel.add(addressField);
        topPanel.add(new JLabel("Select Item:"));
        topPanel.add(menuBox);
        topPanel.add(new JLabel("Qty:"));
        topPanel.add(qtyField);
        topPanel.add(addButton);

        //Center
        orderSummary = new JTextArea();
        orderSummary.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderSummary);

        //Bottom
        JPanel bottomPanel = new JPanel();
        JButton placeOrderButton = new JButton("Place Order");
        JButton trackOrdersButton = new JButton("Track Orders");
        statusLabel = new JLabel("Status: Waiting...");

        bottomPanel.add(placeOrderButton);
        bottomPanel.add(trackOrdersButton);
        bottomPanel.add(statusLabel);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        //Add item
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            if (name.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter your name and address!");
                return;
            }

            int index = menuBox.getSelectedIndex();
            MenuItem item = menu.getItem(index + 1);

            String qtyText = qtyField.getText().trim();
            if (qtyText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a quantity!");
                return;
            }

            int qty;
            try {
                qty = Integer.parseInt(qtyText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity must be a number!");
                return;
            }

            if (currentOrder == null) {
                currentOrder = new Order(name);
                currentOrder.setAddress(address);
            }

            currentOrder.addItem(item, qty);
            refreshCurrentOrderDisplay();
            qtyField.setText("");
        });

        //Place order
        placeOrderButton.addActionListener(e -> {
            if (currentOrder == null || currentOrder.getItems().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No items in order!");
                return;
            }
            ordersList.add(currentOrder);
            JOptionPane.showMessageDialog(this, "Order placed successfully!");
            refreshAllOrdersDisplay();
            currentOrder = null;
            nameField.setText("");
            addressField.setText("");
            orderSummary.setText("");
        });

        //Track order
        trackOrdersButton.addActionListener(e -> refreshAllOrdersDisplay());

        //Status Update
        Timer timer = new Timer(60000, e -> updateStatuses());
        timer.start();
    }

    private void refreshCurrentOrderDisplay() {
        if (currentOrder == null) return;
        StringBuilder sb = new StringBuilder();
        sb.append("Current Order for ").append(currentOrder.getCustomerName())
          .append(", Address: ").append(currentOrder.getAddress()).append("\n");
        for (var entry : currentOrder.getItems().entrySet()) {
            sb.append(" - ").append(entry.getKey().getName())
              .append(" x").append(entry.getValue())
              .append(" = R").append(entry.getKey().getPrice() * entry.getValue()).append("\n");
        }
        sb.append("Total (with tax & delivery): R").append(currentOrder.calculateTotal()).append("\n");
        orderSummary.setText(sb.toString());
    }

    private void refreshAllOrdersDisplay() {
        StringBuilder sb = new StringBuilder();
        if (ordersList.isEmpty()) sb.append("No orders yet.\n");
        else {
            for (Order o : ordersList) {
                sb.append("Order ID: ").append(o.getOrderId())
                  .append(", Customer: ").append(o.getCustomerName())
                  .append(", Address: ").append(o.getAddress())
                  .append(", Status: ").append(o.getStatus()).append("\n");
                for (var entry : o.getItems().entrySet()) {
                    sb.append("   - ").append(entry.getKey().getName())
                      .append(" x").append(entry.getValue())
                      .append(" = R").append(entry.getKey().getPrice() * entry.getValue()).append("\n");
                }
                sb.append("   Total: R").append(o.calculateTotal()).append("\n\n");
            }
        }
        orderSummary.setText(sb.toString());
    }

    private void updateStatuses() {
        for (Order o : ordersList) {
            switch (o.getStatus()) {
                case "Received" -> o.setStatus("Preparing");
                case "Preparing" -> o.setStatus("Out for delivery");
                case "Out for delivery" -> {
                    o.setStatus("Delivered");
                    JOptionPane.showMessageDialog(this,
                        "Order ID " + o.getOrderId() + " for " + o.getCustomerName() + " has been delivered!");
                }
            }
        }
        statusLabel.setText("Statuses updated!");
        refreshAllOrdersDisplay();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FoodDeliveryGUI gui = new FoodDeliveryGUI();
            gui.setVisible(true);
        });
    }
}
