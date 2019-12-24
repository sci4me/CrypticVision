package com.sci.cv.item;

import com.sci.cv.CrypticVision;
import com.sci.cv.client.CrypticGlassesModel;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public final class ItemCrypticGlasses extends ItemArmor {
    public ItemCrypticGlasses() {
        super(ArmorMaterial.DIAMOND, 1, EntityEquipmentSlot.HEAD);
        this.setRegistryName("cryptic_glasses");
        this.setUnlocalizedName("cryptic_glasses");
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        final ItemStack stack = player.getHeldItem(hand);

        if(world.isRemote && player.isSneaking() && hand == EnumHand.MAIN_HAND) {
            player.openGui(CrypticVision.instance(), 0, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }

        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @Override
    public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        final NBTTagCompound tag = stack.getTagCompound();
        if(tag == null) {
            tooltip.add("Effect: Genesis");
        } else {
            tooltip.add("Effect: " + tag.getString("anim"));
        }
    }

    @Nullable
    @Override
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final EntityEquipmentSlot armorSlot, final ModelBiped _default) {
        return CrypticGlassesModel.instance();
    }
}