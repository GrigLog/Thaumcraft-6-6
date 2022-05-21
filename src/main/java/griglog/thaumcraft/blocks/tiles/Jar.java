package griglog.thaumcraft.blocks.tiles;

import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.aspect.*;
import griglog.thaumcraft.blocks.tiles.JarTile;
import griglog.thaumcraft.client.SoundsTC;
import griglog.thaumcraft.utils.Utils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Jar extends Block {
    public Jar(Properties props) {
        super(props);
    }
    public Jar(){
        super(Properties.create(Material.GLASS).sound(SoundsTC.JAR).notSolid());
        setRegistryName("jar_normal");
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new JarTile();
    }


    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(3, 0, 3, 13, 12, 13);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        //Thaumcraft.LOGGER.info("harvested");
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        //Thaumcraft.LOGGER.info("replaced");
        if (!state.matchesBlock(newState.getBlock())) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof JarTile) {
                spawnFilledJar(worldIn, pos, state, (JarTile) tile);
                worldIn.removeTileEntity(pos);
            } else {
                super.onReplaced(state, worldIn, pos, newState, isMoving);
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasTag()) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof JarTile) {
                ((JarTile) tile).ae = new AspectEntry(stack.getTag());
            }
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    private void spawnFilledJar(World world, BlockPos pos, BlockState state, JarTile te) {
        ItemStack drop = new ItemStack(this);
        if (te.ae.amount > 0)
            te.ae.write(Utils.safeTag(drop));
        spawnAsEntity(world, pos, drop);
    }

    @Override
    public BlockRenderType getRenderType(BlockState bs) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    public int getComparatorInputOverride(BlockState state, World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof JarTile){
            int ess = ((JarTile) tile).ae.amount;
            if (ess > 0){
                float r = ess / 250.0f;
                return MathHelper.floor(r * 14.0f) + 1;
            }
        }
        return super.getComparatorInputOverride(state, world, pos);
    }
}
