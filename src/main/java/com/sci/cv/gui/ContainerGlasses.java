package com.sci.cv.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public final class ContainerGlasses extends Container {
    @Override
    public boolean canInteractWith(final EntityPlayer player) {
        return true;
    }
}