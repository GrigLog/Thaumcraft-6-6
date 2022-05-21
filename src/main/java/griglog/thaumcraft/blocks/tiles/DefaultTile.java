package griglog.thaumcraft.blocks.tiles;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.Constants;

public class DefaultTile extends TileEntity {
    public DefaultTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public SUpdateTileEntityPacket getUpdatePacket(){
        return new SUpdateTileEntityPacket(getPos(), -1, write(new CompoundNBT()));
    }

    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt){
        read(getBlockState(), pkt.getNbtCompound());
    }

    public void saveAndSync(){
        markDirty();
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
    }
}
