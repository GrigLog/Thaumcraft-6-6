package griglog.thaumcraft.items.infusions;

import griglog.thaumcraft.utils.WorldUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class DestructiveInfusion{
    static boolean isBreaking = false;
    public static void handle(BlockPos pos, ServerPlayerEntity player, ItemStack stack){
        RayTraceResult rtr = player.pick(10, 1, false);
        if (rtr.getType() == RayTraceResult.Type.BLOCK) {
            Direction side = ((BlockRayTraceResult) rtr).getFace();
            if (!isBreaking) {
                isBreaking = true;
                WorldUtils.mine3x3(player.world, pos, side, player, stack);
                isBreaking = false;
            }
        }
    }
}
