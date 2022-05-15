package griglog.thaumcraft.items.misc;

import griglog.thaumcraft.aspect.*;
import griglog.thaumcraft.items.ModItems;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.utils.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;

public class Phial extends Item {
    public Phial(boolean full) {
        super(ModTab.props());
        setRegistryName("phial_" + (full ? "full": "empty"));
        this.full = full;
    }
    final boolean full;

    @Override
    public ITextComponent getDisplayName(ItemStack is) {
        if (full)
            return new TranslationTextComponent(getTranslationKey(is), getAspect(is).getName());
        return super.getDisplayName(is);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IWorldReader world, BlockPos pos, PlayerEntity player) {
        return true;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext ctx) {
        TileEntity tile = ctx.getWorld().getTileEntity(ctx.getPos());
        if (tile == null || ctx.getPlayer() == null)
            return super.onItemUse(ctx);
        if (tile instanceof IAspectContainer){
            ItemStack is = ctx.getItem();
            IAspectContainer container = (IAspectContainer) tile;
            if (full && container.tryAdd(getAspect(is), 10) != 0) {
                if (!ctx.getPlayer().isCreative()) {
                    is.shrink(1);
                    ItemStack replace = new ItemStack(ModItems.phialEmpty);
                    ctx.getPlayer().addItemStackToInventory(replace);
                }
                return ActionResultType.CONSUME;
            }
            if (!full){
                Aspect[] arr = container.readList().getAspectsSortedByAmount();
                if (arr.length > 0 && container.tryTake(arr[0], 10)){
                    if (!ctx.getPlayer().isCreative()) {
                        is.shrink(1);
                        ItemStack replace = makePhial(arr[0]);
                        ctx.getPlayer().addItemStackToInventory(replace);
                    }
                    return ActionResultType.CONSUME;
                }
            }
        }
        return super.onItemUse(ctx);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)){
            for (Aspect a : Aspects.aspects.values()){
                if (a != Aspects.EMPTY)
                    items.add(makePhial(a));
            }
            items.add(new ItemStack(ModItems.phialEmpty));
        }
    }

    public static Aspect getAspect(ItemStack is){
        Aspect res = Aspects.get(Utils.safeTag(is).getString("key"));
        return res != null ? res : Aspects.EMPTY;
    }

    public static void setAspect(ItemStack is, Aspect as){
        Utils.safeTag(is).putString("key", as.tag);
    }

    public static ItemStack makePhial(Aspect aspect) {
        ItemStack is = new ItemStack(ModItems.phialFull);
        setAspect(is, aspect);
        return is;
    }

    /*
    public ActionResultType onItemUseFirst(PlayerEntity player, World world, BlockPos pos, Direction side, float f1, float f2, float f3, Hand hand) {
        BlockState bi = world.getBlockState(pos);
        if (player.getHeldItem(hand).getItemDamage() == 0 && bi.getBlock() == BlocksTC.alembic) {
            TileAlembic tile = (TileAlembic) world.getTileEntity(pos);
            if (tile.amount >= base) {
                if (world.isRemote) {
                    player.swingArm(hand);
                    return ActionResultType.PASS;
                }
                ItemStack phial = new ItemStack(this, 1, 1);
                setAspects(phial, new AspectList().add(tile.aspect, base));
                if (tile.takeFromContainer(tile.aspect, base)) {
                    player.getHeldItem(hand).shrink(1);
                    if (!player.inventory.addItemStackToInventory(phial)) {
                        world.addEntity(new ItemEntity(world, player.getPosX(), player.getPosY(), player.getPosZ(), phial));
                    }
                    player.playSound(SoundEvents.ITEM_BOTTLE_FILL, 0.25f, 1.0f);
                    player.inventoryContainer.detectAndSendChanges();
                    return ActionResultType.SUCCESS;
                }
            }
        }
        if (player.getHeldItem(hand).getItemDamage() == 0 && (bi.getBlock() == BlocksTC.jarNormal || bi.getBlock() == BlocksTC.jarVoid)) {
            TileJarFillable tile2 = (TileJarFillable) world.getTileEntity(pos);
            if (tile2.amount >= base) {
                if (world.isRemote) {
                    player.swingArm(hand);
                    return ActionResultType.PASS;
                }
                Aspect asp = Aspect.getAspect(tile2.aspect.getTag());
                if (tile2.takeFromContainer(asp, base)) {
                    player.getHeldItem(hand).shrink(1);
                    ItemStack phial2 = new ItemStack(this, 1, 1);
                    setAspects(phial2, new AspectList().add(asp, base));
                    if (!player.inventory.addItemStackToInventory(phial2)) {
                        world.addEntity(new ItemEntity(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, phial2));
                    }
                    player.playSound(SoundEvents.ITEM_BOTTLE_FILL, 0.25f, 1.0f);
                    player.inventoryContainer.detectAndSendChanges();
                    return ActionResultType.SUCCESS;
                }
            }
        }
        AspectList al = getAspects(player.getHeldItem(hand));
        if (al != null && al.size() == 1) {
            Aspect aspect = al.getAspects()[0];
            if (player.getHeldItem(hand).getItemDamage() != 0 && (bi.getBlock() == BlocksTC.jarNormal || bi.getBlock() == BlocksTC.jarVoid)) {
                TileJarFillable tile3 = (TileJarFillable) world.getTileEntity(pos);
                if (tile3.amount <= 250 - base && tile3.doesContainerAccept(aspect)) {
                    if (world.isRemote) {
                        player.swingArm(hand);
                        return ActionResultType.PASS;
                    }
                    if (tile3.addToContainer(aspect, base) == 0) {
                        world.markAndNotifyBlock(pos, world.getChunkAt(pos), bi, bi, 3);
                        tile3.markDirty();
                        player.getHeldItem(hand).shrink(1);
                        if (!player.inventory.addItemStackToInventory(new ItemStack(this, 1, 0))) {
                            world.addEntity(new ItemEntity(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, new ItemStack(this, 1, 0)));
                        }
                        player.playSound(SoundEvents.ITEM_BOTTLE_FILL, 0.25f, 1.0f);
                        player.inventoryContainer.detectAndSendChanges();
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return ActionResultType.PASS;
    }*/
}
