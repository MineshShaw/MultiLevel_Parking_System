package com.parkingsystem.model;

import java.util.*;
import com.parkingsystem.slots.Slot;
import com.parkingsystem.model.enums.SlotType;

public class Gate {
    private final int gateId;
    private final Map<SlotType, TreeSet<SlotData>> slotMap = new HashMap<>();

    public Gate(int gateId) {
        this.gateId = gateId;
        for (SlotType type : SlotType.values()) {
            slotMap.put(type, new TreeSet<>());
        }
    }

    public void addSlotDistance(Slot slot, int distance) {
        slotMap.get(slot.getType()).add(new SlotData(distance, slot));
    }

    public TreeSet<SlotData> getSlotsByType(SlotType type) {
        return slotMap.get(type);
    }

    public int getGateId() { return gateId; }
}
