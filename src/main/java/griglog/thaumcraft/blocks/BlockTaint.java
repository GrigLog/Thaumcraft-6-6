package griglog.thaumcraft.blocks;

import griglog.thaumcraft.client.SoundsTC;
import griglog.thaumcraft.utils.AuraHelper;
import griglog.thaumcraft.utils.WorldUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockTaint {} /* extends Block implements ITaintBlock {
    static Random r = new Random(System.currentTimeMillis());
    public BlockTaint(String name) {
        super(AbstractBlock.Properties.create(ModBlocks.TAINT)
            .hardnessAndResistance(10, 100)
            .sound(SoundsTC.GORE)
            .tickRandomly());
        setRegistryName(name);
    }

    @Override
    public void die(World world, BlockPos pos, BlockState state) {
        if (state.getBlock() == ModBlocks.taintRock) {
            world.setBlockState(pos, ModBlocks.stonePorous.getDefaultState());
        } else if (state.getBlock() == ModBlocks.taintSoil) {
            world.setBlockState(pos, Blocks.DIRT.getDefaultState());
        } else if (state.getBlock() == ModBlocks.taintCrust) {
            world.setBlockState(pos, ModBlocks.fluxGoo.getDefaultState());
        } else if (state.getBlock() == ModBlocks.taintGeyser) {
            world.setBlockState(pos, ModBlocks.fluxGoo.getDefaultState());
        } else {
            world.destroyBlock(pos, false);
        }
    }



    static ArrayList<WeightedRandomLoot> pdrops = null;

    public static boolean canFallBelow(World world, BlockPos pos) {
        BlockState bs = world.getBlockState(pos);
        Block l = bs.getBlock();
        for (int xx = -1; xx <= 1; ++xx) {
            for (int zz = -1; zz <= 1; ++zz) {
                for (int yy = -1; yy <= 1; ++yy) {
                    if (WorldUtils.isLog(world.getBlockState(pos.add(xx, yy, zz)))) {
                        return false;
                    }
                }
            }
        }
        return l.isAir(bs, world, pos) || ((l != ModBlocks.fluxGoo || bs.get(FlowingFluidBlock.LEVEL) < 4) && (l == Blocks.FIRE || l == ModBlocks.taintFibre || bs.isReplaceable() || bs.getMaterial() == Material.WATER || bs.getMaterial() == Material.LAVA));
    }

    public SoundType getSoundType() {
        return SoundsTC.GORE;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
        return BlockFaceShape.UNDEFINED;
    }

    public MaterialColor getMapColor(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MaterialColor.PURPLE;
    }

    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }



    public void updateTick(World world, BlockPos pos, BlockState state, Random random) {
        if (!world.isRemote) {
            if (!TaintHelper.isNearTaintSeed(world, pos) && random.nextInt(10) == 0) {
                die(world, pos, state);
                return;
            }
            if (state.getBlock() == ModBlocks.taintRock) {
                TaintHelper.spreadFibres(world, pos);
            }
            if (state.getBlock() == ModBlocks.taintCrust) {
                Random r = new Random(pos.toLong());
                if (tryToFall(world, pos, pos)) {
                    return;
                }
                if (world.isAirBlock(pos.up())) {
                    boolean doIt = true;
                    Direction dir = Direction.HORIZONTALS[random.nextInt(4)];
                    for (int a = 1; a < 4; ++a) {
                        if (!world.isAirBlock(pos.offset(dir).down(a))) {
                            doIt = false;
                            break;
                        }
                        if (world.getBlockState(pos.down(a)).getBlock() != this) {
                            doIt = false;
                            break;
                        }
                    }
                    if (doIt && tryToFall(world, pos, pos.offset(dir))) {
                        return;
                    }
                }
            } else if (state.getBlock() == ModBlocks.taintGeyser) {
                if (world.rand.nextFloat() < 0.2 && world.isAnyPlayerWithinRangeAt(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 32.0) && EntityUtils.getEntitiesInRange(world, pos, null, (Class<? extends Entity>) EntityTaintSwarm.class, 32.0).isEmpty()) {
                    Entity e = new EntityTaintSwarm(world);
                    e.setLocationAndAngles(pos.getX() + 0.5f, pos.getY() + 1.25f, pos.getZ() + 0.5f, (float) world.rand.nextInt(360), 0.0f);
                    world.addEntity(e);
                } else if (AuraHelper.getFlux(world, pos) < 2.0f) {
                    AuraHelper.polluteAura(world, pos, 0.25f, true);
                }
            }
        }
    }

    public boolean canSilkHarvest(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        return true;
    }

    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        if (!world.isRemote && entity instanceof LivingEntity && !((LivingEntity) entity).isEntityUndead() && world.rand.nextInt(250) == 0) {
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(EffectFluxTaint.instance, 200, 0, false, true));
        }
    }

    public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int eventID, int eventParam) {
        if (eventID == 1) {
            if (worldIn.isRemote) {
                worldIn.playSound(null, pos, SoundEvents.BLOCK_CHORUS_FLOWER_DEATH, SoundCategory.BLOCKS, 0.1f, 0.9f + worldIn.rand.nextFloat() * 0.2f);
            }
            return true;
        }
        return super.eventReceived(state, worldIn, pos, eventID, eventParam);
    }

    private boolean tryToFall(World world, BlockPos pos, BlockPos pos2) {
        if (!BlockTaintFibre.isOnlyAdjacentToTaint(world, pos)) {
            return false;
        }
        if (canFallBelow(world, pos2.down()) && pos2.getY() >= 0) {
            byte b0 = 32;
            if (world.isAreaLoaded(pos2.add(-b0, -b0, -b0), pos2.add(b0, b0, b0))) {
                if (!world.isRemote) {
                    EntityFallingTaint entityfalling = new EntityFallingTaint(world, pos2.getX() + 0.5f, pos2.getY() + 0.5f, pos2.getZ() + 0.5f, world.getBlockState(pos), pos);
                    world.addEntity(entityfalling);
                    return true;
                }
            } else {
                world.setBlockToAir(pos);
                BlockPos p2;
                for (p2 = new BlockPos(pos2); canFallBelow(world, p2.down()) && p2.getY() > 0; p2 = p2.down()) {
                }
                if (p2.getY() > 0) {
                    world.setBlockState(p2, ModBlocks.taintCrust.getDefaultState());
                }
            }
        }
        return false;
    }

    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, BlockState state, int fortune) {
        if (state.getBlock() == this && state.getBlock() == ModBlocks.taintRock) {
            int rr = BlockTaint.r.nextInt(15) + fortune;
            if (rr > 13) {
                List<ItemStack> ret = new ArrayList<ItemStack>();
                ret.add(ConfigItems.FLUX_CRYSTAL.copy());
                return ret;
            }
        }
        return super.getDrops(world, pos, state, fortune);
    }

    protected boolean canSilkHarvest() {
        return false;
    }

    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Item.getItemById(0);
    }
}*/
