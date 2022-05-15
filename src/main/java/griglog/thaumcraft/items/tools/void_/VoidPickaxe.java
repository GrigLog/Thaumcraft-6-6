package griglog.thaumcraft.items.tools.void_;

import com.google.common.collect.ImmutableSet;
import griglog.thaumcraft.api.IWarpingGear;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumTier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class VoidPickaxe extends PickaxeItem implements IWarpingGear {
    public VoidPickaxe() {
        super(ThaumTier.VOID, 5, -2.8f, new Properties().group(ModTab.instance));
        setRegistryName("void_pick");
    }

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return ImmutableSet.of(ToolType.PICKAXE);
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
}
