package griglog.thaumcraft.blocks.tiles;

import griglog.thaumcraft.api.aspect.IAspectHolder;
import griglog.thaumcraft.aspect.*;
import griglog.thaumcraft.blocks.ModBlocks;
import griglog.thaumcraft.data.CrucibleRecipe;
import griglog.thaumcraft.entity.CrucibleItem;
import griglog.thaumcraft.utils.AuraHelper;
import griglog.thaumcraft.utils.TileWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

public class CrucibleTile extends DefaultTile implements ITickableTileEntity, IAspectHolder {
    public static final TileEntityType<CrucibleTile> type = TileWrapper.wrap(CrucibleTile::new, ModBlocks.crucible);
    public CrucibleTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }
    public CrucibleTile(){
        super(type);
    }

    public int heat = 0;
    public AspectList aspects = new AspectList();
    public int water = 0;
    int counter = 0;


    @Override
    public void read(BlockState state, CompoundNBT tag) {
        aspects.read(tag);
        heat = tag.getInt("heat");
        water = tag.getInt("water");
        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        aspects.write(tag);
        tag.putInt("heat", heat);
        tag.putInt("water", water);
        return super.write(tag);
    }

    @Override
    public void tick() {
        ++counter;
        int prevheat = heat;
        if (!world.isRemote) {
            if (water > 0){
                BlockState under = world.getBlockState(pos.down());
                if (under.getMaterial() == Material.LAVA || under.getMaterial() == Material.FIRE || under.getBlock() == Blocks.MAGMA_BLOCK) {
                    if (heat < 200 && ++heat == 151) {
                        markDirty();
                        saveAndSync();
                    }
                } else if (heat > 0 && --heat == 149) {
                    markDirty();
                    saveAndSync();
                }
            } else {
                if (heat > 0 && --heat == 149) {
                    markDirty();
                    saveAndSync();
                }
            }
            if (aspects.visSize() > 500) {
                spillRandom();
            }
            if (counter >= 100) {
                spillRandom();
                counter = 0;
            }
        } else if (water > 0) {
            drawEffects();
        }
        //Thaumcraft.LOGGER.info((world.isRemote ? "client " : "server ") + heat + " heat " + aspects.visSize() + " vis " + water + " water");
    }

    void drawEffects(){

    }

    public void spillRandom() {
        //Thaumcraft.LOGGER.info("spill " + (world.isRemote ? "client" : "server"));
        if (aspects.size() > 0) {
            Aspect tag = aspects.getEntries()[world.rand.nextInt(aspects.getEntries().length)];
            aspects.reduce(tag, 1);
            AuraHelper.polluteAura(world, getPos(), (tag == Aspects.FLUX) ? 1.0f : 0.25f, true);
        }
        markDirty();
        saveAndSync();
    }

    public void clear(){
        int vs = aspects.visSize();
        BlockState state = getBlockState();
        if (state.get(CauldronBlock.LEVEL) > 0 || vs > 0) {
            AuraHelper.polluteAura(world, getPos(), vs * 0.25f, true);
            int f = aspects.getAmount(Aspects.FLUX);
            if (f > 0) {
                AuraHelper.polluteAura(world, getPos(), f * 0.75f, false);
            }
            aspects = new AspectList();
            water = 0;
            markDirty();
            saveAndSync();
        }
    }

    public void attemptSmelt(ItemEntity entity) {
        if (heat <= 150 || water == 0 || world == null)
            return;
        ItemStack item = entity.getItem();
        UUID id = entity.getThrowerId();
        if (id != null) {
            ItemStack res = attemptSmelt(item, world.getPlayerByUuid(id));
            if (res == null || res.getCount() <= 0) {
                entity.remove();
            } else {
                item.setCount(res.getCount());
                entity.setItem(item);
            }
        }
    }

    public ItemStack attemptSmelt(ItemStack is, PlayerEntity player) {
        CrucibleRecipe cr = CrucibleRecipe.check(player.getServer().getRecipeManager(), aspects, is);
        if (cr != null){
            ItemStack res = cr.getRecipeOutput();
            aspects.reduce(cr.aspects);
            is.shrink(1);
            water -= cr.aspects.visSize();
            while (aspects.has(cr.aspects) && is.getCount() > 0 && water > 0) {
                res.setCount(res.getCount() + cr.getRecipeOutput().getCount());
                aspects.reduce(cr.aspects);
                is.shrink(1);
                water -= cr.aspects.visSize();
            }
            if (water < 0)
                water = 0;
            ejectItem(res);
        } else {
            AspectList list = ItemAspects.getAspects(is);
            aspects.add(list);
            is.setCount(0);
        }
        markDirty();
        saveAndSync();
        return is;
    }

    public void ejectItem(ItemStack items) {
        boolean first = true;
        do {
            ItemStack copy = items.copy();
            if (copy.getCount() > copy.getMaxStackSize()) {
                copy.setCount(copy.getMaxStackSize());
            }
            items.shrink(copy.getCount());
            CrucibleItem entityitem = new CrucibleItem(world, pos.getX() + 0.5f, pos.getY() + 0.71f, pos.getZ() + 0.5f, copy);
            entityitem.setMotion(
                first ? 0.0 : ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.01f),
                0.075f,
                first ? 0.0 : ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.01f));
            world.addEntity(entityitem);
            first = false;
        } while (items.getCount() > 0);
    }


    @Override
    public AspectList readList() {
        return aspects;
    }
}
