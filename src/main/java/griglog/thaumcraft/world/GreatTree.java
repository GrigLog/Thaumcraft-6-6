package griglog.thaumcraft.world;

import griglog.thaumcraft.blocks.ModBlocks;
import griglog.thaumcraft.utils.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.Random;

public class GreatTree extends Tree {
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
        return ModFeatures.greatTreeConf;
    }

    //TODO: refactor this monster!!! Make it use the same feature as BigTree.
    public static class Feature extends net.minecraft.world.gen.feature.Feature<BaseTreeFeatureConfig> {
        public Feature() {
            super(BaseTreeFeatureConfig.CODEC);
            setRegistryName("giant_tree");
        }

        static byte[] otherCoordPairs = new byte[]{2, 0, 0, 1, 2, 1};

        int[] basePos = new int[]{0, 0, 0};
        double heightAttenuation = 0.618;
        double branchDensity = 1;
        double branchSlope = 0.38;
        double scaleWidth = 1.2;
        double leafDensity = 0.9;
        int TRUNK_WIDTH = 2;
        final int HEIGHT = 11;
        final int LEAF_DISTANCE = 4;
        int[][] leafNodes;
        boolean spiders = false;

        ISeedReader savedWorld;
        int height;
        int heightScaled = 0;


        void generateLeafNodeList(Random rand) {
            int amount = (int) (1.382 + Math.pow(leafDensity * height / 13.0, 2.0));
            if (amount < 1) {
                amount = 1;
            }
            int[][] nodes = new int[amount * height][4]; //x, y1, z, y2 (y2 < y1???)
            int highest = basePos[1] + height - LEAF_DISTANCE;
            int nodeCtr = 1;
            int middle = basePos[1] + heightScaled;
            int highestRel = highest - basePos[1];
            nodes[0][0] = basePos[0];
            nodes[0][1] = highest;
            nodes[0][2] = basePos[2];
            nodes[0][3] = middle;
            --highest;
            while (highestRel >= 0) {
                int ctr = 0;
                float layerSize = layerSize(highestRel);
                if (layerSize < 0.0f) {
                    --highest;
                    --highestRel;
                } else {
                    while (ctr < amount) {
                        double width = scaleWidth * layerSize * (rand.nextFloat() + 0.328);
                        double randAngle = rand.nextFloat() * 2.0 * 3.141592653589793;
                        int furthestX = MathHelper.floor(width * Math.sin(randAngle) + basePos[0] + 0.5);
                        int furthestZ = MathHelper.floor(width * Math.cos(randAngle) + basePos[2] + 0.5);
                        int[] branchEnd = {furthestX, highest, furthestZ};
                        int[] p2 = {furthestX, highest + LEAF_DISTANCE, furthestZ};
                        if (checkBlockLine(branchEnd, p2) == -1) {
                            int[] pos = {basePos[0], basePos[1], basePos[2]};
                            double distXZ = Math.sqrt(Math.pow(Math.abs(basePos[0] - branchEnd[0]), 2.0) + Math.pow(Math.abs(basePos[2] - branchEnd[2]), 2.0));
                            double slope = distXZ * branchSlope;
                            if (branchEnd[1] - slope > middle) {
                                pos[1] = middle;
                            } else {
                                pos[1] = (int) (branchEnd[1] - slope);
                            }
                            if (checkBlockLine(pos, branchEnd) == -1) {
                                nodes[nodeCtr][0] = furthestX;
                                nodes[nodeCtr][1] = highest;
                                nodes[nodeCtr][2] = furthestZ;
                                nodes[nodeCtr][3] = pos[1];
                                ++nodeCtr;
                            }
                        }
                        ++ctr;
                    }
                    --highest;
                    --highestRel;
                }
            }
            leafNodes = new int[nodeCtr][4];
            System.arraycopy(nodes, 0, leafNodes, 0, nodeCtr);
        }

        void genTreeLeafLayer(int x, int y, int z, float size) {
            int size2 = (int) (size + 0.618);
            int[] center = {x, y, z};
            int[] pos = {0, y, 0};
            for (int dx = -size2; dx <= size2; dx++){
                pos[0] = center[0] + dx;
                for (int dz = -size2; dz <= size2; ++dz) {
                    double distSq = Math.pow(Math.abs(dx) + 0.5, 2.0) + Math.pow(Math.abs(dz) + 0.5, 2.0);
                    if (distSq <= size * size) {
                        pos[2] = center[2] + dz;
                        BlockPos p = new BlockPos(pos[0], pos[1], pos[2]);
                        BlockState state = savedWorld.getBlockState(p);
                        Block block = state.getBlock();
                        if (block == Blocks.AIR || block == ModBlocks.greatLeaves) {
                            if (block.canBeReplacedByLeaves(state, savedWorld, p)) {
                                savedWorld.setBlockState(p, ModBlocks.greatLeaves.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }

        float layerSize(int y) {
            if (y < (float) height * 0.3) {
                return -1.618f;
            }
            float var2 = height / 2.0f;
            float var3 = height / 2.0f - y;
            float size;
            if (var3 == 0.0f) {
                size = var2;
            } else if (Math.abs(var3) >= var2) {
                size = 0.0f;
            } else {
                size = (float) Math.sqrt(Math.pow(Math.abs(var2), 2.0) - Math.pow(Math.abs(var3), 2.0));
            }
            size *= 0.5f;
            return size;
        }

        float leafSize(int yRel) { //relative to the leaf node's lowest point
            return (yRel >= 0 && yRel < LEAF_DISTANCE) ? ((yRel != 0 && yRel != LEAF_DISTANCE - 1) ? 3.0f : 2.0f) : -1.0f;
        }

        void generateLeafNode(int x, int y, int z) {
            for (int height = y, maxHeight = y + LEAF_DISTANCE; height < maxHeight; ++height) {
                float size = leafSize(height - y);
                genTreeLeafLayer(x, height, z, size);
            }
        }

        void placeLogLine(int[] pos1, int[] pos2) {
            int[] diff = {0, 0, 0};
            byte maxIndex = 0;
            for (byte i = 0; i < 3; i++){
                diff[i] = pos2[i] - pos1[i];
                if (Math.abs(diff[i]) > Math.abs(diff[maxIndex])) {
                    maxIndex = i;
                }
            }
            if (diff[maxIndex] == 0) {
                return;
            }
            byte aIndex = otherCoordPairs[maxIndex];
            byte bIndex = otherCoordPairs[maxIndex + 3];
            byte maxSign = (byte) (diff[maxIndex] > 0 ? 1 : -1);
            double da = diff[aIndex] / (double) diff[maxIndex];
            double db = diff[bIndex] / (double) diff[maxIndex];
            int[] pos = {0, 0, 0};
            int i = 0;
            int end = diff[maxIndex] + maxSign;
            for (; i != end; i += maxSign) {
                pos[maxIndex] = pos1[maxIndex] + i;
                pos[aIndex] = MathHelper.floor(pos1[aIndex] + i * da);
                pos[bIndex] = MathHelper.floor(pos1[bIndex] + i * db);
                Direction.Axis axis = Direction.Axis.Y;
                int diffX = Math.abs(pos[0] - pos1[0]);
                int diffZ = Math.abs(pos[2] - pos1[2]);
                int diffXZ = Math.max(diffX, diffZ);
                if (diffXZ > 0) {
                    if (diffX == diffXZ) {
                        axis = Direction.Axis.X;
                    } else {
                        axis = Direction.Axis.Z;
                    }
                }
                BlockPos p = new BlockPos(pos[0], pos[1], pos[2]);
                //if (savedWorld.getBlockState(p).getBlock() == ModBlocks.greatSapling){
                //    savedWorld.destroyBlock(p, false);
                //}
                if (savedWorld.getBlockState(p).canBeReplacedByLogs(savedWorld, p)){
                    savedWorld.setBlockState(p, ModBlocks.greatLog.getDefaultState().with(RotatedPillarBlock.AXIS, axis), 2);
                }
            }
        }

        void generateLeaves() {
            for (int var1 = 0, var2 = leafNodes.length; var1 < var2; ++var1) {
                int var3 = leafNodes[var1][0];
                int var4 = leafNodes[var1][1];
                int var5 = leafNodes[var1][2];
                generateLeafNode(var3, var4, var5);
            }
        }

        boolean leafNodeNeedsBase(int nodeHeight) {
            return nodeHeight >= height * 0.2;
        }

        void generateTrunk() {
            int x = basePos[0];
            int y = basePos[1];
            int y2 = basePos[1] + heightScaled;
            int z = basePos[2];
            for (int dz = 0; dz < TRUNK_WIDTH; dz++){
                for (int dx = 0; dx < TRUNK_WIDTH; dx++){
                    placeLogLine(new int[]{x+dx, y, z+dz}, new int[]{x+dx, y2, z+dz});
                }
            }
        }

        void generateLeafNodeBases() {
            int[] pos = {basePos[0], basePos[1], basePos[2]};
            for (int[] node : leafNodes) {
                int[] nodePos = {node[0], node[1], node[2]};
                pos[1] = node[3];
                int dy = pos[1] - basePos[1];
                if (leafNodeNeedsBase(dy)) {
                    placeLogLine(pos, nodePos);
                }
            }
        }

        int checkBlockLine(int[] pos1, int[] pos2) {
            int[] diff = {0, 0, 0};
            byte maxIndex = 0;
            for (byte i = 0; i < 3; i++){
                diff[i] = pos2[i] - pos1[i];
                if (Math.abs(diff[i]) > Math.abs(diff[maxIndex])) {
                    maxIndex = i;
                }
            }
            if (diff[maxIndex] == 0) {
                return -1;
            }
            byte aIndex = otherCoordPairs[maxIndex];
            byte bIndex = otherCoordPairs[maxIndex + 3];
            byte maxSign = (byte) (diff[maxIndex] > 0 ? 1 : -1);
            double da = diff[aIndex] / (double) diff[maxIndex];
            double db = diff[bIndex] / (double) diff[maxIndex];
            int[] pos = {0, 0, 0};
            int i = 0;
            int end = diff[maxIndex] + maxSign;
            for (; i != end; i += maxSign) {
                pos[maxIndex] = pos1[maxIndex] + i;
                pos[aIndex] = MathHelper.floor(pos1[aIndex] + i * da);
                pos[bIndex] = MathHelper.floor(pos1[bIndex] + i * db);
                Block b = savedWorld.getBlockState(new BlockPos(pos[0], pos[1], pos[2])).getBlock();
                if (b != Blocks.AIR && b != ModBlocks.greatLeaves && b != ModBlocks.greatSapling && b != ModBlocks.greatLog) {
                    return Math.abs(i);
                }
            }
            return -1;
        }

        boolean validTreeLocation(int x, int z) {
            int[] lower = {basePos[0] + x, basePos[1], basePos[2] + z};
            int[] higher = {basePos[0] + x, basePos[1] + height - 1, basePos[2] + z};
            BlockState state = savedWorld.getBlockState(new BlockPos(basePos[0] + x, basePos[1] - 1, basePos[2] + z));
            Block var3 = state.getBlock();
            boolean isSoil = var3.canSustainPlant(state, savedWorld, new BlockPos(basePos[0] + x, basePos[1] - 1, basePos[2] + z), Direction.UP, (IPlantable) ModBlocks.greatSapling);
            if (!isSoil) {
                return false;
            }
            int l = checkBlockLine(lower, higher);
            if (l == -1) {
                return true;
            }
            if (l < 6) {
                return false;
            }
            if (l < height)
                height = l;
            return true;
        }

        @Override
        public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, BaseTreeFeatureConfig config) {
            //long var6 = random.nextLong();
            //rand.setSeed(var6);
            savedWorld = world;
            basePos[0] = pos.getX();
            basePos[1] = pos.getY();
            basePos[2] = pos.getZ();
            height = HEIGHT + random.nextInt(HEIGHT);
            for (int x = 0; x < TRUNK_WIDTH; ++x) {
                for (int z = 0; z < TRUNK_WIDTH; ++z) {
                    if (!validTreeLocation(x, z)) {
                        savedWorld = null;
                        return false;
                    }
                }
            }
            heightScaled = (int) (height * heightAttenuation);
            if (heightScaled >= height) {
                heightScaled = height - 1;
            }

            generateLeafNodeList(random);
            generateLeafNodeBases();
            generateTrunk();
            generateLeaves();
            scaleWidth = 1.66;
            basePos[0] = pos.getX();
            basePos[1] = pos.getY() + heightScaled;
            basePos[2] = pos.getZ();
            generateLeafNodeList(random);
            generateLeafNodeBases();
            generateTrunk();
            generateLeaves();

            if (spiders) {
                world.setBlockState(pos.down(), Blocks.SPAWNER.getDefaultState(), 2);
                MobSpawnerTileEntity spawner = (MobSpawnerTileEntity) world.getTileEntity(pos.down());
                if (spawner != null) {
                    spawner.getSpawnerBaseLogic().setEntityType(EntityType.CAVE_SPIDER);
                    for (int a = 0; a < 50; ++a) {
                        int xx = pos.getX() - 7 + random.nextInt(14);
                        int yy = pos.getY() + random.nextInt(10);
                        int zz = pos.getZ() - 7 + random.nextInt(14);
                        BlockState bs = world.getBlockState(pos);

                        if (bs.isAir() && (BlockUtils.isBlockTouching(world, new BlockPos(xx, yy, zz), ModBlocks.greatLeaves) || BlockUtils.isBlockTouching(world, new BlockPos(xx, yy, zz), ModBlocks.greatLog))) {
                            world.setBlockState(new BlockPos(xx, yy, zz), Blocks.COBWEB.getDefaultState(), 2);
                        }
                    }
                    world.setBlockState(pos.down(2), Blocks.CHEST.getDefaultState(), 2);
                    ChestTileEntity var8 = (ChestTileEntity) world.getTileEntity(pos.down(2));
                    if (var8 != null) {
                        var8.setLootTable(LootTables.CHESTS_SIMPLE_DUNGEON, random.nextLong());
                    }
                }
            }
            savedWorld = null;
            return true;
        }
    }
}
