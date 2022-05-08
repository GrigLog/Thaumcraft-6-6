package griglog.thaumcraft.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITaintBlock {
    void die(World p0, BlockPos p1, BlockState p2);
}
