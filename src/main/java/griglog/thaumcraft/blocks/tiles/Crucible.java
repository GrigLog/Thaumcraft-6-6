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
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof CrucibleTile) {
                ((CrucibleTile) tile).clear(true);
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isRemote) {
            CrucibleTile tile = (CrucibleTile) world.getTileEntity(pos);
            if (tile != null && entity instanceof ItemEntity && !(entity instanceof CrucibleItem) && state.get(LEVEL) > 0) {
                tile.attemptSmelt((ItemEntity) entity);
            } else {
                if (++delay < 10) {
                    return;
                }
                delay = 0;
                if (entity instanceof LivingEntity && tile != null && tile.heat > 150 && state.get(LEVEL) > 0) {
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
                TileEntity tile = worldIn.getTileEntity(pos);
                if (tile instanceof CrucibleTile) {
                    ((CrucibleTile) tile).clear(false);
                    setWaterLevel(worldIn, pos, state, 0);
                }
            }
            return ActionResultType.CONSUME;
        }
        int i = state.get(LEVEL);
        Item item = is.getItem();
        if (item == Items.WATER_BUCKET) {
            if (i < 3) {
                if (!player.abilities.isCreativeMode) {
                    player.setHeldItem(handIn, new ItemStack(Items.BUCKET));
                }
                player.addStat(Stats.FILL_CAULDRON);
                setWaterLevel(worldIn, pos, state, 1);
                worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }
        if (i > 0 && item instanceof IDyeableArmorItem) {
            IDyeableArmorItem idyeablearmoritem = (IDyeableArmorItem)item;
            if (idyeablearmoritem.hasColor(is)) {
                idyeablearmoritem.removeColor(is);
                setWaterLevel(worldIn, pos, state, i - 1);
                player.addStat(Stats.CLEAN_ARMOR);
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.FAIL;
        }
        if (i > 0 && item instanceof BannerItem) {
            if (BannerTileEntity.getPatterns(is) > 0) {
                ItemStack itemstack2 = is.copy();
                itemstack2.setCount(1);
                BannerTileEntity.removeBannerData(itemstack2);
                player.addStat(Stats.CLEAN_BANNER);
                if (!player.abilities.isCreativeMode) {
                    is.shrink(1);
                    setWaterLevel(worldIn, pos, state, i - 1);
                }

                if (is.isEmpty()) {
                    player.setHeldItem(handIn, itemstack2);
                } else if (!player.inventory.addItemStackToInventory(itemstack2)) {
                    player.dropItem(itemstack2, false);
                } else if (player instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity)player).sendContainerToPlayer(player.container);
                }
            }

            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }
        if (i > 0 && item instanceof BlockItem) {
            Block block = ((BlockItem)item).getBlock();
            if (block instanceof ShulkerBoxBlock && !worldIn.isRemote()) {
                ItemStack itemstack1 = new ItemStack(Blocks.SHULKER_BOX, 1);
                if (is.hasTag()) {
                    itemstack1.setTag(is.getTag().copy());
                }

                player.setHeldItem(handIn, itemstack1);
                setWaterLevel(worldIn, pos, state, i - 1);
                player.addStat(Stats.CLEAN_SHULKER_BOX);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
}
