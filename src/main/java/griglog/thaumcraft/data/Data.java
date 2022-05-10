package griglog.thaumcraft.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Data {
    @SubscribeEvent
    static void data(GatherDataEvent event){
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        //gen.addProvider(new BlockModelsProvider(gen, event.getExistingFileHelper()));
        gen.addProvider(new BlockStatesProvider(gen, event.getExistingFileHelper()));
        gen.addProvider(new ItemModelsProvider(gen, event.getExistingFileHelper()));
        gen.addProvider(new LootModifiersProvider(gen));
        gen.addProvider(new ModTagsProvider(gen, new ForgeBlockTagsProvider(gen, event.getExistingFileHelper()), event.getExistingFileHelper()));
        gen.addProvider(new BlockTagsProvider(gen, helper));
        gen.addProvider(new BlockDropsProvider(gen));
    }
    @SubscribeEvent
    static void regSerializers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event){
        event.getRegistry().register(new RefiningModifier.Serializer());
    }
}
