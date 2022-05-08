package griglog.thaumcraft.items.armor;

import griglog.thaumcraft.client.models.ModelFortressArmor;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumMaterial;
import griglog.thaumcraft.items.interfaces.IGoggles;
import griglog.thaumcraft.utils.ItemUtils;
import griglog.thaumcraft.utils.Utils;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class FortressArmor extends ArmorItem implements IGoggles {
    ModelFortressArmor model = new ModelFortressArmor();
    public FortressArmor(EquipmentSlotType slot) {
        super(ThaumMaterial.FORTRESS, slot, ModTab.props());
        setRegistryName("fortress_" + ItemUtils.slotName(slot));
    }

    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        if (entity.ticksExisted % 20 == 0){
            int set = 0;
            for (ItemStack piece : entity.getArmorInventoryList()) {
                if (piece.getItem() instanceof FortressArmor) {
                    ++set;
                    if (MobEntity.getSlotForItemStack(piece) == EquipmentSlotType.HEAD) {
                        CompoundNBT tag = Utils.safeTag(piece);
                        model.mask = tag.contains("mask") ? tag.getInt("mask") : -1;
                        model.hasGoggles = tag.contains("goggles");
                    }
                }
            }
            model.pieces = set;
        }
        return (A) model;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "thaumcraft:textures/entity/armor/fortress_armor.png";
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.RARE;
    }


    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT tag = Utils.safeTag(stack);
        if (tag.contains("goggles")) {
            tooltip.add(new TranslationTextComponent("item.goggles.name").mergeStyle(TextFormatting.DARK_PURPLE));
        }
        if (tag.contains("mask")) {
            tooltip.add(new TranslationTextComponent("item.fortress_helm.mask." + tag.getInt("mask")).mergeStyle(TextFormatting.GOLD));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    /*
    public ISpecialArmor.ArmorProperties getProperties(LivingEntity player, ItemStack armor, DamageSource source, double damage, int slot) {
        int priority = 0;
        double ratio = this.damageReduceAmount / 25.0;
        if (source.isMagicDamage()) {
            priority = 1;
            ratio = this.damageReduceAmount / 35.0;
        } else if (source.isFireDamage() || source.isExplosion()) {
            priority = 1;
            ratio = this.damageReduceAmount / 20.0;
        } else if (source.isUnblockable()) {
            priority = 0;
            ratio = 0.0;
        }
        ISpecialArmor.ArmorProperties ap = new ISpecialArmor.ArmorProperties(priority, ratio, armor.getMaxDamage() + 1 - armor.getItemDamage());
        if (player instanceof PlayerEntity) {
            int q = 0;
            for (int a = 1; a < 4; ++a) {
                ItemStack piece = ((PlayerEntity) player).inventory.armorInventory.get(a);
                if (piece != null && !piece.isEmpty() && piece.getItem() instanceof FortressArmor) {
                    if (piece.hasTag() && piece.getTag().contains("mask")) {
                        ISpecialArmor.ArmorProperties armorProperties = ap;
                        ++armorProperties.Armor;
                    }
                    if (++q <= 1) {
                        ISpecialArmor.ArmorProperties armorProperties2 = ap;
                        ++armorProperties2.Armor;
                        ISpecialArmor.ArmorProperties armorProperties3 = ap;
                        ++armorProperties3.Toughness;
                    }
                }
            }
        }
        return ap;
    }*/


    /*
    public int getArmorDisplay(PlayerEntity player, ItemStack armor, int slot) {
        int q = 0;
        int ar = 0;
        for (int a = 1; a < 4; ++a) {
            ItemStack piece = player.inventory.armorInventory.get(a);
            if (!piece.isEmpty() && piece.getItem() instanceof FortressArmor) {
                if (piece.hasTag() && piece.getTag().contains("mask")) {
                    ++ar;
                }
                if (++q <= 1) {
                    ++ar;
                }
            }
        }
        return ar;
    }*/

    public boolean showIngamePopups(ItemStack itemstack, LivingEntity player) {
        return itemstack.hasTag() && itemstack.getTag().contains("goggles");
    }
}
