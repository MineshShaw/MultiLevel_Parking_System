package com.parkingsystem.model;

import com.parkingsystem.slots.Slot;

public class SlotData implements Comparable<SlotData> {
    private final int distance;
    private final Slot slot;

    public SlotData(int distance, Slot slot) {
        this.distance = distance;
        this.slot = slot;
    }

    public int getDistance() { return distance; }
    public Slot getSlot() { return slot; }

    @Override
    public int compareTo(SlotData other) {
        int distCompare = Integer.compare(this.distance, other.distance);
        if (distCompare != 0) return distCompare;
        return this.slot.getId().compareTo(other.slot.getId());
    }
}