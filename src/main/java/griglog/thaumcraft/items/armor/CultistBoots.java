package griglog.thaumcraft.items.armor;

import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.interfaces.IVisDiscountGear;
import griglog.thaumcraft.items.interfaces.IWarpingGear;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class CultistBoots extends ArmorItem implements IWarpingGear, IVisDiscountGear {
    public CultistBoots() {
        super(ArmorMaterial.IRON, EquipmentSlotType.FEET, ModTab.props());
        setRegistryName("crimson_boots");
    }

    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "thaumcraft:textures/entity/armor/cultistboots.png";
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.UNCOMMON;
    }

    public int getWarp(ItemStack itemstack, PlayerEntity player) {
        return 1;
    }

    public int getVisDiscount(ItemStack stack, PlayerEntity player) {
        return 1;
    }
}
