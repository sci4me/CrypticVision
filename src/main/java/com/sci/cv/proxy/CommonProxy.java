package com.sci.cv.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public abstract class CommonProxy {
    public abstract void preInit(final FMLPreInitializationEvent evt);
    public abstract void init(final FMLInitializationEvent evt);
    public abstract void postInit(final FMLPostInitializationEvent evt);
}