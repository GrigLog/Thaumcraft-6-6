package griglog.thaumcraft.blocks;

import griglog.thaumcraft.aspect.*;
import griglog.thaumcraft.blocks.tiles.TileJar;
import griglog.thaumcraft.client.SoundsTC;
import griglog.thaumcraft.items.interfaces.IEssentiaContainerItem;
import griglog.thaumcraft.utils.AuraHelper;
import griglog.thaumcraft.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapeCube;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

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
        return new TileJar();
    }

    /*
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(0.1875, 0.0, 0.1875, 0.8125, 0.75, 0.8125);
    }*/

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.matchesBlock(newState.getBlock())) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof TileJar) {
                spawnFilledJar(worldIn, pos, state, (TileJar) tile);
            } else {
                super.onReplaced(state, worldIn, pos, newState, isMoving);
            }
        }
    }

    private void spawnFilledJar(World world, BlockPos pos, BlockState state, TileJar te) {
        ItemStack drop = new ItemStack(this);
        if (te.ae.amount > 0)
            te.ae.write(Utils.safeTag(drop));
        spawnAsEntity(world, pos, drop);
    }

    /*

    public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
        TileEntity te = world.getTileEntity(pos);
        if (te != null && te instanceof TileJarBrain) {
            ((TileJarBrain) te).eatDelay = 40;
            if (!world.isRemote) {
                int var6 = world.rand.nextInt(Math.min(((TileJarBrain) te).xp + 1, 64));
                if (var6 > 0) {
                    TileJarBrain tileJarBrain = (TileJarBrain) te;
                    tileJarBrain.xp -= var6;
                    int xp = var6;
                    while (xp > 0) {
                        int var7 = EntityXPOrb.getXPSplit(xp);
                        xp -= var7;
                        world.addEntity(new ExperienceOrbEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, var7));
                    }
                    world.markAndNotifyBlock(pos, world.getChunkAt(pos), state, state, 3);
                    te.markDirty();
                }
            } else {
                world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundsTC.jar, SoundCategory.BLOCKS, 0.2f, 1.0f, false);
            }
        }
        if (te != null && te instanceof TileJarFillable && !((TileJarFillable) te).blocked && player.getHeldItem(hand).getItem() == ItemsTC.jarBrace) {
            ((TileJarFillable) te).blocked = true;
            player.getHeldItem(hand).shrink(1);
            if (world.isRemote) {
                world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundsTC.key, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
            } else {
                te.markDirty();
            }
        } else if (te != null && te instanceof TileJarFillable && player.isSneaking() && ((TileJarFillable) te).aspectFilter != null && side.ordinal() == ((TileJarFillable) te).facing) {
            ((TileJarFillable) te).aspectFilter = null;
            if (world.isRemote) {
                world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundsTC.page, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
            } else {
                world.addEntity(new ItemEntity(world, pos.getX() + 0.5f + side.getXOffset() / 3.0f, pos.getY() + 0.5f, pos.getZ() + 0.5f + side.getZOffset() / 3.0f, new ItemStack(ItemsTC.label)));
            }
        } else if (te != null && te instanceof TileJarFillable && player.isSneaking() && player.getHeldItem(hand).isEmpty()) {
            if (((TileJarFillable) te).aspectFilter == null) {
                ((TileJarFillable) te).aspect = null;
            }
            if (world.isRemote) {
                world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundsTC.jar, SoundCategory.BLOCKS, 0.4f, 1.0f, false);
                world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 0.5f, 1.0f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3f, false);
            } else {
                AuraHelper.polluteAura(world, pos, (float) ((TileJarFillable) te).amount, true);
            }
            ((TileJarFillable) te).amount = 0;
            te.markDirty();
        }
        return true;
    }

    @Override
    public boolean applyLabel(PlayerEntity player, BlockPos pos, Direction side, ItemStack labelstack) {
        TileEntity te = player.world.getTileEntity(pos);
        if (te == null || !(te instanceof TileJarFillable) || ((TileJarFillable) te).aspectFilter != null) {
            return false;
        }
        if (((TileJarFillable) te).amount == 0 && ((IEssentiaContainerItem) labelstack.getItem()).getAspects(labelstack) == null) {
            return false;
        }
        if (((TileJarFillable) te).amount == 0 && ((IEssentiaContainerItem) labelstack.getItem()).getAspects(labelstack) != null) {
            ((TileJarFillable) te).aspect = ((IEssentiaContainerItem) labelstack.getItem()).getAspects(labelstack).getAspects()[0];
        }
        onBlockPlacedBy(player.world, pos, player.world.getBlockState(pos), player, null);
        ((TileJarFillable) te).aspectFilter = ((TileJarFillable) te).aspect;
        player.world.markAndNotifyBlock(pos, player.world.getChunkAt(pos), player.world.getBlockState(pos), player.world.getBlockState(pos), 3);
        te.markDirty();
        player.world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundsTC.jar, SoundCategory.BLOCKS, 0.4f, 1.0f);
        return true;
    }

    public float getEnchantPowerBonus(World world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te != null && te instanceof TileJarBrain) {
            return 5.0f;
        }
        return super.getEnchantPowerBonus(world, pos);
    }

    @OnlyIn(Dist.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random rand) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileJarBrain && ((TileJarBrain) tile).xp >= ((TileJarBrain) tile).xpMax) {
            FXDispatcher.INSTANCE.spark(pos.getX() + 0.5f, pos.getY() + 0.8f, pos.getZ() + 0.5f, 3.0f, 0.2f + rand.nextFloat() * 0.2f, 1.0f, 0.3f + rand.nextFloat() * 0.2f, 0.5f);
        }
    }*/

    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    public int getComparatorInputOverride(BlockState state, World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileJar){
            int ess = ((TileJar) tile).ae.amount;
            if (ess > 0){
                float r = ess / 250.0f;
                return MathHelper.floor(r * 14.0f) + 1;
            }
        }
        return super.getComparatorInputOverride(state, world, pos);
    }
}
