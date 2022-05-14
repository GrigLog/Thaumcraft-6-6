package griglog.thaumcraft.events;

import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.aura.AuraChunk;
import griglog.thaumcraft.aura.AuraHandler;
import griglog.thaumcraft.items.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class PlayerEvents {
    @SubscribeEvent
    static void tick(TickEvent.PlayerTickEvent event){
        if (event.phase == TickEvent.Phase.END && !event.player.world.isRemote && event.player.ticksExisted % 20 == 0){
            AuraChunk ac = AuraHandler.getAura((ServerWorld) event.player.world, event.player.getPosition());
            Thaumcraft.LOGGER.info(ac.vis);
        }
    }
}
