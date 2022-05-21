package griglog.thaumcraft.events;

import griglog.thaumcraft.data.CustomReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Events {
    public static CustomReloadListener listener = new CustomReloadListener();
    @SubscribeEvent
    static void regReloadListener(AddReloadListenerEvent event){
        event.addListener(listener);
    }
}
