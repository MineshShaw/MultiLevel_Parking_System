package com.parkingsystem.controller;

import com.parkingsystem.model.*;
import com.parkingsystem.service.*;
import com.parkingsystem.slots.Slot;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLot {
    private final SlotManager slotManager;
    private final BillingService billingService;
    private final Map<String, ParkingTicket> activeTickets = new ConcurrentHashMap<>();

    public ParkingLot(SlotManager slotManager, BillingService billingService) {
        this.slotManager = slotManager;
        this.billingService = billingService;
    }

    public ParkingTicket entry(Vehicle vehicle, int gateId) {
        Slot slot = slotManager.getNearestSlot(gateId, vehicle.getType());
        
        if (slot == null) {
            return null;
        }

        ParkingTicket ticket = new ParkingTicket(
            java.util.UUID.randomUUID().toString(), 
            slot, 
            vehicle.getLicensePlate()
        );

        activeTickets.put(ticket.getTicketId(), ticket);
        return ticket;
    }

    public double exit(String ticketId) {
        ParkingTicket ticket = activeTickets.remove(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Invalid or expired ticket ID.");
        }

        double fee = billingService.calculateTotalFee(ticket);

        ticket.getAssignedSlot().release();

        return fee;
    }
}