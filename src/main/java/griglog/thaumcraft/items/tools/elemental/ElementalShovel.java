package griglog.thaumcraft.items.tools.elemental;

import com.google.common.collect.ImmutableSet;
import griglog.thaumcraft.items.infusions.InfusionEnchantment;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumTier;
import griglog.thaumcraft.utils.ItemUtils;
import griglog.thaumcraft.utils.MathUtils;
import griglog.thaumcraft.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.Set;
import net.minecraft.util.Direction.Axis;

public class ElementalShovel extends ShovelItem /*implements IArchitect */{
    //private static Block[] isEffective = new Block[] { Blocks.GRASS, Blocks.DIRT, Blocks.SAND, Blocks.GRAVEL, Blocks.SNOW_LAYER, Blocks.SNOW, Blocks.CLAY, Blocks.FARMLAND, Blocks.SOUL_SAND, Blocks.MYCELIUM };
    Direction side;

    public ElementalShovel() {
        super(ThaumTier.ELEMENTAL, 5.5f, -3, ModTab.props());
        side = Direction.DOWN;
        setRegistryName("elemental_shovel");
    }


    public static byte getOrientation(ItemStack stack) {
        return Utils.safeTag(stack).getByte("or");
    }

    public static void setOrientation(ItemStack stack, byte o) {
        Utils.safeTag(stack).putByte("or", (byte) (o % 3));
    }

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return ImmutableSet.of(ToolType.SHOVEL);
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.RARE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (playerIn.isSneaking())
            setOrientation(stack, (byte) (getOrientation(stack) + 1));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos basePos = context.getPos();
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        BlockState baseBs = world.getBlockState(basePos);
        ItemStack stack = player.getHeldItem(hand);
        Direction side = context.getFace();
        Axis blockAxis = side.getAxis();

        byte o = getOrientation(stack);
        BlockPos center;
        Axis normal, playerAxis;
        switch (o) {
            default:
            case 0:
                center = basePos.add(side.getDirectionVec());
                normal = blockAxis;
                break;
            case 1:
                center = basePos.add(side.getDirectionVec()).add(side.getDirectionVec());
                playerAxis = Direction.fromAngle(player.rotationYaw).getAxis();
                if (blockAxis.isHorizontal()){
                    if (blockAxis == playerAxis){
                        normal = blockAxis == Axis.X ? Axis.Z : Axis.X;
                    } else {
                        normal = playerAxis;
                    }
                } else {
                    normal = playerAxis;
                }
                break;
            case 2:
                center = basePos.add(side.getDirectionVec()).add(side.getDirectionVec());
                playerAxis = Direction.fromAngle(player.rotationYaw).getAxis();
                if (blockAxis.isHorizontal()){ //horizontal
                    normal = Axis.Y;
                } else {
                    normal = playerAxis == Axis.X ? Axis.Z : Axis.X;
                }
                break;
        }
        //v1 and v2 are plane direction vectors
        Axis v1 = normal == Axis.Y ? Axis.Z : Axis.Y;
        Axis v2 = normal == Axis.X ? Axis.Z : Axis.X;
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                BlockPos pos = center.add(MathUtils.direction(v1, i)).add(MathUtils.direction(v2, j));
                BlockState bs = world.getBlockState(pos);
                if (bs.isReplaceable(new BlockItemUseContext(context))) {
                    if (player.isCreative() || ItemUtils.consumePlayerItem(player, baseBs.getBlock().asItem())) {
                        world.playSound(pos.getX(), pos.getY(), pos.getZ(), baseBs.getSoundType().getBreakSound(), SoundCategory.BLOCKS, 0.6f, 0.9f + world.rand.nextFloat() * 0.2f, false);
                        world.setBlockState(pos, baseBs.getBlock().getDefaultState());
                        stack.damageItem(1, player, (e) -> {});
                        if (world.isRemote) {
                            //FXDispatcher.INSTANCE.drawBamf(pos, 8401408, false, false, side);
                        }
                        player.swingArm(hand);
                    } else if (baseBs.getBlock() == Blocks.GRASS_BLOCK && (player.isCreative() || ItemUtils.consumePlayerItem(player, Items.DIRT))) {
                        world.playSound(pos.getX(), pos.getY(), pos.getZ(), baseBs.getSoundType().getBreakSound(), SoundCategory.BLOCKS, 0.6f, 0.9f + world.rand.nextFloat() * 0.2f, false);
                        world.setBlockState(pos, Blocks.DIRT.getDefaultState());
                        stack.damageItem(1, player, (e) -> {});
                        if (world.isRemote) {
                            //FXDispatcher.INSTANCE.drawBamf(pos, 8401408, false, false, side);
                        }
                        player.swingArm(hand);
                    }
                    if (stack.isEmpty()) {
                        break;
                    }
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)) {
            ItemStack is = new ItemStack(this);
            InfusionEnchantment.add(is, InfusionEnchantment.DESTRUCTIVE, 1);
            items.add(is);
        } else {
            super.fillItemGroup(group, items);
        }
    }

    /*public ArrayList<BlockPos> getArchitectBlocks(ItemStack focusstack, World world, BlockPos pos, Direction side, PlayerEntity player) {
        ArrayList<BlockPos> b = new ArrayList<BlockPos>();
        if (!player.isSneaking()) {
            return b;
        }
        BlockState bs = world.getBlockState(pos);
        for (int aa = -1; aa <= 1; ++aa) {
            for (int bb = -1; bb <= 1; ++bb) {
                int xx = 0;
                int yy = 0;
                int zz = 0;
                byte o = getOrientation(focusstack);
                if (o == 1) {
                    yy = bb;
                    if (side.ordinal() <= 1) {
                        int l = MathHelper.floor(player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
                        if (l == 0 || l == 2) {
                            xx = aa;
                        } else {
                            zz = aa;
                        }
                    } else if (side.ordinal() <= 3) {
                        zz = aa;
                    } else {
                        xx = aa;
                    }
                } else if (o == 2) {
                    if (side.ordinal() <= 1) {
                        int l = MathHelper.floor(player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
                        yy = bb;
                        if (l == 0 || l == 2) {
                            xx = aa;
                        } else {
                            zz = aa;
                        }
                    } else {
                        zz = bb;
                        xx = aa;
                    }
                } else if (side.ordinal() <= 1) {
                    xx = aa;
                    zz = bb;
                } else if (side.ordinal() <= 3) {
                    xx = aa;
                    yy = bb;
                } else {
                    zz = aa;
                    yy = bb;
                }
                BlockPos p2 = pos.offset(side).add(xx, yy, zz);
                BlockState b2 = world.getBlockState(p2);
                if (bs.getBlock().canPlaceBlockAt(world, p2)) {
                    b.add(p2);
                }
            }
        }
        return b;
    }

    public boolean showAxis(ItemStack stack, World world, PlayerEntity player, Direction side, EnumAxis axis) {
        return false;
    }

    public RayTraceResult getArchitectMOP(ItemStack stack, World world, LivingEntity player) {
        return Utils.rayTrace(world, player, false);
    }

    public boolean useBlockHighlight(ItemStack stack) {
        return true;
    }*/
}
