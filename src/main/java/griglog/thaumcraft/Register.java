package griglog.thaumcraft;

import griglog.thaumcraft.aura.Aura;
import griglog.thaumcraft.blocks.ModBlocks;
import griglog.thaumcraft.blocks.tiles.TileJar;
import griglog.thaumcraft.client.SoundsTC;
import griglog.thaumcraft.items.ModItems;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.utils.Utils;
import griglog.thaumcraft.world.ModFeatures;
import griglog.thaumcraft.world.MagicalForestBiome;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Register {
    @SubscribeEvent
    static void regItems(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(Utils.<Item>getFields(ModItems.class, Item.class, null).toArray(new Item[0]));
        Utils.<Block>getFields(ModBlocks.class, Block.class, null)
            .forEach(b -> event.getRegistry().register(new BlockItem(b, ModTab.props()).setRegistryName(b.getRegistryName())));
    }

    @SubscribeEvent
    static void regBlocks(RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(Utils.<Block>getFields(ModBlocks.class, Block.class, null).toArray(new Block[0]));
    }

    @SubscribeEvent
    static void regFeatures(RegistryEvent.Register<Feature<?>> event) {
        event.getRegistry().registerAll(ModFeatures.silverTree, ModFeatures.greatTree, ModFeatures.bigTree);
    }

    @SubscribeEvent
    static void regTiles(RegistryEvent.Register<TileEntityType<?>> event){
        event.getRegistry().registerAll(TileJar.type);
    }

    @SubscribeEvent
    static void registerBiome(RegistryEvent.Register<Biome> event) {
        event.getRegistry().register(MagicalForestBiome.BIOME);
        BiomeDictionary.addTypes(MagicalForestBiome.KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MAGICAL);
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(MagicalForestBiome.KEY, 10));
    }

    @SubscribeEvent
    static void regSounds(RegistryEvent.Register<SoundEvent> event){
        Utils.<SoundEvent>getFields(SoundsTC.class, SoundEvent.class, null)
            .forEach((sound) -> event.getRegistry().register(sound));
    }

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(Aura.class, new Aura.Storage(), Aura::new);
    }
}
