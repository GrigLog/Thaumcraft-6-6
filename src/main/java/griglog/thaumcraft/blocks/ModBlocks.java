package griglog.thaumcraft.blocks;

import griglog.thaumcraft.blocks.basic.GreatLog;
import griglog.thaumcraft.blocks.basic.GreatSapling;
import griglog.thaumcraft.blocks.basic.SilverLog;
import griglog.thaumcraft.blocks.tiles.Crucible;
import griglog.thaumcraft.blocks.tiles.CrucibleTile;
import griglog.thaumcraft.blocks.tiles.Jar;
import griglog.thaumcraft.blocks.tiles.JarTile;
import griglog.thaumcraft.world.SilverTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;

public class ModBlocks {
    public static final Material TAINT = new Material.Builder(MaterialColor.PURPLE).doesNotBlockMovement().build();
    public static final Block silverLog = new SilverLog();
    public static final Block greatLog = new GreatLog();
    public static final Block silverLeaves = new LeavesBlock(leavesProps()).setRegistryName("leaves_silverwood");
    public static final Block greatLeaves = new LeavesBlock(leavesProps()).setRegistryName("leaves_greatwood");
    public static final Block greatSapling = new GreatSapling();
    public static final Block silverSapling = new SaplingBlock(new SilverTree(), saplingProps()).setRegistryName("sapling_silverwood");
    public static final Block jar = new Jar();
    public static final Block crucible = new Crucible();

    public static final TileEntityType<JarTile> jarTile = JarTile.type;
    public static final TileEntityType<CrucibleTile> crucibleTile = CrucibleTile.type;


    public static AbstractBlock.Properties props(Material mat){
        return AbstractBlock.Properties.create(mat);
    }

    public static AbstractBlock.Properties saplingProps(){
        return AbstractBlock.Properties.create(Material.PLANTS)
            .doesNotBlockMovement()
            .tickRandomly().zeroHardnessAndResistance()
            .sound(SoundType.PLANT);
    }
    public static AbstractBlock.Properties leavesProps() {
        return AbstractBlock.Properties.create(Material.LEAVES)
            .hardnessAndResistance(0.2F)
            .tickRandomly()
            .sound(SoundType.PLANT)
            .notSolid()
            .setAllowsSpawn((state, reader, pos, entity) -> entity == EntityType.OCELOT || entity == EntityType.PARROT)
            .setSuffocates((state, reader, pos) -> false)
            .setBlocksVision((state, reader, pos) -> false);
    }
}
