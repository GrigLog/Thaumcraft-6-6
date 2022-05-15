package griglog.thaumcraft.items.tools.void_;

import griglog.thaumcraft.api.IWarpingGear;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumTier;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class VoidSword extends SwordItem implements IWarpingGear {
    public VoidSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public VoidSword() {
        super(ThaumTier.VOID, 9, -2.4f,
            new Properties().group(ModTab.instance));
        setRegistryName("void_sword");
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, world, entity, itemSlot, isSelected);
        VoidHelper.repairTick(stack, entity);
    }

    @Override
    public boolean hitEntity(ItemStack is, LivingEntity target, LivingEntity hitter) {
        VoidHelper.applyWeakness(target, hitter);
        return super.hitEntity(is, target, hitter);
    }

    public int getWarp(ItemStack itemstack, PlayerEntity player) {
        return 1;
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("enchantment.special.sapless").mergeStyle(TextFormatting.GOLD));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
