package griglog.thaumcraft.blocks.tiles;

import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.aspect.Aspect;
import griglog.thaumcraft.aspect.AspectEntry;
import griglog.thaumcraft.aspect.AspectList;
import griglog.thaumcraft.aspect.Aspects;
import griglog.thaumcraft.blocks.ModBlocks;
import griglog.thaumcraft.client.SoundsTC;
import griglog.thaumcraft.utils.AuraHelper;
import griglog.thaumcraft.utils.TileWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

public class CrucibleTile extends TileEntity implements ITickableTileEntity {
    public static final TileEntityType<CrucibleTile> type = TileWrapper.wrap(CrucibleTile::new, ModBlocks.crucible);
    public CrucibleTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }
    public CrucibleTile(){
        super(type);
    }

    public int heat = 0;
    public AspectList aspects = new AspectList();
    int counter = 0;


    @Override
    public void read(BlockState state, CompoundNBT tag) {
        aspects.read(tag);
        heat = tag.getInt("heat");
        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        aspects.write(tag);
        tag.putInt("heat", heat);
        return super.write(tag);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket(){
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("heat", heat);
        aspects.write(tag);
        return new SUpdateTileEntityPacket(getPos(), -1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt){
        CompoundNBT tag = pkt.getNbtCompound();
        heat = tag.getInt("heat");
        aspects.read(tag);
        ((ClientWorld)world).notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
    }

    public void syncClient(){
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
    }

    @Override
    public void tick() {
        ++counter;
        int prevheat = heat;
        int level = getBlockState().get(CauldronBlock.LEVEL);
        if (!world.isRemote) {
            if (level > 0){
                BlockState under = world.getBlockState(pos.down());
                if (under.getMaterial() == Material.LAVA || under.getMaterial() == Material.FIRE || under.getBlock() == Blocks.MAGMA_BLOCK) {
                    if (heat < 200 && ++heat == 151) {
                        markDirty();
                        syncClient();
                    }
                } else if (heat > 0 && --heat == 149) {
                    markDirty();
                    syncClient();
                }
            } else {
                if (heat > 0 && --heat == 149) {
                    markDirty();
                    syncClient();
                }
            }
            if (aspects.visSize() > 500) {
                spillRandom();
            } else if (level == 3){
                ((Crucible)getBlockState().getBlock()).setWaterLevel(world, pos, getBlockState(), 2);
            }
            if (counter >= 100) {
                spillRandom();
                counter = 0;
            }
        } else if (level > 0) {
            drawEffects();
        }
        Thaumcraft.LOGGER.info((world.isRemote ? "client " : "server ") + heat + " heat " + aspects.visSize() + " vis");
    }

    void drawEffects(){

    }

    public void spillRandom() {
        Thaumcraft.LOGGER.info("spill " + (world.isRemote ? "client" : "server"));
        if (aspects.size() > 0) {
            Aspect tag = aspects.getAspects()[world.rand.nextInt(aspects.getAspects().length)];
            aspects.reduce(tag, 1);
            AuraHelper.polluteAura(world, getPos(), (tag == Aspects.FLUX) ? 1.0f : 0.25f, true);
        }
        markDirty();
        syncClient();
    }

    public void clear(boolean destroying){
        int vs = aspects.visSize();
        BlockState state = getBlockState();
        if (state.get(CauldronBlock.LEVEL) > 0 || vs > 0) {
            AuraHelper.polluteAura(world, getPos(), vs * 0.25f, true);
            int f = aspects.getAmount(Aspects.FLUX);
            if (f > 0) {
                AuraHelper.polluteAura(world, getPos(), f * 0.75f, false);
            }
            aspects = new AspectList();
            //world.addBlockEvent(pos, ModBlocks.crucible, 2, 5);
            if (true) {
                ((Crucible)state.getBlock()).setWaterLevel(world, pos, state, 0);
                markDirty();
                syncClient();
            }
            //world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
        }
    }

    public void attemptSmelt(ItemEntity entity) {
        if (heat <= 150 || world == null)
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

    public ItemStack attemptSmelt(ItemStack item, PlayerEntity player) {
        boolean bubble = false;
        boolean craftDone = false;
        int stacksize = item.getCount();
        AspectEntry ae = new AspectEntry(Aspects.AIR, 10);
        ae.amount *= stacksize;

        int newVis = aspects.visSize() + ae.amount;
        int level = getBlockState().get(CauldronBlock.LEVEL);
        if (newVis > 500)
            ((Crucible)getBlockState().getBlock()).setWaterLevel(world, pos, getBlockState(), 3);
        else
            ((Crucible)getBlockState().getBlock()).setWaterLevel(world, pos, getBlockState(), 2);
        aspects.add(ae);
        markDirty();
        syncClient();
        item.setCount(0);
        return item;

        /*
        for (int a = 0; a < stacksize; ++a) {
            CrucibleRecipe rc = ThaumcraftCraftingManager.findMatchingCrucibleRecipe(player, aspects, item);
            if (rc != null && tank.getFluidAmount() > 0) {
                ItemStack out = rc.getRecipeOutput().copy();
                if (player != null) {
                    FMLCommonHandler.instance().firePlayerCraftingEvent(player, out, new InventoryFake(item));
                }
                aspects = rc.removeMatching(aspects);
                tank.drain(50, true);
                ejectItem(out);
                craftDone = true;
                --stacksize;
                counter = -250L;
            } else {
                AspectList ot = ThaumcraftCraftingManager.getObjectTags(item);
                if (ot != null) {
                    if (ot.size() != 0) {
                        for (Aspect tag : ot.getAspects()) {
                            aspects.add(tag, ot.getAmount(tag));
                        }
                        bubble = true;
                        --stacksize;
                        counter = -150L;
                    }
                }
            }
        }
        if (bubble) {
            world.playSound(null, pos, SoundsTC.bubble, SoundCategory.BLOCKS, 0.2f, 1.0f + world.rand.nextFloat() * 0.4f);
            syncTile(false);
            world.addBlockEvent(pos, BlocksTC.crucible, 2, 1);
        }
        if (craftDone) {
            syncTile(false);
            world.addBlockEvent(pos, BlocksTC.crucible, 99, 0);
        }
        markDirty();
        if (stacksize <= 0) {
            return null;
        }
        item.setCount(stacksize);
        return item;
        */
    }
}
