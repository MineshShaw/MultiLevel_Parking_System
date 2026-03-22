package com.parkingsystem.model;

import com.parkingsystem.model.enums.SlotType;

public class Vehicle {
    private final String licensePlate;
    private final SlotType type;

    public Vehicle(String licensePlate, SlotType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public SlotType getType() { return type; }
    public String getLicensePlate() { return licensePlate; }
}