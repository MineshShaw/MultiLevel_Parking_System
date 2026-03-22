package com.parkingsystem.controller;

import com.parkingsystem.model.*;
import com.parkingsystem.service.*;
import com.parkingsystem.slots.Slot;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLot {
    private final SlotManager slotManager;
    private final BillingService billingService;
    // Thread-safe map to track active sessions
    private final Map<String, ParkingTicket> activeTickets = new ConcurrentHashMap<>();

    public ParkingLot(SlotManager slotManager, BillingService billingService) {
        this.slotManager = slotManager;
        this.billingService = billingService;
    }

    public ParkingTicket entry(Vehicle vehicle, int gateId) {
        // 1. Ask SlotManager to find and ATOMICALLY reserve the nearest slot
        Slot slot = slotManager.getNearestSlot(gateId, vehicle.getType());
        
        if (slot == null) {
            return null; // Or throw custom "ParkingFullException"
        }

        // 2. Create the ticket
        ParkingTicket ticket = new ParkingTicket(
            java.util.UUID.randomUUID().toString(), 
            slot, 
            vehicle.getLicensePlate()
        );

        // 3. Track the ticket
        activeTickets.put(ticket.getTicketId(), ticket);
        return ticket;
    }

    public double exit(String ticketId) {
        ParkingTicket ticket = activeTickets.remove(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Invalid or expired ticket ID.");
        }

        // 1. Calculate the fee
        double fee = billingService.calculateTotalFee(ticket);

        // 2. Explicitly free the slot for the next vehicle
        ticket.getAssignedSlot().release();

        return fee;
    }
}