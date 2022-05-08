package griglog.thaumcraft.items.armor;

import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumMaterial;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

public class CultistLeaderArmor extends ArmorItem{
    BipedModel model1;
    BipedModel model2;
    BipedModel model;

    public CultistLeaderArmor(EquipmentSlotType slot) {
        super(ThaumMaterial.CULTIST_LEADER, slot, ModTab.props());
        model1 = null;
        model2 = null;
        model = null;
        setRegistryName("crimson_praetor_");
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "thaumcraft:textures/entity/armor/cultist_leader_armor.png";
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.RARE;
    }

    /*8
    @OnlyIn(Dist.CLIENT)
    public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel _default) {
        if (model1 == null) {
            model1 = new ModelLeaderArmor(1.0f);
        }
        if (model2 == null) {
            model2 = new ModelLeaderArmor(0.5f);
        }
        return model = CustomArmorHelper.getCustomArmorModel(entityLiving, itemStack, armorSlot, model, model1, model2);
    }*/
}
