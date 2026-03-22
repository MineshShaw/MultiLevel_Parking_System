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
    // We use ConcurrentHashMap to allow thread-safe administrative 
    // updates (like adding gates/levels) while cars are entering.
    private final Map<Integer, Gate> gates = new ConcurrentHashMap<>();
    private final Map<Integer, Level> levels = new ConcurrentHashMap<>();
    private final SlotAssigningStrategy strategy;

    public SlotManager(SlotAssigningStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Entry point for finding a slot.
     */
    public Slot getNearestSlot(int gateId, SlotType type) {
        Gate gate = gates.get(gateId);
        if (gate == null) {
            throw new NoSuchElementException("Gate ID " + gateId + " does not exist.");
        }

        // Retrieve the pre-sorted TreeSet for the requested SlotType
        TreeSet<SlotData> candidates = gate.getSlotsByType(type);
        
        // Delegate the search and reservation logic to the strategy
        return strategy.findSlot(candidates);
    }

    /**
     * Administrative: Add a new level to the system
     */
    public void addLevel(Level level) {
        levels.put(level.getLevelId(), level);
    }

    /**
     * Administrative: Add a new gate to the system
     */
    public void addGate(Gate gate) {
        gates.put(gate.getGateId(), gate);
    }

    /**
     * Requirement: Level-wise operations (Maintenance)
     * This toggles the 'active' status of an entire level.
     */
    public void setLevelMaintenanceMode(int levelId, boolean isUnderMaintenance) {
        Level level = levels.get(levelId);
        if (level != null) {
            // If maintenance is true, active is false
            level.setActive(!isUnderMaintenance);
            System.out.println("Level " + levelId + " maintenance mode set to: " + isUnderMaintenance);
        } else {
            throw new NoSuchElementException("Level ID " + levelId + " not found.");
        }
    }

    /**
     * Helper to get a specific level if needed for reporting
     */
    public Level getLevel(int levelId) {
        return levels.get(levelId);
    }
}