package com.parkingsystem.slots;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import com.parkingsystem.model.Level;

import com.parkingsystem.model.enums.SlotType;

public abstract class Slot {
    private final UUID id;
    private final double hourlyPrice;
    private final SlotType type;
    private final AtomicBoolean occupied = new AtomicBoolean(false);
    private Level parentLevel;

    public Slot(double hourlyPrice, SlotType type) {
        this.id = UUID.randomUUID();
        this.hourlyPrice = hourlyPrice;
        this.type = type;
    }

    public void setParentLevel(Level level) {
        this.parentLevel = level;
    }

    public boolean reserve() {
        if (parentLevel != null && !parentLevel.isActive()) {
            return false;
        }
        return occupied.compareAndSet(false, true);
    }

    public void release() {
        occupied.set(false);
    }

    public UUID getId() { return id; }
    public double getHourlyPrice() { return hourlyPrice; }
    public SlotType getType() { return type; }
    public boolean isOccupied() { return occupied.get(); }
}