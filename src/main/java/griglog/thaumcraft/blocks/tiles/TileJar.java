package griglog.thaumcraft.blocks.tiles;

import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.aspect.Aspect;
import griglog.thaumcraft.aspect.AspectEntry;
import griglog.thaumcraft.aspect.Aspects;
import griglog.thaumcraft.aspect.ISingleAspectContainer;
import griglog.thaumcraft.blocks.ModBlocks;
import griglog.thaumcraft.utils.TileWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

public class TileJar extends TileEntity implements ISingleAspectContainer {
    public static TileEntityType<TileJar> type = TileWrapper.wrap("jar", TileJar::new, ModBlocks.jar);
    public TileJar(TileEntityType<?> type) {
        super(type);
    }

    public TileJar(){
        this(type);
    }

    public static int CAPACITY = 250;
    public AspectEntry ae = new AspectEntry(Aspects.EMPTY, 0);

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        ae.write(tag);
        return super.write(tag);
    }

    @Override
    public void read(BlockState bs, CompoundNBT tag) {
        ae.read(tag);
        super.read(bs, tag);
    }

    @Override
    public Aspect getAspect() {
        return ae.type;
    }

    @Override
    public void changeAspect(Aspect aspect) {
        ae.type = aspect;
    }

    @Override
    public int getAmount() {
        return ae.amount;
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

    @Override
    public void take(int amount) {
        ae.amount -= amount;
    }

    @Override
    public void add(int amount) {
        ae.amount += amount;
    }

    /*
    @Override
    public void readSyncNBT(CompoundNBT nbttagcompound) {
        aspect = Aspect.getAspect(nbttagcompound.getString("Aspect"));
        aspectFilter = Aspect.getAspect(nbttagcompound.getString("AspectFilter"));
        amount = nbttagcompound.getShort("Amount");
        facing = nbttagcompound.getByte("facing");
        blocked = nbttagcompound.getBoolean("blocked");
    }

    @Override
    public CompoundNBT writeSyncNBT(CompoundNBT nbttagcompound) {
        if (aspect != null) {
            nbttagcompound.putString("Aspect", aspect.getTag());
        }
        if (aspectFilter != null) {
            nbttagcompound.putString("AspectFilter", aspectFilter.getTag());
        }
        nbttagcompound.putShort("Amount", (short) amount);
        nbttagcompound.putByte("facing", (byte) facing);
        nbttagcompound.putBoolean("blocked", blocked);
        return nbttagcompound;
    }
     */

    /*
    public boolean takeFromContainer(Aspect tt, int am) {
        if (amount >= am && tt == aspect) {
            amount -= am;
            if (amount <= 0) {
                aspect = null;
                amount = 0;
            }
            syncTile(false);
            markDirty();
            return true;
        }
        return false;
    }

    public boolean takeFromContainer(AspectList ot) {
        return false;
    }

    public boolean doesContainerContainAmount(Aspect tag, int amt) {
        return amount >= amt && tag == aspect;
    }

    public boolean doesContainerContain(AspectList ot) {
        for (Aspect tt : ot.getAspects()) {
            if (amount > 0 && tt == aspect) {
                return true;
            }
        }
        return false;
    }

    public int containerContains(Aspect tag) {
        if (tag == aspect) {
            return amount;
        }
        return 0;
    }

    public boolean doesContainerAccept(Aspect tag) {
        return aspectFilter == null || tag.equals(aspectFilter);
    }*/

    /*
    @Override
    public boolean isConnectable(Direction face) {
        return face == Direction.UP;
    }

    @Override
    public boolean canInputFrom(Direction face) {
        return face == Direction.UP;
    }

    @Override
    public boolean canOutputTo(Direction face) {
        return face == Direction.UP;
    }

    @Override
    public void setSuction(Aspect aspect, int amount) {
    }

    @Override
    public int getMinimumSuction() {
        return (aspectFilter != null) ? 64 : 32;
    }

    @Override
    public Aspect getSuctionType(Direction loc) {
        return (aspectFilter != null) ? aspectFilter : aspect;
    }

    @Override
    public int getSuctionAmount(Direction loc) {
        if (amount >= 250) {
            return 0;
        }
        if (aspectFilter != null) {
            return 64;
        }
        return 32;
    }

    @Override
    public Aspect getEssentiaType(Direction loc) {
        return aspect;
    }

    @Override
    public int getEssentiaAmount(Direction loc) {
        return amount;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, Direction face) {
        return (canOutputTo(face) && takeFromContainer(aspect, amount)) ? amount : 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, Direction face) {
        return canInputFrom(face) ? (amount - addToContainer(aspect, amount)) : 0;
    }*/

    /*
    @Override
    public void update() {
        if (!world.isRemote && ++count % 5 == 0 && amount < 250) {
            fillJar();
        }
    }

    void fillJar() {
        TileEntity te = ThaumcraftApiHelper.getConnectableTile(world, pos, Direction.UP);
        if (te != null) {
            IEssentiaTransport ic = (IEssentiaTransport) te;
            if (!ic.canOutputTo(Direction.DOWN)) {
                return;
            }
            Aspect ta = null;
            if (aspectFilter != null) {
                ta = aspectFilter;
            } else if (aspect != null && amount > 0) {
                ta = aspect;
            } else if (ic.getEssentiaAmount(Direction.DOWN) > 0 && ic.getSuctionAmount(Direction.DOWN) < getSuctionAmount(Direction.UP) && getSuctionAmount(Direction.UP) >= ic.getMinimumSuction()) {
                ta = ic.getEssentiaType(Direction.DOWN);
            }
            if (ta != null && ic.getSuctionAmount(Direction.DOWN) < getSuctionAmount(Direction.UP)) {
                addToContainer(ta, ic.takeEssentia(ta, 1, Direction.DOWN));
            }
        }
    }

    @Override
    public boolean isBlocked() {
        return blocked;
    }*/
}
