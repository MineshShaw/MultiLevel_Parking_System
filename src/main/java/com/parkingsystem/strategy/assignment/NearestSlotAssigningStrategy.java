package com.parkingsystem.strategy.assignment;

import com.parkingsystem.model.SlotData;
import com.parkingsystem.slots.Slot;

import java.util.TreeSet;
public class NearestSlotAssigningStrategy implements SlotAssigningStrategy {
    
    @Override
    public Slot findSlot(TreeSet<SlotData> candidates) {
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }

        // Iterate through candidates (TreeSet ensures we start with the shortest distance)
        for (SlotData data : candidates) {
            Slot slot = data.getSlot();
            
            // reserve() handles both the Level-active check and the Atomic occupancy flip
            if (slot.reserve()) {
                System.out.println("Slot found at distance: " + data.getDistance());
                return slot;
            }
        }
        
        return null; // No available slots for this type from this gate
    }
}