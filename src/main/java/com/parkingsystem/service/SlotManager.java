package com.parkingsystem.service;

import com.parkingsystem.model.Gate;
import com.parkingsystem.model.Level;
import com.parkingsystem.model.SlotData;
import com.parkingsystem.model.enums.SlotType;
import com.parkingsystem.slots.Slot;
import com.parkingsystem.strategy.assignment.SlotAssigningStrategy;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SlotManager {
    private final Map<Integer, Gate> gates = new ConcurrentHashMap<>();
    private final Map<Integer, Level> levels = new ConcurrentHashMap<>();
    private final SlotAssigningStrategy strategy;

    public SlotManager(SlotAssigningStrategy strategy) {
        this.strategy = strategy;
    }

    public Slot getNearestSlot(int gateId, SlotType type) {
        Gate gate = gates.get(gateId);
        if (gate == null) {
            throw new NoSuchElementException("Gate ID " + gateId + " does not exist.");
        }

        TreeSet<SlotData> candidates = gate.getSlotsByType(type);

        return strategy.findSlot(candidates);
    }

    public void addLevel(Level level) {
        levels.put(level.getLevelId(), level);
    }

    public void addGate(Gate gate) {
        gates.put(gate.getGateId(), gate);
    }
    
    public Level getLevel(int levelId) {
        return levels.get(levelId);
    }
}