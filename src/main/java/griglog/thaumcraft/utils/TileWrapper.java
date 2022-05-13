package griglog.thaumcraft.utils;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.function.Supplier;

public class TileWrapper {
    public static <T extends TileEntity> TileEntityType<T> wrap(String name, Supplier<? extends T> factoryIn, Block... validBlocks){
        return (TileEntityType<T>) TileEntityType.Builder.create(factoryIn, validBlocks).build(null).setRegistryName(name);
    }
}
