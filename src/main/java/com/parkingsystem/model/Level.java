package com.parkingsystem.model;

import java.util.ArrayList;
import java.util.List;
import com.parkingsystem.slots.Slot;

public class Level {
    private final int levelId;
    private final List<Slot> slots = new ArrayList<>();
    private final List<Gate> gates = new ArrayList<>();
    private volatile boolean active = true;

    public Level(int levelId) {
        this.levelId = levelId;
    }

    public void addSlot(Slot slot) {
        slot.setParentLevel(this);
        slots.add(slot);
    }

    public void addGate(Gate gate) {
        gates.add(gate);
    }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public List<Slot> getSlots() { return slots; }
    public int getLevelId() { return levelId; }
}
