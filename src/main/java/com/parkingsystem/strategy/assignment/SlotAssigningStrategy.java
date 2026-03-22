package com.parkingsystem.strategy.assignment;

import com.parkingsystem.model.SlotData;
import com.parkingsystem.slots.Slot;

import java.util.TreeSet;

public interface SlotAssigningStrategy {
    Slot findSlot(TreeSet<SlotData> candidates);
}
