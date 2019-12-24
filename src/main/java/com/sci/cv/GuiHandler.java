package com.sci.cv;

import com.sci.cv.gui.ContainerGlasses;
import com.sci.cv.gui.GuiGlasses;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public final class GuiHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        if(ID == 0) {
            return new ContainerGlasses();
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        if(ID == 0) {
            return new GuiGlasses(player);
        }
        return null;
    }
}