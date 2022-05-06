package griglog.thaumcraft.events;

import griglog.thaumcraft.items.infusions.BurrowingInfusion;
import griglog.thaumcraft.items.infusions.DestructiveInfusion;
import griglog.thaumcraft.items.infusions.InfusionEnchantment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ToolEvents {
    @SubscribeEvent
    static void blockBreak(BlockEvent.BreakEvent event){
        PlayerEntity player = event.getPlayer();
        ItemStack is = player.getHeldItem(player.getActiveHand());
        World world = player.world;
        BlockPos pos = event.getPos();
        int lvl;
        if (!world.isRemote) {
            for (InfusionEnchantment.Pair p : InfusionEnchantment.getPairList(is)){
                if (p.type == InfusionEnchantment.DESTRUCTIVE)
                    DestructiveInfusion.handle(pos, (ServerPlayerEntity) player, is);
                if (p.type == InfusionEnchantment.BURROWING){
                    if (BurrowingInfusion.handle((ServerPlayerEntity) player, pos, is)){
                        event.setCanceled(true);
                        return;
                    }
                }
            }
        }
    }
}
