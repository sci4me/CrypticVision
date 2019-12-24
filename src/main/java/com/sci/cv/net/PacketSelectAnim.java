package com.sci.cv.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSelectAnim implements IMessage {
    public String name;

    public PacketSelectAnim() {
    }

    public PacketSelectAnim(final String name) {
        this.name = name;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        final StringBuilder sb = new StringBuilder();
        final int n = buf.readInt();
        for(int i = 0; i < n; i++) {
            sb.append(buf.readChar());
        }
        this.name = sb.toString();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(this.name.length());
        for(final char c : this.name.toCharArray()) {
            buf.writeChar(c);
        }
    }

    public static final class Handler implements IMessageHandler<PacketSelectAnim, IMessage> {
        @Override
        public IMessage onMessage(final PacketSelectAnim message, final MessageContext ctx) {
            final EntityPlayerMP player = ctx.getServerHandler().player;
            final ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);

            NBTTagCompound tag = stack.getTagCompound();
            System.out.println("TAG COMPOUND: " + tag);
            if(tag == null) {
                tag = new NBTTagCompound();
            }

            tag.setString("anim", message.name);

            stack.setTagCompound(tag);
            return null;
        }
    }
}