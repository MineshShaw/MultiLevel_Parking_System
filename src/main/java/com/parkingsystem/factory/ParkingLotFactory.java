package com.parkingsystem.factory;

import com.parkingsystem.controller.ParkingLot;
import com.parkingsystem.model.*;
import com.parkingsystem.model.dto.*;
import com.parkingsystem.model.enums.SlotType;
import com.parkingsystem.slots.*;
import com.parkingsystem.strategy.assignment.NearestSlotAssigningStrategy;
import com.parkingsystem.strategy.billing.HourlyBillingStrategy;
import com.parkingsystem.service.BillingService;
import com.parkingsystem.service.SlotManager;

import java.util.*;

public class ParkingLotFactory {

    public static ParkingLot build(ParkingLotBuilder builder) {
        SlotManager sm = new SlotManager(new NearestSlotAssigningStrategy());
        List<Gate> globalGateList = new ArrayList<>();
        
        // --- PASS 1: Create Gates and assign them a "Global Index" ---
        for (int i = 0; i < builder.getNumLevels(); i++) {
            Level level = new Level(i + 1);
            sm.addLevel(level);
            
            for (int g = 0; g < builder.getGatesPerLevel()[i]; g++) {
                Gate gate = new Gate(globalGateList.size()); // ID is the current index
                level.addGate(gate);
                sm.addGate(gate);
                globalGateList.add(gate);
            }
        }

        // --- PASS 2: Create Slots and wire them to Gates via the global list ---
        for (int i = 0; i < builder.getNumLevels(); i++) {
            Level level = sm.getLevel(i + 1);
            List<SlotInput> slotsInLevel = builder.getLevelsData().get(i);

            for (SlotInput si : slotsInLevel) {
                Slot slot = createSlot(si.type, si.price);
                level.addSlot(slot);

                // Wire distances: si.distancesToGates[j] maps to globalGateList.get(j)
                for (int gateIdx = 0; gateIdx < si.distancesToGates.length; gateIdx++) {
                    int dist = si.distancesToGates[gateIdx];
                    Gate targetGate = globalGateList.get(gateIdx);
                    targetGate.addSlotDistance(slot, dist);
                }
            }
        }

        return new ParkingLot(sm, new BillingService(new HourlyBillingStrategy()));
    }

    private static Slot createSlot(SlotType type, double price) {
        return switch (type) {
            case SMALL -> new SmallSlot(price);
            case MEDIUM -> new MediumSlot(price);
            case LARGE -> new LargeSlot(price);
        };
    }
}