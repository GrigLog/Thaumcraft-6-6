package griglog.thaumcraft.items.armor;

import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumMaterial;
import griglog.thaumcraft.api.IWarpingGear;
import griglog.thaumcraft.items.tools.void_.VoidHelper;
import griglog.thaumcraft.utils.ItemUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class VoidArmor extends ArmorItem implements IWarpingGear {

    public VoidArmor(EquipmentSlotType slot) {
        super(ThaumMaterial.VOID, slot, ModTab.props());
        setRegistryName("void_" + ItemUtils.slotName(slot));
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        switch (slot){
            default:
            case HEAD:
            case CHEST:
            case FEET:
                return "thaumcraft:textures/entity/armor/void_1.png";
            case LEGS:
                return "thaumcraft:textures/entity/armor/void_2.png";
        }
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.UNCOMMON;
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        super.onArmorTick(stack, world, player);
        VoidHelper.repairTick(stack, player);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        VoidHelper.repairTick(stack, entityIn);
    }

    public int getWarp(ItemStack itemstack, PlayerEntity player) {
        return 1;
    }
}
