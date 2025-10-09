package com.fooddelivery;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {
    private List<MenuItem> menu = new ArrayList<>();

    public MenuManager() {
        menu.add(new MenuItem("Burger", 50.0));
        menu.add(new MenuItem("Pizza", 80.0));
        menu.add(new MenuItem("Fries", 20.0));
        menu.add(new MenuItem("Soda", 15.0));
    }

    public List<MenuItem> getMenu() { return menu; }

    public void displayMenu() {
        System.out.println("\n=== Menu ===");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i));
        }
    }

    public MenuItem getItem(int index) {
        if (index < 1 || index > menu.size()) return null;
        return menu.get(index - 1);
    }
}
