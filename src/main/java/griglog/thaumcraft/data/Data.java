package griglog.thaumcraft.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Data {
    @SubscribeEvent
    static void data(GatherDataEvent event){
        DataGenerator gen = event.getGenerator();
        gen.addProvider(new ItemModelsProvider(gen, event.getExistingFileHelper()));
    }
}
