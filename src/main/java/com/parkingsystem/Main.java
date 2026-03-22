package com.parkingsystem;

import com.parkingsystem.controller.ParkingLot;
import com.parkingsystem.model.ParkingTicket;
import com.parkingsystem.model.Vehicle;
import com.parkingsystem.model.dto.ParkingLotBuilder;
import com.parkingsystem.model.enums.SlotType;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Random rand = new Random();
        ParkingLotBuilder builder = new ParkingLotBuilder().setLevels(4);

        for (int i = 0; i < 4; i++) builder.setGatesForLevel(i, 2);

        double[] levelPrices = {10.0, 15.0, 20.0, 25.0};
        SlotType[] types = SlotType.values();

        for (int l = 0; l < 4; l++) {
            for (int s = 0; s < 25; s++) {
                SlotType randomType = types[rand.nextInt(types.length)];
                
                int[] distances = new int[8];
                for (int d = 0; d < 8; d++) distances[d] = rand.nextInt(100) + 1;
                
                builder.addSlot(l, randomType, levelPrices[l], distances);
            }
        }

        ParkingLot parkingLot = builder.build();

        System.out.println("--- System Initialized with 4 Levels, 8 Gates, and 100 Slots ---");

        ExecutorService simulation = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(120);

        for (int i = 0; i < 120; i++) {
            final int vehicleId = i;
            simulation.execute(() -> {
                try {
                    String plate = "VEH-" + vehicleId;
                    SlotType type = types[rand.nextInt(types.length)];
                    int gateId = rand.nextInt(8);

                    ParkingTicket ticket = parkingLot.entry(new Vehicle(plate, type), gateId);
                    
                    if (ticket != null) {
                        System.out.println("[ENTRY] " + plate + " (" + type + ") at Gate " + gateId + 
                                           " -> Slot Price: $" + ticket.getAssignedSlot().getHourlyPrice());
                        
                        Thread.sleep(rand.nextInt(2000) + 1000);

                        double fee = parkingLot.exit(ticket.getTicketId());
                        System.out.println("[EXIT]  " + plate + " paid: $" + fee);
                    } else {
                        System.out.println("[FULL]  " + plate + " (" + type + ") found no space.");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        simulation.shutdown();
        System.out.println("\n--- Simulation Complete: 120 vehicles processed ---");       
    }
}