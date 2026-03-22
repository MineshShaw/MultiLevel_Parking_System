package com.parkingsystem.service;

import com.parkingsystem.model.ParkingTicket;
import com.parkingsystem.strategy.billing.BillingStrategy;

public class BillingService {
    private final BillingStrategy strategy;

    public BillingService(BillingStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateTotalFee(ParkingTicket ticket) {
        return strategy.calculateCost(ticket);
    }
}