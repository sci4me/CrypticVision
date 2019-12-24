package com.sci.cv.proxy;

import akka.util.Crypt;
import com.sci.cv.CrypticVision;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public final class ClientProxy extends CommonProxy {
    @Override
    public void preInit(final FMLPreInitializationEvent evt) {
        OBJLoader.INSTANCE.addDomain("crypticv");

        final ModelResourceLocation location = new ModelResourceLocation("crypticv:cryptic_glasses", "inventory");
        ModelBakery.registerItemVariants(CrypticVision.instance().glasses(), location);
        ModelLoader.setCustomMeshDefinition(CrypticVision.instance().glasses(), stack -> location);
    }

    @Override
    public void init(final FMLInitializationEvent evt) {
    }

    @Override
    public void postInit(final FMLPostInitializationEvent evt) {

    }
}