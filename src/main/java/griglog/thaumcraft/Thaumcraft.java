package griglog.thaumcraft;

import griglog.thaumcraft.aspect.AspectEntry;
import griglog.thaumcraft.aspect.AspectList;
import griglog.thaumcraft.aspect.Aspects;
import griglog.thaumcraft.blocks.ModBlocks;
import griglog.thaumcraft.blocks.tiles.TileJar;
import griglog.thaumcraft.client.JarRenderer;
import griglog.thaumcraft.items.ModItems;
import griglog.thaumcraft.items.interfaces.IEssentiaContainerItem;
import griglog.thaumcraft.items.misc.Phial;
import griglog.thaumcraft.utils.ItemUtils;
import griglog.thaumcraft.world.ModFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Thaumcraft.id)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Thaumcraft {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String id = "thaumcraft";

    @SubscribeEvent
    static void setupClient(FMLClientSetupEvent event){
        BlockColors blockColors = event.getMinecraftSupplier().get().getBlockColors();
        blockColors.register((state, reader, pos, col) ->
                (reader != null && pos != null) ? BiomeColors.getFoliageColor(reader, pos) : FoliageColors.getDefault(),
            ModBlocks.greatLeaves);

        ItemColors itemColors = event.getMinecraftSupplier().get().getItemColors();
        itemColors.register((stack, tintIndex) -> {
            DyeableArmorItem item = (DyeableArmorItem) stack.getItem();
            return tintIndex == 0 ? 6961280 : item.getColor(stack);
        }, ModItems.clothBoots, ModItems.clothLegs, ModItems.clothChest, ModItems.voidRobeChest, ModItems.voidRobeLegs, ModItems.voidRobeHelm);
        itemColors.register((stack, colorIndex) -> {
            BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return blockColors.getColor(blockstate, null, null, colorIndex);
        }, ModBlocks.greatLeaves.asItem());
        itemColors.register((stack, tintIndex) -> {
            if (tintIndex == 1)
                return Phial.getAspect(stack).color;
            return -1;
        }, ModItems.phialFull);
        itemColors.register((stack, tintIndex) ->
            tintIndex == 1 && stack.hasTag() ? Aspects.getSafe(stack.getTag().getString("key")).color : -1,
            ModBlocks.jar.asItem());

        RenderTypeLookup.setRenderLayer(ModBlocks.silverSapling, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.greatSapling, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.jar, RenderType.getTranslucent());
        //RenderTypeLookup.setRenderLayer(ModBlocks.greatLeaves, RenderType.getCutoutMipped());
        ClientRegistry.bindTileEntityRenderer(TileJar.type, JarRenderer::new);

        event.enqueueWork(() ->
        ItemModelsProperties.registerProperty(ModBlocks.jar.asItem(), new ResourceLocation(Thaumcraft.id, "fill"),
            (stack, world, entity) -> {
            if (!stack.hasTag())
                return 0;
            float fill = (float) stack.getTag().getInt("amount") / TileJar.CAPACITY;
            return fill;
            })
        );
    }

    @SubscribeEvent
    static void setupCommon(FMLCommonSetupEvent event){
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Thaumcraft.id, "silver_tree_conf"), ModFeatures.silverTreeConf);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Thaumcraft.id, "great_tree_conf"), ModFeatures.greatTreeConf);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Thaumcraft.id, "big_tree_conf"), ModFeatures.bigTreeConf);
    }
}
