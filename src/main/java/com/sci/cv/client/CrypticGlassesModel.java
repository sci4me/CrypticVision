package com.sci.cv.client;

import com.sci.cv.CrypticVision;
import com.sci.cv.client.effect.Effect;
import com.sci.cv.client.effect.Effects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

public final class CrypticGlassesModel extends ModelBiped {
    private static final CrypticGlassesModel INSTANCE = new CrypticGlassesModel();
    private static final ItemStack STACK = new ItemStack(CrypticVision.instance().glasses());
    private static final int LIGHT_SEGMENTS = 20;

    public static ModelBiped instance() {
        return INSTANCE;
    }

    private IBakedModel model;

    private CrypticGlassesModel() {
        try {
            final ModelManager mm = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager();
            this.model = mm.getModel(new ModelResourceLocation("crypticv:cryptic_glasses"));
        } catch(final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(final Entity entity, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        ItemStack stack = null;
        for(final ItemStack armor : entity.getArmorInventoryList()) {
            if(armor.getItem().equals(CrypticVision.instance().glasses())) {
                stack = armor;
                break;
            }
        }

        final Effect effect;
        final NBTTagCompound tag = stack.getTagCompound();
        if(tag == null) {
            effect = Effects.byName("Genesis");
        } else {
            effect = Effects.byName(tag.getString("anim"));
        }

        effect.update();

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        if(entity instanceof EntityArmorStand) GlStateManager.rotate(90, 0, 1, 0);

        if(entity.isSneaking()) GlStateManager.translate(0, 0.2, 0);

        this.drawModel((EntityLivingBase) entity, netHeadYaw, headPitch);
        this.drawLights(netHeadYaw, headPitch, effect);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void drawModel(final EntityLivingBase entity, final float netHeadYaw, final float headPitch) {
        GlStateManager.pushMatrix();

        GlStateManager.scale(0.135, 0.135, 0.135);
        GlStateManager.rotate(180, 1, 0, 0);
        GlStateManager.rotate(90, 0, 1, 0);
        GlStateManager.rotate(netHeadYaw, 0, -1, 0);
        GlStateManager.rotate(headPitch, 0, 0, 1);
        GlStateManager.translate(-1.45, 2.15, 0.5);

        Minecraft.getMinecraft().getRenderItem().renderItem(STACK, entity, ItemCameraTransforms.TransformType.HEAD, false);

        GlStateManager.popMatrix();
    }

    private void drawLights(final float netHeadYaw, final float headPitch, final Effect effect) {
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableTexture2D();
        GlStateManager.enableDepth();

        GlStateManager.rotate(netHeadYaw, 0, 1, 0);
        GlStateManager.rotate(headPitch, 1, 0, 0);
        GlStateManager.translate(0, -0.2225, -0.28);

        this.drawLight(-0.1475, 0.0125, effect.lightColor(0));
        this.drawLight(-0.1475, -0.0125, effect.lightColor(2));
        this.drawLight(-0.1225, 0.0125, effect.lightColor(4));
        this.drawLight(-0.1225, -0.0125, effect.lightColor(6));

        this.drawLight(0.1475, 0.0125, effect.lightColor(1));
        this.drawLight(0.1475, -0.0125, effect.lightColor(3));
        this.drawLight(0.1225, 0.0125, effect.lightColor(5));
        this.drawLight(0.1225, -0.0125, effect.lightColor(7));

        int light = 8;

        {
            final double r = 0.0675;
            final double o = Math.toRadians(12);
            for(int i = 0; i < Effect.INNER_LIGHTS; i++) {
                final double s = Math.sin(i * Math.PI * 2 / Effect.INNER_LIGHTS - o);
                final double c = Math.cos(i * Math.PI * 2 / Effect.INNER_LIGHTS - o);

                this.drawLight(r * c - 0.135, r * s, effect.lightColor(light++));
                this.drawLight(r * c + 0.135, r * s, effect.lightColor(light++));
            }
        }

        {
            final double r = 0.12;
            final double o = Math.toRadians(8);
            for(int i = 0; i < Effect.OUTER_LIGHTS; i++) {
                final double s = Math.sin(i * Math.PI * 2 / Effect.OUTER_LIGHTS - o);
                final double c = Math.cos(i * Math.PI * 2 / Effect.OUTER_LIGHTS - o);

                this.drawLight(r * c - 0.135, r * s, effect.lightColor(light++));
                this.drawLight(r * c + 0.135, r * s, effect.lightColor(light++));
            }
        }

        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    private void drawLight(final double x, final double y, final int color) {
        if(color == 0) return;

        final Tessellator tess = Tessellator.getInstance();
        final BufferBuilder buf = tess.getBuffer();
        buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);

        final int red = (color >> 24) & 0xFF;
        final int green = (color >> 16) & 0xFF;
        final int blue = (color >> 8) & 0xFF;
        final int alpha = color & 0xFF;

        buf.pos(x, y, 0);
        buf.color(red, green, blue, alpha);
        buf.endVertex();

        final double R = 0.01;
        for(int i = 0; i <= LIGHT_SEGMENTS; i++) {
            final double s = Math.sin(i * Math.PI * 2 / LIGHT_SEGMENTS);
            final double c = Math.cos(i * Math.PI * 2 / LIGHT_SEGMENTS);
            buf.pos(x + (R * c), y + (R * s), 0);
            buf.color(red, green, blue, alpha);
            buf.endVertex();
        }

        tess.draw();
    }
}