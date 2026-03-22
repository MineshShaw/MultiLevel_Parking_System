package com.parkingsystem.slots;

import com.parkingsystem.model.enums.SlotType;

public class LargeSlot extends Slot {
    public LargeSlot(double price) { super(price, SlotType.LARGE); }
}