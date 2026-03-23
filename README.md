# Multi-Level Parking System (LLD)
An enterprise-grade, low-level design implementation of a multi-level parking lot. This system supports high-concurrency slot allocation, nearest-distance logic, and level-specific pricing using Java.

---
## 🚀 How it Works

The system follows a modular flow to ensure scalability and thread safety:

### 1. **Configuration (The Blueprint):** 
Using the ParkingLotBuilder, the client defines the physical structure: the number of levels, gates per level, and slots.

### 2. **Distance Mapping:** 
For every slot, a distance array is provided relative to every gate in the system. This allows the system to find the "nearest" slot regardless of which floor it’s on.

### 3. **Entry & Allocation:** 
When a vehicle arrives at a gate, the SlotManager uses the NearestSlotAssigningStrategy to find the closest available slot.

### 4. **Concurrency Control:** 
To prevent two cars from taking the same spot at the same time, we use AtomicBoolean operations within the Slot class to ensure "first-come, first-served" at a CPU level.

### 5. **Billing & Exit:** 
Upon exit, the BillingService calculates the fee based on the HourlyBillingStrategy. Pricing is determined by the specific slot's rate (allowing different levels to have different costs).

---

## 📊 Low Level Design (UML)

![Multi-Level Parking System LLD Diagram](https://lh3.googleusercontent.com/d/1Z0wMvU6b66_5g4NfkRzhBO8zuQ96IlJI)
---

## 📁 File Structure
```
src/main/java/com/parkingsystem
├── Main.java                                 # Entry point & Simulation orchestrator
├── controller
│   └── ParkingLot.java                       # Main API Facade for entry/exit operations
├── factory
│   └── ParkingLotFactory.java                # Assembler logic to build system from DTOs
├── model
│   ├── Gate.java                             # Entry/Exit points with sorted slot data
│   ├── Level.java                            # Floor representation & maintenance status
│   ├── ParkingTicket.java                    # Session record (timestamp, slot, vehicle info)
│   ├── SlotData.java                         # Wrapper for sorting slots by distance
│   ├── Vehicle.java                          # Vehicle DTO (plate, type)
│   ├── dto
│   │   ├── ParkingLotBuilder.java            # Fluent interface for system configuration
│   │   └── SlotInput.java                    # Temporary container for raw slot data
│   └── enums
│       └── SlotType.java                     # SMALL, MEDIUM, LARGE definitions
├── service
│   ├── BillingService.java                   # High-level orchestrator for fee calculation
│   └── SlotManager.java                      # Infrastructure registry & search coordinator
├── slots
│   ├── Slot.java                             # Abstract base with Atomic thread-safety
│   ├── SmallSlot.java                        # Concrete slot for small vehicles
│   ├── MediumSlot.java                       # Concrete slot for medium vehicles
│   └── LargeSlot.java                        # Concrete slot for large vehicles
└── strategy
    ├── assignment
    │   ├── SlotAssigningStrategy.java        # Interface for slot allocation logic
    │   └── NearestSlotAssigningStrategy.java # Proximity-based allocation logic
    └── billing
        ├── BillingStrategy.java              # Interface for fee calculation logic
        └── HourlyBillingStrategy.java        # Standard time-based pricing implementation
```
---

## 🛠️ Compilation & Execution

### **1. Compile**
```bash
javac -d out --source-path src/main/java src/main/java/com/parkingsystem/Main.java
```

### **2. Run**
```bash
java -cp out com.parkingsystem.Main
```

> **Note:**  
> The `-d out` flag keeps compiled `.class` files separate from source code.

---
## ✨ Key Features

### 1. **Thread Safety:** 
Handled via java.util.concurrent and Atomic primitives.

### 2. **Strategy Pattern:** 
Easily swap out NearestSlot with CheapestSlot or Hourly with FlatRate billing.

### 3. **Builder Pattern:** 
Simplifies the "hectic" process of initializing complex infrastructure.

### 4. **Scalability:** 
Supports a virtually unlimited number of levels and gates.
