package griglog.thaumcraft.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class BlockUtils {
    public static boolean isBlockTouching(IWorld world, BlockPos pos, BlockState bs) {
        for (Direction face : Direction.values()) {
            if (world.getBlockState(pos.offset(face)) == bs) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlockTouching(IWorld world, BlockPos pos, Block bs) {
        for (Direction face : Direction.values()) {
            if (world.getBlockState(pos.offset(face)).getBlock() == bs) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlockTouching(IWorld world, BlockPos pos, Material mat, boolean solid) {
        for (Direction face : Direction.values()) {
            if (world.getBlockState(pos.offset(face)).getMaterial() == mat && (!solid || world.getBlockState(pos.offset(face)).isSolidSide(world, pos.offset(face), face.getOpposite()))) {
                return true;
            }
        }
        return false;
    }
}
