package com.parkingsystem.strategy.billing;

import com.parkingsystem.model.ParkingTicket;

public interface BillingStrategy {
    double calculateCost(ParkingTicket ticket);
}


