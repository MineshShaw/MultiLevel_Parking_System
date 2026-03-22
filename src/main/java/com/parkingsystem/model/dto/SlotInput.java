package com.parkingsystem.model.dto;

import com.parkingsystem.model.enums.SlotType;

public class SlotInput {
    public final SlotType type;
    public final double price;
    public final int[] distancesToGates;

    public SlotInput(SlotType type, double price, int[] distancesToGates) {
        this.type = type;
        this.price = price;
        this.distancesToGates = distancesToGates;
    }
}