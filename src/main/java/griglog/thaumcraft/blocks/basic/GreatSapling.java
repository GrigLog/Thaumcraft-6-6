package griglog.thaumcraft.blocks.basic;

import griglog.thaumcraft.blocks.ModBlocks;
import griglog.thaumcraft.world.GreatTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Random;

public class GreatSapling extends SaplingBlock {
    public GreatSapling(){
        super(new GreatTree(), ModBlocks.saplingProps());
        setRegistryName("sapling_greatwood");
    }

    private boolean isTwoByTwo(IBlockReader world, BlockPos pos, int x, int y) {
        return world.getBlockState(pos.add(x, 0, y)).getBlock() == this
            && world.getBlockState(pos.add(x+1, 0, y)).getBlock() == this
            && world.getBlockState(pos.add(x, 0, y+1)).getBlock() == this
            && world.getBlockState(pos.add(x+1, 0, y+1)).getBlock() == this;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return isTwoByTwo(worldIn, pos, 0, 0);
    }

    @Override
    public boolean canBeReplacedByLogs(BlockState state, IWorldReader world, BlockPos pos) {
        return true;
    }
}
