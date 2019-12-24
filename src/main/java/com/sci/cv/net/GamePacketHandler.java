package com.sci.cv.net;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class GamePacketHandler {
    private static final SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel("cv");
    private static int nextID;

    public static void init() {
        GamePacketHandler.wrapper.registerMessage(new PacketSelectAnim.Handler(), PacketSelectAnim.class, GamePacketHandler.nextID++, Side.SERVER);
    }

    public static void sendTo(final IMessage message, final EntityPlayerMP player) {
        GamePacketHandler.wrapper.sendTo(message, player);
    }

    public static void sendToAllAround(final IMessage message, final NetworkRegistry.TargetPoint target) {
        GamePacketHandler.wrapper.sendToAllAround(message, target);
    }

    public static void sendToAll(final IMessage message) {
        GamePacketHandler.wrapper.sendToAll(message);
    }

    public static void sendToDimension(final IMessage message, final int dimension) {
        GamePacketHandler.wrapper.sendToDimension(message, dimension);
    }

    public static void sendToServer(final IMessage message) {
        GamePacketHandler.wrapper.sendToServer(message);
    }
}