package griglog.thaumcraft;

import griglog.thaumcraft.aspect.Aspect;
import griglog.thaumcraft.aspect.Aspects;
import griglog.thaumcraft.aura.Aura;
import griglog.thaumcraft.blocks.ModBlocks;
import griglog.thaumcraft.blocks.tiles.JarTile;
import griglog.thaumcraft.client.JarRenderer;
import griglog.thaumcraft.client.SoundsTC;
import griglog.thaumcraft.data.CustomReloadListener;
import griglog.thaumcraft.entity.ModEntities;
import griglog.thaumcraft.items.ModItems;
import griglog.thaumcraft.utils.ItemUtils;
import griglog.thaumcraft.utils.Utils;
import griglog.thaumcraft.world.ModFeatures;
import griglog.thaumcraft.world.MagicalForestBiome;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import static griglog.thaumcraft.blocks.ModBlocks.*;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Register {
    @SubscribeEvent
    static void regItems(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(Utils.<Item>getFields(ModItems.class, Item.class, null).toArray(new Item[0]));
        for (Block b : ModItems.blockItems){
            event.getRegistry().register(ItemUtils.getItem(b));
        }
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
        event.getRegistry().registerAll(Utils.<TileEntityType<?>>getFields(ModBlocks.class, TileEntityType.class, null).toArray(new TileEntityType[0]));
    }

    @SubscribeEvent
    static void regEntities(RegistryEvent.Register<EntityType<?>> event){
        event.getRegistry().registerAll(Utils.<EntityType<?>>getFields(ModEntities.class, EntityType.class, null).toArray(new EntityType[0]));
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
    static void setupClient(FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.crucibleItem, (manager) -> new ItemRenderer(manager, Minecraft.getInstance().getItemRenderer()));
    }

    @SubscribeEvent
    static void addTextures(TextureStitchEvent.Pre event){
        if (event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)){
            event.addSprite(JarRenderer.EMPTY_TEXTURE);
            for (Aspect a : Aspects.aspects.values()){
                if (a != Aspects.EMPTY)
                    event.addSprite(new ResourceLocation(Thaumcraft.id, "aspects/" + a.tag));
            }
        }
    }

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(Aura.class, new Aura.Storage(), Aura::new);
    }
}
