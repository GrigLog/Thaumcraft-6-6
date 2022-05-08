package griglog.thaumcraft.items.armor;

import griglog.thaumcraft.client.models.ModelRobeArmor;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumMaterial;
import griglog.thaumcraft.items.interfaces.IVisDiscountGear;
import griglog.thaumcraft.items.interfaces.IWarpingGear;
import griglog.thaumcraft.utils.ItemUtils;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class CultistRobeArmor extends ArmorItem implements IVisDiscountGear, IWarpingGear {
    BipedModel<LivingEntity> model1 = new ModelRobeArmor();

    public CultistRobeArmor(EquipmentSlotType slot) {
        super(ThaumMaterial.CULTIST_ROBE, slot, ModTab.props());
        setRegistryName("crimson_robe_" + ItemUtils.slotName(slot));
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "thaumcraft:textures/entity/armor/cultist_robe_armor.png";
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.UNCOMMON;
    }

    public int getVisDiscount(ItemStack stack, PlayerEntity player) {
        return 1;
    }

    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        return (A) model1;
    }

    /*@OnlyIn(Dist.CLIENT)
    public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel _default) {
        if (model1 == null) {
            model1 = new ModelRobe(1.0f);
        }
        if (model2 == null) {
            model2 = new ModelRobe(0.5f);
        }
        return model = CustomArmorHelper.getCustomArmorModel(entityLiving, itemStack, armorSlot, model, model1, model2);
    }*/

    public int getWarp(ItemStack itemstack, PlayerEntity player) {
        return 1;
    }
}
