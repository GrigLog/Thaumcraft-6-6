package griglog.thaumcraft.items.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.ArrayList;

public interface IArchitect {

    /**
     * Returns the location that should be used as the starting point.
     */
    RayTraceResult getArchitectMOP(ItemStack stack, World world, LivingEntity player);

    /**
     * @return will this trigger on block highlighting event
     */
    boolean useBlockHighlight(ItemStack stack);

    /**
     * Returns a list of blocks that should be highlighted in world. The starting point is whichever block the player currently has highlighted in the world.
     */
    ArrayList<BlockPos> getArchitectBlocks(ItemUseContext context);

    /**
     * which axis should be displayed.
     */
    boolean showAxis(ItemStack stack, World world, PlayerEntity player, Direction side, EnumAxis axis);

    enum EnumAxis {
        // east / west
        X,
        // up / down
        Y,
        // north / south
        Z
    }

}