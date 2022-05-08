package griglog.thaumcraft.items.armor;

import griglog.thaumcraft.client.models.ModelKnightArmor;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumMaterial;
import griglog.thaumcraft.utils.ItemUtils;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CultistPlateArmor extends ArmorItem {
    BipedModel<LivingEntity> model1 = new ModelKnightArmor(1f);
    BipedModel<LivingEntity> model2 = new ModelKnightArmor(0.5f);

    public CultistPlateArmor(EquipmentSlotType slot) {
        super(ThaumMaterial.CULTIST_PLATE, slot, ModTab.props());
        setRegistryName("crimson_plate_" + ItemUtils.slotName(slot));
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "thaumcraft:textures/entity/armor/cultist_plate_armor.png";
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.UNCOMMON;
    }

    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        return stack2.isItemEqual(new ItemStack(Items.IRON_INGOT)) || super.getIsRepairable(stack1, stack2);
    }



    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        return (A) model1;
        /*switch (armorSlot){
            default:
            case HEAD:
            case CHEST:
                return (A) model1;
            case LEGS:
            case FEET:
                return (A) model2;
        }*/
    }
    /*public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel _default) {
        if (model1 == null) {
            model1 = new ModelKnightArmor(1.0f);
        }
        if (model2 == null) {
            model2 = new ModelKnightArmor(0.5f);
        }
        return model = CustomArmorHelper.getCustomArmorModel(entityLiving, itemStack, armorSlot, model, model1, model2);
    }*/
}
