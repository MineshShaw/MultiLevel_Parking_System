package com.parkingsystem.strategy.billing;

import com.parkingsystem.model.ParkingTicket;
import java.time.Duration;
import java.time.LocalDateTime;

public class HourlyBillingStrategy implements BillingStrategy {
    @Override
    public double calculateCost(ParkingTicket ticket) {
        LocalDateTime exitTime = LocalDateTime.now();
        Duration duration = Duration.between(ticket.getEntryTime(), exitTime);
        
        // Calculate hours, rounding up (e.g., 1 hour 5 mins = 2 hours)
        long hours = (long) Math.ceil(duration.toMinutes() / 60.0);
        
        // Ensure at least 1 hour is charged
        if (hours <= 0) hours = 1;

        return hours * ticket.getAssignedSlot().getHourlyPrice();
    }
}