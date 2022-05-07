package griglog.thaumcraft.items.tools.void_;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumTier;
import griglog.thaumcraft.items.infusions.InfusionEnchantment;
import griglog.thaumcraft.items.interfaces.IWarpingGear;
import griglog.thaumcraft.utils.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class PrimalCrusher extends ToolItem implements IWarpingGear{
    private static final Set<Block> isEffective = ImmutableSet.<Block>builder()
        .addAll(PickaxeItem.EFFECTIVE_ON).addAll(AxeItem.EFFECTIVE_ON_BLOCKS).addAll(ShovelItem.EFFECTIVE_ON)
        /*.addAll(BlocksTC.taintRock, BlocksTC.taintSoil, BlocksTC.taintFeature, BlocksTC.taintLog, BlocksTC.taintFibre)*/.build();
    public PrimalCrusher() {
        super(10, -2.8f, ThaumTier.PRIMAL_VOID, isEffective, ModTab.props());
        setRegistryName("primal_crusher");
    }

    public boolean canHarvestBlock(BlockState bs) {
        return bs.getMaterial() != Material.WOOD && bs.getMaterial() != Material.LEAVES && bs.getMaterial() != Material.PLANTS;
    }

    static boolean isBreaking = false;
    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
        if (!player.world.isRemote) {
            RayTraceResult rtr = player.pick(10, 1, false);
            if (rtr.getType() == RayTraceResult.Type.BLOCK) {
                Direction side = ((BlockRayTraceResult) rtr).getFace();
                if (!isBreaking) {
                    isBreaking = true;
                    WorldUtils.mine5x5(player.world, pos, side, (ServerPlayerEntity) player, stack);
                    isBreaking = false;
                }
            }
        }
        return super.onBlockStartBreak(stack, pos, player);
    }

    /*public float getDestroySpeed(ItemStack stack, BlockState state) {
        return (state.getMaterial() != Material.IRON && state.getMaterial() != Material.ANVIL && state.getMaterial() != Material.ROCK) ? super.getDestroySpeed(stack, state) : efficiency;
    }*/

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return ImmutableSet.of(ToolType.SHOVEL, ToolType.PICKAXE);
    }

    public int getWarp(ItemStack itemstack, PlayerEntity player) {
        return 2;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        VoidHelper.repairTick(stack, entityIn);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)){
            ItemStack is = new ItemStack(this);
            InfusionEnchantment.add(is, InfusionEnchantment.REFINING, 1);
            items.add(is);
        }
    }
}
