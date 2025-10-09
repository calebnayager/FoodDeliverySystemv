package com.fooddelivery;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItem> items = new ArrayList<>();

    public Menu() {
        //food items
        items.add(new MenuItem("Cheese Burger", 50.0));
        items.add(new MenuItem("Veggie Pizza", 80.0));
        items.add(new MenuItem("Fries", 25.0));
        items.add(new MenuItem("Coke", 15.0));
        items.add(new MenuItem("Ice Cream", 20.0));
    }

    public List<MenuItem> getItems() { return items; }

    public void showMenu() {
        System.out.println("\n--- Food Menu ---");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
    }

    public MenuItem getItem(int index) {
        if (index < 1 || index > items.size()) return null;
        return items.get(index - 1);
    }
}
