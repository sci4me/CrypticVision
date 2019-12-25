package com.sci.cv.client.effect;

import com.sci.cv.util.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

public final class AnimPakEffect extends Effect {
    private final long msPerFrame;
    private final String displayName;

    private int[][] frames;
    private int frame;
    private long last;

    public AnimPakEffect(final String name, final String displayName, final long msPerFrame) throws IOException {
        this.msPerFrame = msPerFrame;
        this.displayName = displayName;

        final IResource res = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(String.format("%s:anims/%s.animpak", Constants.MOD_ID, name)));

        final DataInputStream din = new DataInputStream(new GZIPInputStream(res.getInputStream()));

        final byte[] magic = new byte[5];
        if(din.read(magic) != 5) throw new IllegalArgumentException("Invalid AnimPak file!");
        if(!Arrays.equals(magic, new byte[]{ 'A', 'M', 'P', 'K', '\u001B' })) throw new IllegalArgumentException("Invalid AnikPak magic!");

        final byte version = din.readByte();
        if(version != 1) throw new IllegalArgumentException("Expected AnimPak version 1, got " + version);

        final int frameCount = din.readInt();

        this.frames = new int[frameCount][];
        for(int i = 0; i < this.frames.length; i++) {
            this.frames[i] = new int[Effect.LIGHTS];

            final int active = din.readInt();
            for(int j = 0; j < active; j++) {
                final int index = din.readInt();
                int value = din.readInt();

                if(value != 0) {
                    value = ((value & 0x00FFFFFF) << 8) | 0xFF;
                }

                this.frames[i][index] = value;
            }
        }
    }

    @Override
    public String name() {
        return this.displayName;
    }

    @Override
    public void update() {
        if(this.last == 0) {
            this.last = System.currentTimeMillis();
        } else {
            final long diff = System.currentTimeMillis() - this.last;
            if(diff >= this.msPerFrame) {
                this.last += diff;

                this.frame++;
                if(this.frame >= this.frames.length) this.frame = 0;
            }
        }
    }

    @Override
    public int lightColor(final int lightIndex) {
        return this.frames[this.frame][lightIndex];
    }
}