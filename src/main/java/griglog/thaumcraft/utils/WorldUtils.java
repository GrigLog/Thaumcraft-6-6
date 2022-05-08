package griglog.thaumcraft.utils;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldUtils {
    public static boolean tryBreakBlock(World world, BlockPos pos, ServerPlayerEntity player, ItemStack is) {
        BlockState bs = world.getBlockState(pos);
        boolean unminable = bs.getPlayerRelativeBlockHardness(player, world, pos) == 0;
        if (!world.isRemote && !bs.isAir() && !unminable && (!bs.getRequiresTool() || is.canHarvestBlock(bs)) && is.getDestroySpeed(bs) >= 1
            /*&& ForgeHooks.onBlockBreakEvent(world, player.interactionManager.getGameType(), player, pos) != -1*/) { //TODO: test on servers with regions
            world.destroyBlock(pos, !player.isCreative(), player);
            is.attemptDamageItem(1, world.rand, player);
            return true;
            //player.interactionManager.tryHarvestBlock(pos);
        }
        return false;
    }

    public static boolean isLog(BlockState bs){
        return bs.getMaterial() == Material.WOOD || bs.getMaterial() == Material.NETHER_WOOD || bs.isIn(BlockTags.LOGS);
    }

    public static void mine5x5(World world, BlockPos pos, Direction side, ServerPlayerEntity player, ItemStack is) {
        switch (side) {
            case UP:
            case DOWN:
                for (int x = -2; x <= 2; x++) {
                    for (int z = -2; z <= 2; z++) {
                        tryBreakBlock(world, pos.add(x, 0, z), player, is);
                    }
                }
                break;

            case SOUTH:
            case NORTH:
                for (int x = -2; x <= 2; x++) {
                    for (int y = -1; y <= 3; y++) {
                        tryBreakBlock(world, pos.add(x, y, 0), player, is);
                    }
                }
                break;

            case EAST:
            case WEST:
                for (int z = -2; z <= 2; z++) {
                    for (int y = -1; y <= 3; y++) {
                        tryBreakBlock(world, pos.add(0, y, z), player, is);
                    }
                }
                break;
        }
    }

    public static void mine3x3(World world, BlockPos pos, Direction side, ServerPlayerEntity player, ItemStack is) {
        switch (side) {
            case UP:
            case DOWN:
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        tryBreakBlock(world, pos.add(x, 0, z), player, is);
                    }
                }
                break;

            case SOUTH:
            case NORTH:
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        tryBreakBlock(world, pos.add(x, y, 0), player, is);
                    }
                }
                break;

            case EAST:
            case WEST:
                for (int z = -1; z <= 1; z++) {
                    for (int y = -1; y <= 1; y++) {
                        tryBreakBlock(world, pos.add(0, y, z), player, is);
                    }
                }
                break;
        }
    }
}
