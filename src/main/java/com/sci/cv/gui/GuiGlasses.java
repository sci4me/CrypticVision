package com.sci.cv.gui;

import com.sci.cv.client.effect.Effect;
import com.sci.cv.client.effect.Effects;
import com.sci.cv.net.GamePacketHandler;
import com.sci.cv.net.PacketSelectAnim;
import com.sci.cv.util.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public final class GuiGlasses extends GuiContainer {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Constants.MOD_ID, "textures/gui/glasses.png");

    private final EntityPlayer player;

    public GuiGlasses(final EntityPlayer player) {
        super(new ContainerGlasses());
        this.player = player;
        this.xSize = 280;
        this.ySize = 166;
    }

    @Override
    public void initGui() {
        super.initGui();

        int cb = 0;
        int cx = 10;
        int cy = 10;
        for(final Effect effect : Effects.all()) {
            this.addButton(new GuiButton(cb++, this.guiLeft + cx, this.guiTop + cy, 80, 20, effect.name()));

            cy += 30;
            if(cy >= this.ySize - 20) {
                cy = 10;
                cx += 90;
            }
        }

        final NBTTagCompound tag = player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound();
        if(tag != null) {
            this.select(tag.getString("anim"));
        } else {
            this.select("Genesis");
        }
    }

    private void select(final String name) {
        for(final GuiButton button : this.buttonList) {
            button.enabled = !button.displayString.equals(name);
        }
    }

    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        GamePacketHandler.sendToServer(new PacketSelectAnim(button.displayString));
        this.select(button.displayString);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        final Tessellator tess = Tessellator.getInstance();
        final BufferBuilder buf = tess.getBuffer();
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buf.pos(this.guiLeft, this.guiTop, this.zLevel).tex(0, 0).endVertex();
        buf.pos(this.guiLeft, this.guiTop + this.ySize, this.zLevel).tex(0, 1).endVertex();
        buf.pos(this.guiLeft + this.xSize, this.guiTop + this.ySize, this.zLevel).tex(1, 1).endVertex();
        buf.pos(this.guiLeft + this.xSize, this.guiTop, this.zLevel).tex(1, 0).endVertex();
        tess.draw();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {

    }
}