package griglog.thaumcraft.world;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.state.Property;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BigTree extends Tree {
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
        return ModFeatures.bigTreeConf;
    }

    public static class Feature extends net.minecraft.world.gen.feature.Feature<BaseTreeFeatureConfig>{
        public Feature() {
            super(BaseTreeFeatureConfig.CODEC);
            setRegistryName("big_tree");
        }

        static class FoliageCoordinates extends BlockPos {
            public int branchBase;
            public FoliageCoordinates(BlockPos pos, int branchBase) {
                super(pos.getX(), pos.getY(), pos.getZ());
                this.branchBase = branchBase;
            }
        }


        double heightAttenuation = 0.6618;
        double branchSlope = 0.381;
        double scaleWidth = 1.25;
        double leafDensity = 0.9;
        int heightLimitLimit = 11;
        int leafDistanceLimit = 4;

        ISeedReader savedWorld;
        BlockPos basePos;
        List<FoliageCoordinates> foliageCoords;
        int heightLimit;
        int height;

        void generateLeafNodeList(Random rand) {
            height = (int) (heightLimit * heightAttenuation);
            if (height >= heightLimit) {
                height = heightLimit - 1;
            }
            int i = (int) (1.382 + Math.pow(leafDensity * heightLimit / 13.0, 2.0));
            if (i < 1) {
                i = 1;
            }
            int j = basePos.getY() + height;
            int k = heightLimit - leafDistanceLimit;
            (foliageCoords = Lists.newArrayList()).add(new FoliageCoordinates(basePos.up(k), j));
            while (k >= 0) {
                float f = layerSize(k);
                if (f >= 0.0f) {
                    for (int l = 0; l < i; ++l) {
                        double d0 = scaleWidth * f * (rand.nextFloat() + 0.328);
                        double d2 = rand.nextFloat() * 2.0f * 3.141592653589793;
                        double d3 = d0 * Math.sin(d2) + 0.5;
                        double d4 = d0 * Math.cos(d2) + 0.5;
                        BlockPos blockpos = basePos.add(d3, k - 1, d4);
                        BlockPos blockpos2 = blockpos.up(leafDistanceLimit);
                        if (checkBlockLine(blockpos, blockpos2) == -1) {
                            int i2 = basePos.getX() - blockpos.getX();
                            int j2 = basePos.getZ() - blockpos.getZ();
                            double d5 = blockpos.getY() - Math.sqrt(i2 * i2 + j2 * j2) * branchSlope;
                            int k2 = (d5 > j) ? j : ((int) d5);
                            BlockPos blockpos3 = new BlockPos(basePos.getX(), k2, basePos.getZ());
                            if (checkBlockLine(blockpos3, blockpos) == -1) {
                                foliageCoords.add(new FoliageCoordinates(blockpos, blockpos3.getY()));
                            }
                        }
                    }
                }
                --k;
            }
        }

        void crosSection(BlockPos pos, float p_181631_2_, BlockState bs) {
            for (int i = (int) (p_181631_2_ + 0.618), j = -i; j <= i; ++j) {
                for (int k = -i; k <= i; ++k) {
                    if (Math.pow(Math.abs(j) + 0.5, 2.0) + Math.pow(Math.abs(k) + 0.5, 2.0) <= p_181631_2_ * p_181631_2_) {
                        BlockPos blockpos = pos.add(j, 0, k);
                        BlockState state = savedWorld.getBlockState(blockpos);
                        if (state.isAir() || BlockTags.LEAVES.contains(state.getBlock()))
                            savedWorld.setBlockState(blockpos, bs, 2);
                        
                    }
                }
            }
        }

        float layerSize(int p_76490_1_) {
            if (p_76490_1_ < heightLimit * 0.3f) {
                return -1.0f;
            }
            float f = heightLimit / 2.0f;
            float f2 = f - p_76490_1_;
            float f3 = MathHelper.sqrt(f * f - f2 * f2);
            if (f2 == 0.0f) {
                f3 = f;
            } else if (Math.abs(f2) >= f) {
                return 0.0f;
            }
            return f3 * 0.5f;
        }

        float leafSize(int p_76495_1_) {
            return (p_76495_1_ >= 0 && p_76495_1_ < leafDistanceLimit) ? ((p_76495_1_ != 0 && p_76495_1_ != leafDistanceLimit - 1) ? 3.0f : 2.0f) : -1.0f;
        }

        void generateLeafNode(BlockPos pos) {
            for (int i = 0; i < leafDistanceLimit; ++i) {
                crosSection(pos.up(i), leafSize(i), Blocks.OAK_LEAVES.getDefaultState()/*.withProperty((Property) BlockLeaves.CHECK_DECAY, (Comparable) false)*/);
            }
        }

        void limb(BlockPos from, BlockPos to) {
            BlockPos diff = to.subtract(from);
            int i = greatestAxis(diff);
            float dx = diff.getX() / (float) i;
            float dy = diff.getY() / (float) i;
            float dz = diff.getZ() / (float) i;
            Direction.Axis axis =  Direction.getFacingFromVector(dx, dy, dz).getAxis();
            for (int j = 0; j <= i; ++j) {
                BlockPos between = from.add(0.5f + j * dx, 0.5f + j * dy, 0.5f + j * dz);
                savedWorld.setBlockState(between, Blocks.OAK_LOG.getDefaultState().with(RotatedPillarBlock.AXIS, axis), 2);
            }
        }

        private int greatestAxis(BlockPos pos) {
            int i = MathHelper.abs(pos.getX());
            int j = MathHelper.abs(pos.getY());
            int k = MathHelper.abs(pos.getZ());
            return Math.max(Math.max(i, j), k);
        }

        void generateLeaves() {
            for (FoliageCoordinates foliagecoordinates : foliageCoords) {
                generateLeafNode(foliagecoordinates);
            }
        }

        boolean leafNodeNeedsBase(int p_76493_1_) {
            return p_76493_1_ >= heightLimit * 0.2;
        }

        void generateTrunk() {
            BlockPos blockpos = basePos;
            BlockPos blockpos2 = basePos.up(height);
            limb(blockpos, blockpos2);
        }

        void generateLeafNodeBases() {
            for (FoliageCoordinates fcords : foliageCoords) {
                int i = fcords.branchBase;
                BlockPos blockpos = new BlockPos(basePos.getX(), i, basePos.getZ());
                if (leafNodeNeedsBase(i - basePos.getY())) {
                    limb(blockpos, fcords);
                }
            }
        }

        int checkBlockLine(BlockPos from, BlockPos to) {
            BlockPos diff = to.add(-from.getX(), -from.getY(), -from.getZ());
            int i = greatestAxis(diff);
            float dx = diff.getX() / (float) i;
            float dy = diff.getY() / (float) i;
            float dz = diff.getZ() / (float) i;
            if (i == 0) {
                return -1;
            }
            for (int j = 0; j <= i; ++j) {
                BlockPos between = from.add(0.5f + j * dx, 0.5f + j * dy, 0.5f + j * dz);
                if (!savedWorld.getBlockState(between).canBeReplacedByLogs(savedWorld, between))
                    return j;
            }
            return -1;
        }

        public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, BaseTreeFeatureConfig config) {
            savedWorld = world;
            basePos = pos;
            if (heightLimit == 0) {
                heightLimit = 11 + rand.nextInt(heightLimitLimit);
            }
            if (!validTreeLocation()) {
                return false;
            }
            generateLeafNodeList(rand);
            generateLeaves();
            generateTrunk();
            generateLeafNodeBases();
            return true;
        }

        private boolean validTreeLocation() {
            BlockPos down = basePos.down();
            BlockState state = savedWorld.getBlockState(down);
            boolean isSoil = state.getBlock().canSustainPlant(state, savedWorld, down, Direction.UP, (IPlantable) Blocks.OAK_SAPLING);
            if (!isSoil) {
                return false;
            }
            int i = checkBlockLine(basePos, basePos.up(heightLimit - 1));
            if (i == -1) {
                return true;
            }
            if (i < 6) {
                return false;
            }
            heightLimit = i;
            return true;
        }
    }
}
