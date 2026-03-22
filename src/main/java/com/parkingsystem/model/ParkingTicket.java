package com.parkingsystem.model;

import com.parkingsystem.slots.Slot;
import java.time.LocalDateTime;

public class ParkingTicket {
    private final String ticketId;
    private final Slot assignedSlot;
    private final LocalDateTime entryTime;
    private final String vehiclePlate;

    public ParkingTicket(String ticketId, Slot assignedSlot, String vehiclePlate) {
        this.ticketId = ticketId;
        this.assignedSlot = assignedSlot;
        this.vehiclePlate = vehiclePlate;
        this.entryTime = LocalDateTime.now();
    }

    public String getTicketId() { return ticketId; }
    public Slot getAssignedSlot() { return assignedSlot; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public String getVehiclePlate() { return vehiclePlate; }
}
