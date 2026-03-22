package com.parkingsystem.model.dto;

import com.parkingsystem.model.enums.SlotType;

public class SlotInput {
    public final SlotType type;
    public final double price; // can be used for dynamic pricing based on distance or time
    public final int[] distancesToGates; // index = Gate Index, value = distance

    public SlotInput(SlotType type, double price, int[] distancesToGates) {
        this.type = type;
        this.price = price;
        this.distancesToGates = distancesToGates;
    }
}