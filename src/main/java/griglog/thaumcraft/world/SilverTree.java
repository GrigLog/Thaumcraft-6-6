package griglog.thaumcraft.world;

import griglog.thaumcraft.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class SilverTree extends Tree {
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
        return ModFeatures.silverTreeConf;
    }

    public static net.minecraft.world.gen.feature.Feature<BaseTreeFeatureConfig> feature = new Feature();
    static class Feature extends net.minecraft.world.gen.feature.Feature<BaseTreeFeatureConfig> {
        public Feature() {
            super(BaseTreeFeatureConfig.CODEC);
            setRegistryName("silver_tree");
        }

        private static final int minTreeHeight = 7;
        private static final int randomTreeHeight = 4;
        boolean genFlowers;

        @Override
        public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, BaseTreeFeatureConfig config) {
            int height = random.nextInt(randomTreeHeight) + minTreeHeight;
            boolean flag = true;
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            if (y < 1 || y + height + 1 > 256) {
                return false;
            }
            for (int i1 = y; i1 <= y + 1 + height; ++i1) {
                byte spread = 1;
                if (i1 == y) {
                    spread = 0;
                }
                if (i1 >= y + 1 + height - 2) {
                    spread = 3;
                }
                for (int j1 = x - spread; j1 <= x + spread && flag; ++j1) {
                    for (int k1 = z - spread; k1 <= z + spread && flag; ++k1) {
                        if (i1 >= 0 && i1 < 256) {
                            BlockState state = world.getBlockState(new BlockPos(j1, i1, k1));
                            if (!state.isAir() && state.getMaterial() != Material.LEAVES && !state.canBeReplacedByLeaves(world, pos) && i1 > y) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }
            if (!flag) {
                return false;
            }
            BlockState state2 = world.getBlockState(new BlockPos(x, y - 1, z));
            Block block2 = state2.getBlock();
            boolean isSoil = state2.canSustainPlant(world, new BlockPos(x, y - 1, z), Direction.UP, (IPlantable) ModBlocks.silverSapling);
            if (!isSoil || y >= 256 - height - 1)
                return false;
            block2.onPlantGrow(state2, world, new BlockPos(x, y - 1, z), new BlockPos(x, y, z));
            int start = y + height - 5;
            for (int end = y + height + 3 + random.nextInt(3), k2 = start; k2 <= end; ++k2) {
                int cty = MathHelper.clamp(k2, y + height - 3, y + height);
                for (int xx = x - 5; xx <= x + 5; ++xx) {
                    for (int zz = z - 5; zz <= z + 5; ++zz) {
                        double d3 = xx - x;
                        double d4 = k2 - cty;
                        double d5 = zz - z;
                        double dist = d3 * d3 + d4 * d4 + d5 * d5;
                        BlockState s2 = world.getBlockState(new BlockPos(xx, k2, zz));
                        if (dist < 10 + random.nextInt(8) && s2.getBlock().canBeReplacedByLeaves(s2, world, new BlockPos(xx, k2, zz))) {
                            world.setBlockState(new BlockPos(xx, k2, zz), ModBlocks.silverLeaves.getDefaultState(), 2);
                        }
                    }
                }
            }
            int k2;
            for (k2 = 0; k2 < height; ++k2) {
                BlockState s3 = world.getBlockState(new BlockPos(x, y + k2, z));
                if (s3.isAir() || s3.getMaterial() != Material.LEAVES || s3.canBeReplacedByLogs(world, pos)) {
                    world.setBlockState(new BlockPos(x, y + k2, z), ModBlocks.silverLog.getDefaultState(), 2);
                    world.setBlockState(new BlockPos(x - 1, y + k2, z), ModBlocks.silverLog.getDefaultState(), 2);
                    world.setBlockState(new BlockPos(x + 1, y + k2, z), ModBlocks.silverLog.getDefaultState(), 2);
                    world.setBlockState(new BlockPos(x, y + k2, z - 1), ModBlocks.silverLog.getDefaultState(), 2);
                    world.setBlockState(new BlockPos(x, y + k2, z + 1), ModBlocks.silverLog.getDefaultState(), 2);
                }
            }
            world.setBlockState(new BlockPos(x, y + k2, z), ModBlocks.silverLog.getDefaultState(), 2);
            world.setBlockState(new BlockPos(x - 1, y, z - 1), ModBlocks.silverLog.getDefaultState(), 2);
            world.setBlockState(new BlockPos(x - 1, y, z + 1), ModBlocks.silverLog.getDefaultState(), 2);
            world.setBlockState(new BlockPos(x + 1, y, z - 1), ModBlocks.silverLog.getDefaultState(), 2);
            world.setBlockState(new BlockPos(x - 1, y, z - 1), ModBlocks.silverLog.getDefaultState(), 2);
            if (random.nextInt(3) != 0) {
                world.setBlockState(new BlockPos(x - 1, y + 1, z - 1), ModBlocks.silverLog.getDefaultState(), 2);
            }
            if (random.nextInt(3) != 0) {
                world.setBlockState(new BlockPos(x + 1, y + 1, z + 1), ModBlocks.silverLog.getDefaultState(), 2);
            }
            if (random.nextInt(3) != 0) {
                world.setBlockState(new BlockPos(x - 1, y + 1, z + 1), ModBlocks.silverLog.getDefaultState(), 2);
            }
            if (random.nextInt(3) != 0) {
                world.setBlockState(new BlockPos(x + 1, y + 1, z - 1), ModBlocks.silverLog.getDefaultState(), 2);
            }
            world.setBlockState(new BlockPos(x - 2, y, z), ModBlocks.silverLog.getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.X), 2); //0);
            world.setBlockState(new BlockPos(x + 2, y, z), ModBlocks.silverLog.getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.X), 2); //0);
            world.setBlockState(new BlockPos(x, y, z - 2), ModBlocks.silverLog.getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z), 2); //2);
            world.setBlockState(new BlockPos(x, y, z + 2), ModBlocks.silverLog.getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z), 2); //2);
            world.setBlockState(new BlockPos(x - 2, y - 1, z), ModBlocks.silverLog.getDefaultState(), 2); 
            world.setBlockState(new BlockPos(x + 2, y - 1, z), ModBlocks.silverLog.getDefaultState(), 2); 
            world.setBlockState(new BlockPos(x, y - 1, z - 2), ModBlocks.silverLog.getDefaultState(), 2); 
            world.setBlockState(new BlockPos(x, y - 1, z + 2), ModBlocks.silverLog.getDefaultState(), 2); 
            world.setBlockState(new BlockPos(x - 1, y + (height - 4), z - 1), ModBlocks.silverLog.getDefaultState(), 2); 
            world.setBlockState(new BlockPos(x + 1, y + (height - 4), z + 1), ModBlocks.silverLog.getDefaultState(), 2); 
            world.setBlockState(new BlockPos(x - 1, y + (height - 4), z + 1), ModBlocks.silverLog.getDefaultState(), 2); 
            world.setBlockState(new BlockPos(x + 1, y + (height - 4), z - 1), ModBlocks.silverLog.getDefaultState(), 2); 
            if (random.nextInt(3) == 0) {
                world.setBlockState(new BlockPos(x - 1, y + (height - 5), z - 1), ModBlocks.silverLog.getDefaultState(), 2); 
            }
            if (random.nextInt(3) == 0) {
                world.setBlockState(new BlockPos(x + 1, y + (height - 5), z + 1), ModBlocks.silverLog.getDefaultState(), 2); 
            }
            if (random.nextInt(3) == 0) {
                world.setBlockState(new BlockPos(x - 1, y + (height - 5), z + 1), ModBlocks.silverLog.getDefaultState(), 2); 
            }
            if (random.nextInt(3) == 0) {
                world.setBlockState(new BlockPos(x + 1, y + (height - 5), z - 1), ModBlocks.silverLog.getDefaultState(), 2); 
            }
            world.setBlockState(new BlockPos(x - 2, y + (height - 4), z), ModBlocks.silverLog.getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.X), 2); //0);
            world.setBlockState(new BlockPos(x + 2, y + (height - 4), z), ModBlocks.silverLog.getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.X), 2); //0);
            world.setBlockState(new BlockPos(x, y + (height - 4), z - 2), ModBlocks.silverLog.getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z), 2); //2);
            world.setBlockState(new BlockPos(x, y + (height - 4), z + 2), ModBlocks.silverLog.getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z), 2); //2);
            //if (worldgen) {
            //    WorldGenerator flowers = new WorldGenCustomFlowers(BlocksTC.shimmerleaf, 0);
            //    flowers.generate(world, random, new BlockPos(x, y, z));
            //}
            return true;
        }
    }
}
