package griglog.thaumcraft.items.infusions;

import griglog.thaumcraft.utils.WorldUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SChangeBlockPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Map;

public class BurrowingInfusion {
    public static boolean handle(ServerPlayerEntity player, BlockPos pos, ItemStack is){
        BlockState bs = player.world.getBlockState(pos);
        if (!player.isSneaking() && ForgeHooks.isToolEffective(player.world, pos, is) && (WorldUtils.isLog(bs) || isOre(bs))){
            is.attemptDamageItem(1, player.world.rand, player);
            breakFurthestBlock(player.world, pos, bs, player, is);
            return true;
        }
        return false;
    }

    public static boolean isOre(BlockState bs){
        return Tags.Blocks.ORES.contains(bs.getBlock());
    }

    static BlockPos lastPos;
    static double lastdistance;

    public static boolean breakFurthestBlock(World world, BlockPos pos, BlockState bs, ServerPlayerEntity player, ItemStack is) {
        lastPos = new BlockPos(pos);
        lastdistance = 0.0;
        int reach = WorldUtils.isLog(bs) ? 2 : 1;
        findBlocks(world, pos, bs, reach);
        boolean worked = WorldUtils.tryBreakBlock(world, lastPos, player, is);
        //TODO: destroy leaves
        /*world.markAndNotifyBlock(pos, world.getChunkAt(pos), bs, bs, 3, 512);
        if (worked && isLog(bs)) {
            world.markAndNotifyBlock(pos, world.getChunkAt(pos), bs, bs, 3, 512);
            for (int xx = -3; xx <= 3; ++xx) {
                for (int yy = -3; yy <= 3; ++yy) {
                    for (int zz = -3; zz <= 3; ++zz) {
                        world.getPendingBlockTicks().scheduleTick(lastPos.add(xx, yy, zz), world.getBlockState(lastPos.add(xx, yy, zz)).getBlock(), 50 + world.rand.nextInt(75), TickPriority.HIGH);
                    }
                }
            }
        }*/
        return worked;
    }

    public static void findBlocks(World world, BlockPos pos, BlockState block, int reach) {
        for (int xx = -reach; xx <= reach; ++xx) {
            for (int yy = reach; yy >= -reach; --yy) {
                for (int zz = -reach; zz <= reach; ++zz) {
                    if (Math.abs(lastPos.getX() + xx - pos.getX()) > 24) {
                        return;
                    }
                    if (Math.abs(lastPos.getY() + yy - pos.getY()) > 48) {
                        return;
                    }
                    if (Math.abs(lastPos.getZ() + zz - pos.getZ()) > 24) {
                        return;
                    }
                    BlockState bs = world.getBlockState(lastPos.add(xx, yy, zz));
                    boolean same = bs == block; //TODO: compare blocks instead?
                    if (same && bs.getBlockHardness(world, lastPos.add(xx, yy, zz)) >= 0.0f) {
                        double xd = lastPos.getX() + xx - pos.getX();
                        double yd = lastPos.getY() + yy - pos.getY();
                        double zd = lastPos.getZ() + zz - pos.getZ();
                        double d = xd * xd + yd * yd + zd * zd;
                        if (d > lastdistance) {
                            lastdistance = d;
                            lastPos = lastPos.add(xx, yy, zz);
                            findBlocks(world, pos, block, reach);
                            return;
                        }
                    }
                }
            }
        }
    }
}
