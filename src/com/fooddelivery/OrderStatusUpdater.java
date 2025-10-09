package com.fooddelivery;

import java.util.Timer;
import java.util.TimerTask;

public class OrderStatusUpdater {
    private Timer timer;

    public void startStatusUpdate(Order order) {
        timer = new Timer();
        String[] statuses = {"Preparing", "Out for Delivery", "Delivered"};
        int delay = 6000; 

        for (int i = 0; i < statuses.length; i++) {
            int index = i;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    order.setStatus(statuses[index]);
                    if (statuses[index].equals("Delivered")) timer.cancel();
                }
            }, delay * (i + 1));
        }
    }
}
