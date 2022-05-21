package griglog.thaumcraft.data.providers;

import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.aspect.AspectList;
import griglog.thaumcraft.data.CustomReloadListener;
import griglog.thaumcraft.data.ModTags;
import griglog.thaumcraft.items.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static griglog.thaumcraft.aspect.Aspects.*;


public class ItemAspectsProvider implements IDataProvider {
    DataGenerator gen;
    StringBuilder sb;
    String PATH = "data/" + Thaumcraft.id + "/aspects/";
    public ItemAspectsProvider(DataGenerator generator){
        gen = generator;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        Path basePath = gen.getOutputFolder();

        Path path = basePath.resolve(PATH + CustomReloadListener.ITEM);
        sb = new StringBuilder();
        addItemAspects();
        writeIfNecessary(cache, path);

        path = basePath.resolve(PATH + CustomReloadListener.ITEM_TAG);
        sb = new StringBuilder();
        addItemTagAspects();
        writeIfNecessary(cache, path);

        path = basePath.resolve(PATH + CustomReloadListener.BLOCK_TAG);
        sb = new StringBuilder();
        addBlockTagAspects();
        writeIfNecessary(cache, path);
    }

    void writeIfNecessary(DirectoryCache cache, Path path) {
        String hash = HASH_FUNCTION.hashUnencodedChars(sb.toString()).toString();
        try {
            if (!Files.exists(path) || !Objects.equals(cache.getPreviousHash(path), hash)) {
                Files.createDirectories(path.getParent());
                Files.write(path, sb.toString().getBytes(StandardCharsets.UTF_8));
            }
            cache.recordHash(path, hash);
        } catch (IOException exc){
            exc.printStackTrace();
        }
    }


    void put(IForgeRegistryEntry<?> registered, AspectList list){
        sb.append(registered.getRegistryName().toString()).append(' ');
        sb.append(list.toString());
        sb.append('\n');
    }

    void add(IForgeRegistryEntry<?> registered, AspectList list){
        sb.append(registered.getRegistryName().toString()).append(" add ");
        sb.append(list.toString());
        sb.append('\n');
    }

    void putOverride(IForgeRegistryEntry<?> registered, AspectList list){
        sb.append(registered.getRegistryName().toString()).append(" override ");
        sb.append(list.toString());
        sb.append('\n');
    }

    void put(ITag.INamedTag<?> tag, AspectList list){
        sb.append(tag.getName()).append(' ');
        sb.append(list.toString());
        sb.append('\n');
    }

    void putOverride(ITag.INamedTag<?> tag, AspectList list){
        sb.append(tag.getName()).append(" override "); //TODO: make it override parent tags
        sb.append(list.toString());
        sb.append('\n');
    }

    @Override
    public String getName() {
        return "Item Aspects: " + Thaumcraft.id;
    }

    void addBlockAspects(){
        put(Blocks.NETHER_PORTAL, new AspectList().add(FIRE, 10).add(MOTION, 20).add(MAGIC, 10));
        put(Blocks.END_PORTAL, new AspectList().add(ELDRITCH, 10).add(MOTION, 20).add(MAGIC, 10));
    }

    void addItemAspects() {
        //TODO: dyes, armor, tools...
        put(Items.BEDROCK, new AspectList().add(VOID, 25).add(ENTROPY, 25).add(EARTH, 25).add(DARKNESS, 25));
        put(Items.GRASS_BLOCK, new AspectList().add(EARTH, 5).add(PLANT, 1));
        put(Items.FARMLAND, new AspectList().add(EARTH, 5).add(WATER, 2).add(ORDER, 2));
        put(Items.GRASS, new AspectList().add(EARTH, 5).add(PLANT, 2));
        put(Items.GRASS_PATH, new AspectList().add(EARTH, 5).add(PLANT, 2).add(ORDER, 2));
        put(Items.MYCELIUM, new AspectList().add(EARTH, 5).add(PLANT, 1).add(FLUX, 1));

        put(Items.SOUL_SAND, new AspectList().add(EARTH, 3).add(TRAP, 1).add(SOUL, 3));
        put(Items.MOSSY_COBBLESTONE, new AspectList().add(PLANT, 3));
        put(Items.MOSSY_STONE_BRICKS, new AspectList().add(PLANT, 1));
        put(Items.CRACKED_STONE_BRICKS, new AspectList().add(ENTROPY, 1));
        put(Items.CHISELED_STONE_BRICKS, new AspectList().add(ORDER, 1));
        put(Items.SMOOTH_SANDSTONE, new AspectList().add(ORDER, 1));
        put(Items.CHISELED_SANDSTONE, new AspectList().add(ORDER, 1));
        put(Items.SMOOTH_RED_SANDSTONE, new AspectList().add(ORDER, 1));
        put(Items.CHISELED_RED_SANDSTONE, new AspectList().add(ORDER, 1));
        put(Items.CLAY_BALL, new AspectList().add(WATER, 5).add(EARTH, 5));
        put(Items.TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 1));
        put(Items.BLACK_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.BLUE_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.BROWN_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.CYAN_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.GRAY_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.GREEN_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.LIGHT_BLUE_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.LIME_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.MAGENTA_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.ORANGE_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.PINK_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.PURPLE_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.RED_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.LIGHT_GRAY_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.WHITE_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.YELLOW_GLAZED_TERRACOTTA, new AspectList().add(WATER, 15).add(EARTH, 15).add(FIRE, 2).add(SENSES, 2));
        put(Items.TALL_GRASS, new AspectList().add(PLANT, 5).add(AIR, 1));
        put(Items.FERN, new AspectList().add(PLANT, 5).add(AIR, 1));
        put(Items.LILY_PAD, new AspectList().add(PLANT, 5).add(WATER, 1));
        put(Items.DEAD_BUSH, new AspectList().add(PLANT, 5).add(ENTROPY, 1));
        put(Items.VINE, new AspectList().add(PLANT, 5));


        put(Items.CACTUS, new AspectList().add(PLANT, 5).add(WATER, 5).add(AVERSION, 1));
        put(Items.BROWN_MUSHROOM, new AspectList().add(EARTH, 2));
        put(Items.RED_MUSHROOM, new AspectList().add(FIRE, 2));
        put(Items.BROWN_MUSHROOM_BLOCK, new AspectList().add(PLANT, 5).add(DARKNESS, 2).add(EARTH, 2));
        put(Items.RED_MUSHROOM_BLOCK, new AspectList().add(PLANT, 5).add(DARKNESS, 2).add(FIRE, 2));
        put(Items.SUGAR_CANE, new AspectList().add(PLANT, 5).add(WATER, 3).add(AIR, 2));
        put(Items.APPLE, new AspectList().add(PLANT, 5).add(LIFE, 5));
        put(Items.BAKED_POTATO, new AspectList().add(PLANT, 5).add(LIFE, 5));
        put(Items.POISONOUS_POTATO, new AspectList().add(PLANT, 5).add(DEATH, 5));
        put(Items.PUMPKIN, new AspectList().add(PLANT, 10));
        put(Items.MELON, new AspectList().add(PLANT, 10));
        put(Items.MELON_SLICE, new AspectList().add(PLANT, 1));
        put(Items.SPONGE, new AspectList().add(EARTH, 5).add(TRAP, 5).add(VOID, 5));
        put(Items.WET_SPONGE, new AspectList().add(EARTH, 5).add(TRAP, 5).add(WATER, 5));
        put(Items.SUGAR, new AspectList().add(PLANT, 5).add(WATER, 3).add(AIR, 2).add(DESIRE, 1).add(ENERGY, 1));
        put(Items.CAKE, new AspectList().add(PLANT, 15).add(LIFE, 13).add(WATER, 3).add(DESIRE, 2).add(ENERGY, 1).add(AIR, 1));
        put(Items.PUMPKIN_PIE, new AspectList().add(PLANT, 9).add(DESIRE, 1).add(LIFE, 2).add(WATER, 1));

        put(Items.EXPERIENCE_BOTTLE, new AspectList().add(MIND, 20));
        put(Items.NAME_TAG, new AspectList().add(MIND, 10).add(BEAST, 10));
        put(Items.IRON_HORSE_ARMOR, new AspectList().add(METAL, 15).add(PROTECT, 10).add(BEAST, 5));
        put(Items.GOLDEN_HORSE_ARMOR, new AspectList().add(METAL, 10).add(PROTECT, 15).add(BEAST, 5));
        put(Items.DIAMOND_HORSE_ARMOR, new AspectList().add(CRYSTAL, 15).add(PROTECT, 20).add(BEAST, 5));
        put(Items.MAGMA_BLOCK, new AspectList().add(FIRE, 10).add(EARTH, 5));
        put(Items.CHORUS_FLOWER, new AspectList().add(ELDRITCH, 5).add(SENSES, 5).add(PLANT, 5));
        put(Items.CHORUS_PLANT, new AspectList().add(ELDRITCH, 5).add(PLANT, 5));
        put(Items.CHORUS_FRUIT, new AspectList().add(ELDRITCH, 5).add(SENSES, 5).add(PLANT, 5));
        put(Items.POPPED_CHORUS_FRUIT, new AspectList().add(ELDRITCH, 5).add(SENSES, 5).add(PLANT, 4).add(FIRE, 1));
        put(Items.ICE, new AspectList().add(COLD, 20));
        put(Items.PACKED_ICE, new AspectList().add(COLD, 15).add(ORDER, 5));
        put(Items.SNOWBALL, new AspectList().add(COLD, 1));
        put(Items.COOKIE, new AspectList().add(DESIRE, 1).add(LIFE, 1).add(PLANT, 1));

        put(Blocks.TORCH, new AspectList().add(LIGHT, 5));
        put(Items.COBWEB, new AspectList().add(TRAP, 5).add(BEAST, 1));
        put(Items.FLINT, new AspectList().add(EARTH, 5).add(TOOL, 5));
        put(Items.STRING, new AspectList().add(BEAST, 5).add(CRAFT, 1));
        put(Items.LEATHER, new AspectList().add(BEAST, 5).add(PROTECT, 5));
        put(Items.ROTTEN_FLESH, new AspectList().add(MAN, 5).add(LIFE, 5).add(ENTROPY, 5));
        put(Items.SPIDER_EYE, new AspectList().add(SENSES, 5).add(BEAST, 5).add(DEATH, 5));

        put(Items.CHICKEN, new AspectList().add(BEAST, 5).add(LIFE, 5).add(AIR, 5));
        put(Items.COOKED_CHICKEN, new AspectList().add(CRAFT, 1).add(BEAST, 5).add(LIFE, 5));
        put(Items.PORKCHOP, new AspectList().add(BEAST, 5).add(LIFE, 5).add(EARTH, 5));
        put(Items.COOKED_PORKCHOP, new AspectList().add(CRAFT, 1).add(BEAST, 5).add(LIFE, 5));
        put(Items.BEEF, new AspectList().add(BEAST, 5).add(LIFE, 5).add(EARTH, 5));
        put(Items.COOKED_BEEF, new AspectList().add(CRAFT, 1).add(BEAST, 5).add(LIFE, 5));
        put(Items.MUTTON, new AspectList().add(BEAST, 5).add(LIFE, 5).add(EARTH, 5));
        put(Items.COOKED_MUTTON, new AspectList().add(CRAFT, 1).add(BEAST, 5).add(LIFE, 5));
        put(Items.RABBIT, new AspectList().add(BEAST, 5).add(LIFE, 5).add(EARTH, 5));
        put(Items.COOKED_RABBIT, new AspectList().add(CRAFT, 1).add(BEAST, 5).add(LIFE, 5));
        put(Items.RABBIT_HIDE, new AspectList().add(BEAST, 5).add(PROTECT, 2));
        put(Items.RABBIT_FOOT, new AspectList().add(BEAST, 5).add(PROTECT, 5).add(MOTION, 10).add(ALCHEMY, 5));
        put(Items.BLAZE_ROD, new AspectList().add(FIRE, 15).add(ENERGY, 5));
        put(Items.SADDLE, new AspectList().add(BEAST, 10).add(MOTION, 10).add(ORDER, 5));
        put(Items.GHAST_TEAR, new AspectList().add(UNDEAD, 5).add(SOUL, 10).add(ALCHEMY, 10));

        put(Items.SKELETON_SKULL, new AspectList().add(UNDEAD, 10));
        put(Items.WITHER_SKELETON_SKULL, new AspectList().add(UNDEAD, 10));
        put(Items.ZOMBIE_HEAD, new AspectList().add(MAN, 10));
        put(Items.PLAYER_HEAD, new AspectList().add(MAN, 10));
        put(Items.CREEPER_HEAD, new AspectList().add(ENTROPY, 5).add(FIRE, 5));
        put(Items.DRAGON_HEAD, new AspectList().add(FIRE, 10).add(DARKNESS, 10));
        put(Items.DRAGON_BREATH, new AspectList().add(DARKNESS, 10).add(ENTROPY, 10).add(FIRE, 10).add(ALCHEMY, 10));
        add(Items.CAULDRON, new AspectList().add(ALCHEMY, 15));
        add(Items.FERMENTED_SPIDER_EYE, new AspectList().add(ALCHEMY, 5));
        add(Items.BLAZE_POWDER, new AspectList().add(ALCHEMY, 5));
        add(Items.GLISTERING_MELON_SLICE, new AspectList().add(ALCHEMY, 5));
        add(Items.MAGMA_CREAM, new AspectList().add(ALCHEMY, 5));
        put(Items.TOTEM_OF_UNDYING, new AspectList().add(ORDER, 10).add(ENTROPY, 10).add(LIFE, 25).add(UNDEAD, 10));
        put(Items.SHULKER_SHELL, new AspectList().add(PROTECT, 10).add(ELDRITCH, 5).add(BEAST, 5).add(VOID, 5));
        put(Items.MUSIC_DISC_13, new AspectList().add(WATER, 5));
        put(Items.MUSIC_DISC_CAT, new AspectList().add(BEAST, 5));
        put(Items.MUSIC_DISC_CHIRP, new AspectList().add(EARTH, 5));
        put(Items.MUSIC_DISC_FAR, new AspectList().add(ELDRITCH, 5));
        put(Items.MUSIC_DISC_MALL, new AspectList().add(MAN, 5));
        put(Items.MUSIC_DISC_MELLOHI, new AspectList().add(CRAFT, 5));
        put(Items.MUSIC_DISC_STAL, new AspectList().add(DARKNESS, 5));
        put(Items.MUSIC_DISC_STRAD, new AspectList().add(ENERGY, 5));
        put(Items.MUSIC_DISC_WARD, new AspectList().add(LIFE, 5));
        put(Items.MUSIC_DISC_BLOCKS, new AspectList().add(TOOL, 5));
        put(Items.MUSIC_DISC_WAIT, new AspectList().add(TRAP, 5));
        put(Items.CHAINMAIL_HELMET, new AspectList().add(METAL, 42));
        put(Items.CHAINMAIL_CHESTPLATE, new AspectList().add(METAL, 67));
        put(Items.CHAINMAIL_LEGGINGS, new AspectList().add(METAL, 58));
        put(Items.CHAINMAIL_BOOTS, new AspectList().add(METAL, 33));
        add(Items.PAPER, new AspectList().add(MIND, 2));
        put(Items.BOOK, new AspectList().add(MIND, 5).add(PLANT, 3));
        put(Items.ENCHANTED_BOOK, new AspectList().add(MIND, 5).add(PLANT, 3));
        add(Items.BOOKSHELF, new AspectList().add(MIND, 8));
        put(Items.DRAGON_EGG, new AspectList().add(ELDRITCH, 15).add(BEAST, 15).add(DARKNESS, 15).add(MOTION, 15).add(MAGIC, 5));
        put(Items.END_PORTAL_FRAME, new AspectList().add(ELDRITCH, 10).add(ENERGY, 10).add(MOTION, 10).add(MAGIC, 5));
        put(Items.SPAWNER, new AspectList().add(BEAST, 20).add(MOTION, 20).add(UNDEAD, 20).add(MAGIC, 20));
        put(Items.ELYTRA, new AspectList().add(FLIGHT, 20).add(MOTION, 15));
        put(Items.END_ROD, new AspectList().add(FIRE, 1).add(LIGHT, 5));
        add(Items.CHEST, new AspectList().add(VOID, 15));
        add(Items.TRAPPED_CHEST, new AspectList().add(TRAP, 10));
        add(Items.ENDER_EYE, new AspectList().add(SENSES, 10).add(MAGIC, 5));
        add(Items.GLASS_BOTTLE, new AspectList().add(VOID, 5));
        put(Items.GOLDEN_APPLE,  new AspectList().add(METAL, 60).add(DESIRE, 60).add(MAGIC, 15).add(LIFE, 10));
        put(Items.ENCHANTED_GOLDEN_APPLE,  new AspectList().add(METAL, 100).add(DESIRE, 100).add(MAGIC, 30).add(LIFE, 15).add(PROTECT, 15));
        add(Items.BOWL, new AspectList().add(VOID, 5));
        add(Items.MUSHROOM_STEW, new AspectList().add(LIFE, 5));
        add(Items.MINECART, new AspectList().add(MOTION, 15));

        add(Items.FISHING_ROD, new AspectList().add(WATER, 10).add(TOOL, 5));
        add(Items.SHIELD, new AspectList().add(PROTECT, 20));
        add(Items.SPECTRAL_ARROW, new AspectList().add(SENSES, 10).add(MAGIC, 5));
        put(Items.BUCKET, new AspectList().add(METAL, 33).add(VOID, 5));
        //TODO: aspects for all forge buckets?
        //add(ForgeModContainer.getInstance().universalBucket), new AspectList(Items.BUCKET)));
        put(Items.WATER_BUCKET, new AspectList().add(METAL, 33).add(VOID, 5).add(WATER, 20));
        put(Items.LAVA_BUCKET, new AspectList().add(METAL, 33).add(VOID, 5).add(FIRE, 15).add(EARTH, 5));
        put(Items.MILK_BUCKET, new AspectList().add(METAL, 33).add(VOID, 5).add(LIFE, 10).add(BEAST, 5).add(WATER, 5));
        add(Items.BREWING_STAND, new AspectList().add(CRAFT, 15).add(ALCHEMY, 25));
        add(Items.STONE_BUTTON, new AspectList().add(MECHANISM, 5));
        add(Items.RAIL, new AspectList().add(MOTION, 10));
        add(Items.DETECTOR_RAIL, new AspectList().add(MECHANISM, 5).add(SENSES, 1));
        add(Items.POWERED_RAIL, new AspectList().add(MECHANISM, 5).add(ENERGY, 1));
        add(Items.ACTIVATOR_RAIL, new AspectList().add(MECHANISM, 5));

        add(Items.LEVER, new AspectList().add(MECHANISM, 5));
        add(Items.PISTON, new AspectList().add(MECHANISM, 10).add(MOTION, 10));
        add(Items.STICKY_PISTON, new AspectList().add(MECHANISM, 10).add(MOTION, 10));
        add(Items.JUKEBOX, new AspectList().add(SENSES, 20).add(MECHANISM, 10).add(AIR, 15));
        add(Items.NOTE_BLOCK, new AspectList().add(SENSES, 20).add(MECHANISM, 10).add(AIR, 15));

        add(Items.FURNACE, new AspectList().add(FIRE, 10));
        add(Items.ENCHANTING_TABLE, new AspectList().add(MAGIC, 25).add(CRAFT, 15));
        add(Items.CRAFTING_TABLE, new AspectList().add(CRAFT, 20));
        add(Items.CLOCK, new AspectList().add(MECHANISM, 10));
        add(Items.BEACON, new AspectList().add(AURA, 10).add(MAGIC, 10).add(EXCHANGE, 10));

        add(Items.CARROT_ON_A_STICK, new AspectList().add(MOTION, 5).add(DESIRE, 10));
        add(Items.FLOWER_POT, new AspectList().add(VOID, 5).add(PLANT, 5));
        add(Items.GOLDEN_CARROT, new AspectList().add(SENSES, 10).add(ALCHEMY, 5));
        add(Items.ENDER_CHEST, new AspectList().merge(EXCHANGE, 10).merge(MOTION, 10).merge(VOID, 20));
        add(Items.COMPARATOR, new AspectList().merge(MECHANISM, 15).merge(ORDER, 5).merge(SENSES, 5));
        add(Items.REPEATER, new AspectList().merge(MECHANISM, 15).merge(ENERGY, 10));
        add(Items.HOPPER, new AspectList().merge(MECHANISM, 5).merge(EXCHANGE, 10).merge(VOID, 5));
        add(Items.DROPPER, new AspectList().merge(MECHANISM, 5).merge(EXCHANGE, 10).merge(VOID, 5));
        add(Items.DISPENSER, new AspectList().merge(MECHANISM, 5).merge(EXCHANGE, 10).merge(VOID, 5));
        add(Items.TRIPWIRE_HOOK, new AspectList().merge(SENSES, 5).merge(MECHANISM, 5).merge(TRAP, 5));
        add(Items.DAYLIGHT_DETECTOR, new AspectList().merge(SENSES, 10).merge(LIGHT, 10).merge(MECHANISM, 5));
        //TODO: potion aspects
        /*for (Potion potiontype : Potion.REGISTRY) {
            ItemStack stack = PotionUtils.addPotionToItemStack(Items.POTIONITEM), potiontype);
            add(stack, getPotionAspects(stack).add(WATER, 5));
            ItemStack stack2 = PotionUtils.addPotionToItemStack(Items.TIPPED_ARROW), potiontype);
            add(stack2, getPotionAspects(stack2).add(AVERSION, 5));
            ItemStack stack3 = PotionUtils.addPotionToItemStack(Items.SPLASH_POTION), potiontype);
            add(stack3, getPotionAspects(stack3).add(ENERGY, 5));
            ItemStack stack4 = PotionUtils.addPotionToItemStack(Items.LINGERING_POTION), potiontype);
            add(stack4, getPotionAspects(stack4).add(TRAP, 5));
        }*/
        put(Items.INK_SAC, new AspectList().add(WATER, 2).add(BEAST, 2));
        put(Items.LAPIS_LAZULI, new AspectList().add(EARTH, 2).add(DESIRE, 2));
        put(Items.BONE_MEAL, new AspectList().add(LIFE, 2).add(DEATH, 1).add(PLANT, 1));
        put(Items.COAL, new AspectList().add(ENERGY, 10).add(FIRE, 10));
        put(Items.CHARCOAL, new AspectList().add(ENERGY, 10).add(FIRE, 10));


        put(ModItems.phialEmpty, new AspectList().add(VOID, 3));
        put(ModItems.phialFull, new AspectList());
        //add(BlocksTC.grassAmbient), new AspectList(Blocks.GRASS)).add(LIGHT, 5));
        /*
        addCrafted(BlocksTC.tableWood, new AspectList().add(TOOL, 1));
        addCrafted(BlocksTC.tableStone, new AspectList().add(TOOL, 1));
        add(BlocksTC.arcaneWorkbench), new AspectList(Blocks.CRAFTING_TABLE)).add(MAGIC, 5).add(AURA, 5));
        add(ItemsTC.tripleMeatTreat, new AspectList().add(LIFE, 10).add(DESIRE, 10));
        add(FluidUtil.getFilledBucket(new FluidStack(ConfigBlocks.FluidPure.instance, 1000)), new AspectList(Items.BUCKET)).add(MIND, 15).add(ORDER, 15));
        add(FluidUtil.getFilledBucket(new FluidStack(ConfigItems.FluidDeath.instance, 1000)), new AspectList(Items.BUCKET)).add(DEATH, 15).add(ENTROPY, 15));
        add("clusterIron", new AspectList().add(ORDER, 5).add(METAL, 15).add(EARTH, 5));
        add("clusterGold", new AspectList().add(ORDER, 5).add(METAL, 15).add(EARTH, 5).add(DESIRE, 10));
        add("clusterCinnabar", new AspectList().add(ORDER, 5).add(METAL, 15).add(EARTH, 5).add(ALCHEMY, 5).add(DEATH, 5));
        add("clusterQuartz", new AspectList().add(ORDER, 5).add(CRYSTAL, 10));
        add("oreCinnabar", new AspectList().add(METAL, 10).add(ALCHEMY, 5).add(DEATH, 5));
        add("oreAmber", new AspectList().add(TRAP, 10).add(CRYSTAL, 10));
        add("quicksilver", new AspectList().add(METAL, 10).add(DEATH, 5).add(ALCHEMY, 5));
        add("gemAmber", new AspectList().add(TRAP, 10).add(CRYSTAL, 10));
        add(ItemsTC.nuggets, 1, 10, new AspectList().add(EARTH, 5).add(ORDER, 5).add(METAL, 5));
        add(BlocksTC.crystalAir, new AspectList().add(AIR, 15).add(CRYSTAL, 10));
        add(BlocksTC.crystalFire, new AspectList().add(FIRE, 15).add(CRYSTAL, 10));
        add(BlocksTC.crystalWater, new AspectList().add(WATER, 15).add(CRYSTAL, 10));
        add(BlocksTC.crystalEarth, new AspectList().add(EARTH, 15).add(CRYSTAL, 10));
        add(BlocksTC.crystalOrder, new AspectList().add(ORDER, 15).add(CRYSTAL, 10));
        add(BlocksTC.crystalEntropy, new AspectList().add(ENTROPY, 15).add(CRYSTAL, 10));
        add(BlocksTC.crystalTaint, new AspectList().add(FLUX, 15).add(CRYSTAL, 10));
        add(BlocksTC.taintFibre, new AspectList().add(PLANT, 5).add(FLUX, 10));
        add(BlocksTC.taintCrust, new AspectList().add(LIFE, 5).add(FLUX, 5));
        add(BlocksTC.taintSoil, new AspectList().add(EARTH, 5).add(FLUX, 5));
        add(BlocksTC.taintGeyser, new AspectList().add(AURA, 5).add(WATER, 5).add(FLUX, 10));
        add(BlocksTC.taintRock, new AspectList().add(EARTH, 10).add(FLUX, 5));
        add(BlocksTC.taintFeature, 1, 0, new AspectList().add(AURA, 5).add(BEAST, 5).add(FLUX, 10));
        add(BlocksTC.taintLog, 1, 0, new AspectList().add(PLANT, 5).add(FLUX, 5));
        add(BlocksTC.logGreatwood, new AspectList().add(PLANT, 20).add(LIFE, 5));
        add(BlocksTC.logSilverwood, new AspectList().add(PLANT, 20).add(AURA, 5));
        add(BlocksTC.leafGreatwood, new AspectList().add(PLANT, 5));
        add(BlocksTC.leafSilverwood, new AspectList().add(PLANT, 5));
        add(BlocksTC.saplingGreatwood, new AspectList().add(PLANT, 15).add(LIFE, 5));
        add(BlocksTC.saplingSilverwood, new AspectList().add(PLANT, 15).add(AURA, 5));
        add(BlocksTC.shimmerleaf, new AspectList().add(PLANT, 5).add(AURA, 10).add(ENERGY, 5));
        add(BlocksTC.cinderpearl, new AspectList().add(PLANT, 5).add(AURA, 5).add(FIRE, 10));
        add(BlocksTC.vishroom, new AspectList().add(PLANT, 2).add(DEATH, 1).add(MAGIC, 1).add(ENTROPY, 1));
        add(BlocksTC.stoneAncient, new AspectList().add(EARTH, 5).add(ELDRITCH, 5));
        add(BlocksTC.stoneAncientTile, new AspectList().add(EARTH, 5).add(ELDRITCH, 5));
        add(BlocksTC.stoneAncientRock, new AspectList().add(EARTH, 5).add(ELDRITCH, 5));
        add(BlocksTC.stoneEldritchTile, new AspectList().add(EARTH, 5).add(ELDRITCH, 5));
        add(BlocksTC.stoneAncientDoorway, new AspectList().add(METAL, 5).add(ELDRITCH, 5).add(TRAP, 5));
        add(BlocksTC.stoneAncientGlyphed, new AspectList().add(METAL, 5).add(ELDRITCH, 5).add(MIND, 5));
        add(BlocksTC.stonePorous, new AspectList().add(EARTH, 5).add(VOID, 5));
        add(BlocksTC.researchTable, 1, 32767), new AspectList(BlocksTC.tableWood)).add(MIND, 5));
        add(ItemsTC.brain, new AspectList().add(LIFE, 5).add(MIND, 20).add(UNDEAD, 10));
        add(ItemsTC.lootBag, 1, 0, new AspectList().add(DESIRE, 10));
        add(ItemsTC.lootBag, 1, 1, new AspectList().add(DESIRE, 20));
        add(ItemsTC.lootBag, 1, 2, new AspectList().add(DESIRE, 30));
        add(BlocksTC.lootUrnCommon, new AspectList().add(DESIRE, 10).add(EARTH, 2));
        add(BlocksTC.lootUrnUncommon, new AspectList().add(DESIRE, 20).add(EARTH, 2));
        add(BlocksTC.lootUrnRare, new AspectList().add(DESIRE, 30).add(EARTH, 2));
        add(BlocksTC.lootCrateCommon, new AspectList().add(DESIRE, 10).add(PLANT, 2));
        add(BlocksTC.lootCrateUncommon, new AspectList().add(DESIRE, 20).add(PLANT, 2));
        add(BlocksTC.lootCrateRare, new AspectList().add(DESIRE, 30).add(PLANT, 2));
        add(ItemsTC.chunks, new AspectList().add(LIFE, 5).add(ENTROPY, 1));
        add(ItemsTC.salisMundus, new AspectList().add(MAGIC, 5).add(ENERGY, 5));
        add(BlocksTC.crucible), new AspectList(Items.CAULDRON, 1, 32767)).add(CRAFT, 20).add(ALCHEMY, 20));
        for (Block ca : BlocksTC.candles.values()) {
            addCrafted(ca, new AspectList().add(LIGHT, 5));
        }
        add(ItemsTC.thaumonomicon, 1, 32767), new AspectList(Items.BOOKSHELF)).merge(MAGIC, 10));
        addCrafted(BlocksTC.pedestalArcane, 1, 0, new AspectList().add(MAGIC, 3).add(AIR, 3));
        addCrafted(BlocksTC.pedestalAncient, 1, 1, new AspectList().add(MAGIC, 3).add(ELDRITCH, 3));
        addCrafted(BlocksTC.pedestalEldritch, 1, 2, new AspectList().add(MAGIC, 3).add(ELDRITCH, 3));
        addCrafted(ItemsTC.thaumometer, new AspectList().add(SENSES, 10).add(AURA, 10));
        addCrafted(ItemsTC.goggles, new AspectList().merge(SENSES, 10).merge(AURA, 10));
        addCrafted(BlocksTC.arcaneEar, new AspectList().add(SENSES, 20));
        add(ItemsTC.amuletVis, 1, 0, new AspectList().add(AURA, 20).add(METAL, 5).add(MAGIC, 5));
        add(ItemsTC.baubles, 1, 3, new AspectList().add(AURA, 5).add(METAL, 5).add(MAGIC, 20));
        add(ItemsTC.crimsonPlateChest, 1, 32767), new AspectList(Items.IRON_CHESTPLATE)).add(ELDRITCH, 5));
        add(ItemsTC.crimsonPraetorChest, 1, 32767), new AspectList(Items.IRON_CHESTPLATE)).add(ELDRITCH, 10));
        add(ItemsTC.crimsonRobeChest, 1, 32767), new AspectList(Items.LEATHER_CHESTPLATE)).add(MAGIC, 5).add(ELDRITCH, 5));
        add(ItemsTC.crimsonPlateLegs, 1, 32767), new AspectList(Items.IRON_LEGGINGS)).add(ELDRITCH, 5));
        add(ItemsTC.crimsonPraetorLegs, 1, 32767), new AspectList(Items.IRON_LEGGINGS)).add(ELDRITCH, 10));
        add(ItemsTC.crimsonRobeLegs, 1, 32767), new AspectList(Items.LEATHER_LEGGINGS)).add(MAGIC, 5).add(ELDRITCH, 5));
        add(ItemsTC.crimsonPlateHelm, 1, 32767), new AspectList(Items.IRON_HELMET)).add(ELDRITCH, 5));
        add(ItemsTC.crimsonPraetorHelm, 1, 32767), new AspectList(Items.IRON_HELMET)).add(ELDRITCH, 10));
        add(ItemsTC.crimsonRobeHelm, 1, 32767), new AspectList(Items.LEATHER_HELMET)).add(MAGIC, 5).add(ELDRITCH, 5));
        add(ItemsTC.crimsonBoots, 1, 32767), new AspectList(Items.IRON_BOOTS)).add(ELDRITCH, 5));
        add(ItemsTC.crimsonBlade, 1, 32767), new AspectList(Items.IRON_SWORD)).add(ELDRITCH, 10).add(DEATH, 10));
        for (Block ca : BlocksTC.banners.values()) {
            addCrafted(ca, new AspectList().add(ELDRITCH, 5));
        }
        add(ItemsTC.eldritchEye, new AspectList().add(ELDRITCH, 15).add(AURA, 15).add(SENSES, 15).add(SOUL, 15));
        add(ItemsTC.curio, 1, 0, new AspectList().add(MIND, 15).add(MAGIC, 15));
        add(ItemsTC.curio, 1, 1, new AspectList().add(MIND, 15).add(BEAST, 15));
        add(ItemsTC.curio, 1, 2, new AspectList().add(MIND, 15).add(DEATH, 15));
        add(ItemsTC.curio, 1, 3, new AspectList().add(MIND, 15).add(ELDRITCH, 15));
        add(ItemsTC.curio, 1, 4, new AspectList().add(MIND, 30));
        add(ItemsTC.curio, 1, 5, new AspectList().add(MIND, 15).add(FLUX, 15));
        add(ItemsTC.curio, 1, 6, new AspectList().add(MIND, 15).add(ELDRITCH, 5).add(SOUL, 5).add(MAGIC, 5));
        add(ItemsTC.runedTablet, new AspectList().add(TRAP, 15).add(MIND, 15).add(MECHANISM, 15));
        add(ItemsTC.celestialNotes, new AspectList().add(MIND, 5).add(DARKNESS, 5).add(LIGHT, 5));
        add(ItemsTC.primordialPearl, new AspectList().add(AIR, 10).add(FIRE, 10).add(WATER, 10).add(EARTH, 10).add(ORDER, 10).add(ENTROPY, 10));
        add(BlocksTC.eldritch, 1, 0, new AspectList().add(VOID, 10).add(ELDRITCH, 10));
        add(BlocksTC.eldritch, 1, 1, new AspectList().add(VOID, 10).add(ELDRITCH, 10));
        add(BlocksTC.eldritch, 1, 2, new AspectList().add(VOID, 10).add(ELDRITCH, 10));
        add(BlocksTC.eldritch, 1, 3, new AspectList().add(VOID, 10).add(ELDRITCH, 10));
        add(BlocksTC.eldritch, 1, 4, new AspectList().add(VOID, 10).add(ELDRITCH, 10).add(MECHANISM, 10));
        add(BlocksTC.eldritch, 1, 5, new AspectList().add(VOID, 10).add(ELDRITCH, 10));
        add(BlocksTC.eldritch, 1, 6, new AspectList().add(VOID, 10).add(ELDRITCH, 10).add(MOTION, 15));
        add(BlocksTC.eldritch, 1, 7, new AspectList().add(VOID, 10).add(ELDRITCH, 10).add(BEAST, 15));
    */
    }


    void addItemTagAspects(){
        put(Tags.Items.ORES, new AspectList().add(EARTH, 5));
        put(Tags.Items.DUSTS, new AspectList().add(ENTROPY, 1));
        put(Tags.Items.INGOTS, new AspectList().add(METAL, 10));
        put(ModTags.CLUSTERS, new AspectList().add(ORDER, 5).add(EARTH, 5));
        put(Tags.Items.STONE, new AspectList().add(EARTH, 5));
        put(Tags.Items.COBBLESTONE, new AspectList().add(EARTH, 5).add(ENTROPY, 1));
        put(Tags.Items.CROPS, new AspectList().add(PLANT, 5).add(LIFE, 5));
        put(Tags.Items.SEEDS, new AspectList().add(PLANT, 5).add(LIFE, 1));
        put(Tags.Items.MUSHROOMS, new AspectList().add(PLANT, 5).add(DARKNESS, 2));
        put(ItemTags.WOOL, new AspectList().add(BEAST, 15).add(CRAFT, 5));
        put(ItemTags.TALL_FLOWERS, new AspectList().add(PLANT, 5).add(AIR, 1).add(LIFE, 1).add(SENSES, 5));
        put(Tags.Items.SANDSTONE, new AspectList().add(EARTH, 15).add(ENTROPY, 15));
        put(ItemTags.STONE_BRICKS, new AspectList().add(EARTH, 3));
        put(Tags.Items.SLIMEBALLS, new AspectList().add(WATER, 5).add(LIFE, 5).add(ALCHEMY, 1));
        put(Tags.Items.FEATHERS, new AspectList().add(FLIGHT, 5).add(AIR, 5));
        put(Tags.Items.BONES, new AspectList().add(DEATH, 5).add(LIFE, 5));
        put(Tags.Items.EGGS, new AspectList().add(LIFE, 5).add(BEAST, 5));
        put(Tags.Items.GUNPOWDER, new AspectList().add(FIRE, 10).add(ENTROPY, 10).add(ALCHEMY, 5));
        put(Tags.Items.HEADS, new AspectList().add(DEATH, 10).add(SOUL, 10));

        put(Tags.Items.ORES_LAPIS, new AspectList().add(SENSES, 15));
        put(Tags.Items.ORES_DIAMOND, new AspectList().add(DESIRE, 15).add(CRYSTAL, 15));
        put(Tags.Items.GEMS_DIAMOND, new AspectList().add(CRYSTAL, 15).add(DESIRE, 15));
        put(Tags.Items.ORES_REDSTONE, new AspectList().add(ENERGY, 15));
        //add(Items.LIT_REDSTONE_ORE, new AspectList().add(EARTH, 5).add(ENERGY, 15));
        put(Tags.Items.ORES_EMERALD, new AspectList().add(DESIRE, 10).add(CRYSTAL, 15));
        put(Tags.Items.GEMS_EMERALD, new AspectList().add(CRYSTAL, 15).add(DESIRE, 10));
        put(Tags.Items.ORES_QUARTZ, new AspectList().add(CRYSTAL, 10));
        put(Tags.Items.GEMS_QUARTZ, new AspectList().add(CRYSTAL, 5));
        put(Tags.Items.ORES_IRON, new AspectList().add(METAL, 15));
        put(ModTags.DUSTS_IRON, new AspectList().add(METAL, 15));
        put(Tags.Items.INGOTS_IRON, new AspectList().add(METAL, 15));
        put(Tags.Items.ORES_GOLD, new AspectList().add(METAL, 10).add(DESIRE, 10));
        put(ModTags.DUSTS_GOLD, new AspectList().add(METAL, 10).add(DESIRE, 10));
        put(Tags.Items.INGOTS_GOLD, new AspectList().add(DESIRE, 10));
        put(Tags.Items.ORES_COAL, new AspectList().add(ENERGY, 15).add(FIRE, 15));
        putOverride(Tags.Items.DUSTS_REDSTONE, new AspectList().add(ENERGY, 10));
        putOverride(Tags.Items.DUSTS_GLOWSTONE, new AspectList().add(SENSES, 5).add(LIGHT, 10));
        put(ModTags.INGOTS_COPPER, new AspectList().add(EXCHANGE, 5));
        put(ModTags.DUSTS_COPPER, new AspectList().add(METAL, 10).add(EXCHANGE, 5));
        put(ModTags.ORES_COPPER, new AspectList().add(METAL, 10).add(EXCHANGE, 5));
        put(ModTags.CLUSTERS_COPPER, new AspectList().add(METAL, 15).add(EXCHANGE, 10));
        put(ModTags.INGOTS_TIN, new AspectList().add(CRYSTAL, 5));
        put(ModTags.DUSTS_TIN, new AspectList().add(METAL, 10).add(CRYSTAL, 5));
        put(ModTags.ORES_TIN, new AspectList().add(METAL, 10).add(CRYSTAL, 5));
        put(ModTags.CLUSTERS_TIN, new AspectList().add(METAL, 15).add(CRYSTAL, 10));
        put(ModTags.INGOTS_SILVER, new AspectList().add(DESIRE, 5));
        put(ModTags.DUSTS_SILVER, new AspectList().add(METAL, 10).add(DESIRE, 5));
        put(ModTags.ORES_SILVER, new AspectList().add(METAL, 10).add(DESIRE, 5));
        put(ModTags.CLUSTERS_SILVER, new AspectList().add(METAL, 15).add(DESIRE, 10));
        put(ModTags.INGOTS_LEAD, new AspectList().add(ORDER, 5));
        put(ModTags.DUSTS_LEAD, new AspectList().add(METAL, 10).add(ORDER, 5));
        put(ModTags.ORES_LEAD, new AspectList().add(METAL, 10).add(ORDER, 5));
        put(ModTags.CLUSTERS_LEAD, new AspectList().add(METAL, 15).add(ORDER, 10));
        put(ModTags.INGOTS_BRASS, new AspectList().add(TOOL, 5));
        put(ModTags.DUSTS_BRASS, new AspectList().add(METAL, 10).add(TOOL, 5));
        put(ModTags.INGOTS_BRONZE, new AspectList().add(TOOL, 5));
        put(ModTags.DUSTS_BRONZE, new AspectList().add(METAL, 10).add(TOOL, 5));
        put(ModTags.ORES_URANIUM, new AspectList().add(METAL, 10).add(ENERGY, 10));
        put(ModTags.INGOTS_URANIUM, new AspectList().add(DEATH, 5).add(ENERGY, 10));
        put(ModTags.GEMS_RUBY, new AspectList().add(CRYSTAL, 10).add(DESIRE, 10));
        put(ModTags.GEMS_SAPPHIRE, new AspectList().add(CRYSTAL, 10).add(DESIRE, 10));
        put(ModTags.INGOTS_STEEL, new AspectList().add(METAL, 15).add(ORDER, 5));
        put(Tags.Items.NUGGETS_GOLD, new AspectList().add(METAL, 1).add(DESIRE, 1));
        put(Tags.Items.NUGGETS_IRON, new AspectList().add(METAL, 1));

        put(Tags.Items.SAND, new AspectList().add(EARTH, 5).add(ENTROPY, 5));
        put(Tags.Items.END_STONES, new AspectList().add(EARTH, 5).add(DARKNESS, 5));
        put(Tags.Items.GRAVEL, new AspectList().add(EARTH, 5).add(ENTROPY, 2));
        put(Tags.Items.GLASS, new AspectList().add(CRYSTAL, 5));
        put(Tags.Items.INGOTS_BRICK, new AspectList().add(WATER, 5).add(EARTH, 5).add(FIRE, 1));
        put(Tags.Items.NETHERRACK, new AspectList().add(EARTH, 5).add(FIRE, 2));
        putOverride(Tags.Items.INGOTS_NETHER_BRICK, new AspectList().add(EARTH, 5).add(FIRE, 3).add(ORDER, 1));
        put(Tags.Items.OBSIDIAN, new AspectList().add(EARTH, 5).add(FIRE, 5).add(DARKNESS, 5));
        put(ItemTags.LOGS, new AspectList().add(PLANT, 20));
        put(ItemTags.SAPLINGS, new AspectList().add(PLANT, 15).add(LIFE, 5));
        put(ItemTags.LEAVES, new AspectList().add(PLANT, 5));
        put(Tags.Items.CROPS_CARROT,  new AspectList().add(SENSES, 5));
        put(Tags.Items.CROPS_POTATO, new AspectList().add(EARTH, 5));
        put(Tags.Items.CROPS_BEETROOT, new AspectList().add(DESIRE, 1));
        putOverride(Tags.Items.CROPS_NETHER_WART, new AspectList().add(PLANT, 1).add(FLUX, 2).add(ALCHEMY, 3));
        put(ItemTags.FISHES, new AspectList().add(BEAST, 5).add(LIFE, 5).add(WATER, 5));
        put(Tags.Items.ENDER_PEARLS, new AspectList().add(ELDRITCH, 10).add(MOTION, 15));
        put(ItemTags.MUSIC_DISCS, new AspectList().add(SENSES, 15).add(DESIRE, 10).add(AIR, 5));
        put(Tags.Items.NETHER_STARS, new AspectList().add(ELDRITCH, 10).add(MAGIC, 20).add(ORDER, 20).add(AURA, 10));
        put(Tags.Items.DUSTS_PRISMARINE, new AspectList().add(WATER, 5).add(EARTH, 5));
        put(Tags.Items.GEMS_PRISMARINE, new AspectList().add(WATER, 5).add(CRYSTAL, 5).add(LIGHT, 5));
        put(ItemTags.ARROWS, new AspectList().add(AVERSION, 5).add(FLIGHT, 1));
        put(ItemTags.DOORS, new AspectList().add(TRAP, 5).add(MECHANISM, 5));
        put(ItemTags.BOATS, new AspectList().add(WATER, 10).add(MOTION, 15));
        put(Tags.Items.FENCE_GATES, new AspectList().add(TRAP, 5).add(MECHANISM, 5));

        put(ItemTags.TRAPDOORS, new AspectList().add(TRAP, 5).add(MOTION, 5));
        put(ItemTags.BUTTONS,  new AspectList().add(MECHANISM, 5));
        put(ModTags.GEAR, new AspectList().add(MECHANISM, 5));
        put(ItemTags.FLOWERS, new AspectList().add(PLANT, 5).add(LIFE, 1).add(SENSES, 5));
    }

    void addBlockTagAspects(){
        put(BlockTags.PRESSURE_PLATES, new AspectList().add(MECHANISM, 5).add(SENSES, 5));
        put(Tags.Blocks.DIRT, new AspectList().add(EARTH, 5));
    }
}
