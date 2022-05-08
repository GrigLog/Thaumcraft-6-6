package griglog.thaumcraft.items.armor;

import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumMaterial;
import griglog.thaumcraft.utils.ItemUtils;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ThaumiumArmor extends ArmorItem {
    public ThaumiumArmor(EquipmentSlotType slot) {
        super(ThaumMaterial.THAUMIUM, slot, ModTab.props());
        setRegistryName("thaumium_" + ItemUtils.slotName(slot));
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        switch (slot){
            default:
            case HEAD:
            case CHEST:
            case FEET:
                return "thaumcraft:textures/entity/armor/thaumium_1.png";
            case LEGS:
                return "thaumcraft:textures/entity/armor/thaumium_2.png";
        }
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.UNCOMMON;
    }
}
