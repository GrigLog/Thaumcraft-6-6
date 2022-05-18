package griglog.thaumcraft.blocks.tiles;

import griglog.thaumcraft.blocks.ModBlocks;
import griglog.thaumcraft.entity.CrucibleItem;
import griglog.thaumcraft.items.ModTab;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class Crucible extends CauldronBlock {
    public Crucible(){
        super(AbstractBlock.Properties.create(Material.IRON, MaterialColor.STONE)
            .sound(SoundType.METAL)
            .setRequiresTool()
            .hardnessAndResistance(2.0F)
            .notSolid());
        setRegistryName("crucible");
    }
    int delay = 0;

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CrucibleTile();
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote) {
            CrucibleTile tile = (CrucibleTile) worldIn.getTileEntity(pos);
            if (tile != null) {
                tile.clear();
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isRemote) {
            CrucibleTile tile = (CrucibleTile) world.getTileEntity(pos);
            if (tile != null && entity instanceof ItemEntity && !(entity instanceof CrucibleItem)) {
                tile.attemptSmelt((ItemEntity) entity);
            } else {
                if (++delay < 10) {
                    return;
                }
                delay = 0;
                if (entity instanceof LivingEntity && tile != null && tile.heat > 150 && tile.water > 0) {
                    entity.attackEntityFrom(DamageSource.IN_FIRE, 1.0f);
                    world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.4f, 2.0f + world.rand.nextFloat() * 0.4f);
                }
            }
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack is = player.getHeldItem(handIn);
        if (is.isEmpty() && player.isSneaking()){
            if (!worldIn.isRemote) {
                CrucibleTile tile = (CrucibleTile) worldIn.getTileEntity(pos);
                if (tile != null) {
                    tile.clear();
                }
            }
            return ActionResultType.CONSUME;
        }
        CrucibleTile tile = (CrucibleTile) worldIn.getTileEntity(pos);
        if (tile == null){
            return ActionResultType.PASS;
        }
        Item item = is.getItem();
        if (item == Items.WATER_BUCKET) {
            if (tile.water < 1000) {
                if (!player.abilities.isCreativeMode) {
                    player.setHeldItem(handIn, new ItemStack(Items.BUCKET));
                }
                player.addStat(Stats.FILL_CAULDRON);
                tile.water = 1000;
                tile.syncClient();
                worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }
        return ActionResultType.PASS;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
