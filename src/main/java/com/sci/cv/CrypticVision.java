package com.sci.cv;

import com.sci.cv.item.ItemCrypticGlasses;
import com.sci.cv.net.GamePacketHandler;
import com.sci.cv.proxy.CommonProxy;
import com.sci.cv.util.Constants;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = Constants.MOD_ID, useMetadata = true)
public final class CrypticVision {
    private static final CrypticVision INSTANCE = new CrypticVision();

    @SidedProxy(clientSide = Constants.CLIENT_PROXY, serverSide = Constants.SERVER_PROXY)
    private static CommonProxy proxy;

    @Mod.InstanceFactory
    public static CrypticVision instance() {
        return CrypticVision.INSTANCE;
    }

    private ItemCrypticGlasses glasses;

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent evt) {
        this.glasses = new ItemCrypticGlasses();

        final IForgeRegistry<Item> itemRegistry = GameRegistry.findRegistry(Item.class);
        itemRegistry.register(this.glasses);

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        GamePacketHandler.init();

        CrypticVision.proxy.preInit(evt);
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent evt) {
        CrypticVision.proxy.init(evt);
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent evt) {
        CrypticVision.proxy.postInit(evt);
    }

    public ItemCrypticGlasses glasses() {
        return this.glasses;
    }
}