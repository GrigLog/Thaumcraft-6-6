package griglog.thaumcraft.blocks.tiles;

import griglog.thaumcraft.aspect.Aspect;
import griglog.thaumcraft.aspect.AspectEntry;
import griglog.thaumcraft.aspect.Aspects;
import griglog.thaumcraft.api.aspect.ISingleAspectContainer;
import griglog.thaumcraft.blocks.ModBlocks;
import griglog.thaumcraft.utils.TileWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.Constants;

public class JarTile extends DefaultTile implements ISingleAspectContainer {
    public static TileEntityType<JarTile> type = TileWrapper.wrap(JarTile::new, ModBlocks.jar);
    public JarTile(TileEntityType<?> type) {
        super(type);
    }

    public JarTile(){
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
        saveAndSync();
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
        saveAndSync();
    }

    @Override
    public void add(int amount) {
        ae.amount += amount;
        saveAndSync();
    }
}
