package griglog.thaumcraft.items.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import griglog.thaumcraft.client.models.ModelFortressArmor;
import griglog.thaumcraft.client.models.ModelRobeArmor;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumMaterial;
import griglog.thaumcraft.items.interfaces.IGoggles;
import griglog.thaumcraft.items.interfaces.IVisDiscountGear;
import griglog.thaumcraft.items.interfaces.IWarpingGear;
import griglog.thaumcraft.items.tools.void_.VoidHelper;
import griglog.thaumcraft.utils.ItemUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.w3c.dom.Attr;

import java.util.UUID;

public class VoidRobeArmor extends DyeableArmorItem implements IVisDiscountGear, IGoggles, IWarpingGear {
    BipedModel<LivingEntity> model = new ModelRobeArmor();
    public VoidRobeArmor(EquipmentSlotType slot) {
        super(ThaumMaterial.VOID_ROBE, slot, ModTab.props());
        setRegistryName("void_robe_" + ItemUtils.slotName(slot));
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return (type == null) ? "thaumcraft:textures/entity/armor/void_robe_armor_overlay.png" : "thaumcraft:textures/entity/armor/void_robe_armor.png";
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.EPIC;
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

    public boolean showIngamePopups(ItemStack itemstack, LivingEntity player) {
        EquipmentSlotType type = ((ArmorItem) itemstack.getItem()).getEquipmentSlot();
        return type == EquipmentSlotType.HEAD;
    }

    public int getVisDiscount(ItemStack stack, PlayerEntity player) {
        return 5;
    }

    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        return (A) model;
    }

    public int getColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 0x6A3880;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        if (equipmentSlot != slot)
            return super.getAttributeModifiers(equipmentSlot);
        Multimap<Attribute, AttributeModifier> attribs = HashMultimap.create(super.getAttributeModifiers(slot));
        //UUID uuid = new UUID((getRegistryName() + slot.toString()).hashCode(), 0);
        attribs.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier("Void robe modifier " + slot, 0.2, AttributeModifier.Operation.ADDITION));
        return attribs;
    }

    /*
    public ISpecialArmor.ArmorProperties getProperties(LivingEntity player, ItemStack armor, DamageSource source, double damage, int slot) {
        int priority = 0;
        double ratio = this.damageReduceAmount / 25.0;
        if (source.isMagicDamage()) {
            priority = 1;
            ratio = this.damageReduceAmount / 35.0;
        } else if (source.isUnblockable()) {
            priority = 0;
            ratio = 0.0;
        }
        return new ISpecialArmor.ArmorProperties(priority, ratio, armor.getMaxDamage() + 1 - armor.getItemDamage());
    }

    public int getArmorDisplay(PlayerEntity player, ItemStack armor, int slot) {
        return this.damageReduceAmount;
    }
    */

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getWorld();
        BlockState bs = world.getBlockState(context.getPos());
        if (!world.isRemote && bs.getBlock() == Blocks.CAULDRON){
            int level = bs.get(CauldronBlock.LEVEL);
            if (level > 0){
                removeColor(stack);
            }
            ((CauldronBlock)bs.getBlock()).setWaterLevel(world, context.getPos(), bs, level - 1);
            return ActionResultType.SUCCESS;
        }
        return super.onItemUseFirst(stack, context);
    }

    public int getWarp(ItemStack itemstack, PlayerEntity player) {
        return 3;
    }
}
