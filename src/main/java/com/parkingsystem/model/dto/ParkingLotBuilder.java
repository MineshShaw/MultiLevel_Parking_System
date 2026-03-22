package com.parkingsystem.model.dto;

import com.parkingsystem.model.enums.SlotType;
import java.util.ArrayList;
import java.util.List;
import com.parkingsystem.controller.ParkingLot;
import com.parkingsystem.factory.ParkingLotFactory;

public class ParkingLotBuilder {
    private int numLevels;
    private int[] gatesPerLevel;
    private final List<List<SlotInput>> levelsData = new ArrayList<>();

    public ParkingLotBuilder setLevels(int n) {
        this.numLevels = n;
        this.gatesPerLevel = new int[n];
        for (int i = 0; i < n; i++) {
            levelsData.add(new ArrayList<>());
        }
        return this;
    }

    public ParkingLotBuilder setGatesForLevel(int levelIndex, int numGates) {
        this.gatesPerLevel[levelIndex] = numGates;
        return this;
    }

    public ParkingLotBuilder addSlot(int levelIndex, SlotType type, double price, int[] distances) {
        levelsData.get(levelIndex).add(new SlotInput(type, price, distances));
        return this;
    }

    public ParkingLot build() {
        return ParkingLotFactory.build(this);
    }

    public int getNumLevels() { return numLevels; }
    public int[] getGatesPerLevel() { return gatesPerLevel; }
    public List<List<SlotInput>> getLevelsData() { return levelsData; }
}