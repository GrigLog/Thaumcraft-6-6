package griglog.thaumcraft.items.tools.elemental;

import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumTier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class ElementalHoe extends HoeItem {
    public ElementalHoe() {
        super(ThaumTier.ELEMENTAL, 1, 0, ModTab.props());
        setRegistryName("elemental_hoe");
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.RARE;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null || player.isSneaking())
            return super.onItemUse(context);
        boolean madeFarmland = false;
        for (int xx = -1; xx <= 1; ++xx) {
            for (int zz = -1; zz <= 1; ++zz) {
                if (makeFarmland(context, player, context.getPos().add(xx, 0, zz)).isSuccessOrConsume()) {
                    if (context.getWorld().isRemote) {
                        //BlockPos pp = pos.add(xx, 0, zz);
                        //FXDispatcher.INSTANCE.drawBamf(pp.getX() + 0.5, pp.getY() + 1.01, pp.getZ() + 0.5, 0.3f, 0.12f, 0.1f, xx == 0 && zz == 0, false, Direction.UP);
                    }
                    madeFarmland = true;
                }
            }
        }
        if (!madeFarmland) {
            World world = context.getWorld();
            if (BoneMealItem.applyBonemeal(ItemStack.EMPTY, world, context.getPos(), player)) {
                if (!world.isRemote) {
                    player.getHeldItem(context.getHand()).attemptDamageItem(3, world.rand, (ServerPlayerEntity) player);
                    world.playBroadcastSound(2005, context.getPos(), 0);
                } else {
                    BoneMealItem.spawnBonemealParticles(world, context.getPos(), 0);
                    //FXDispatcher.INSTANCE.drawBlockMistParticles(context.getPos(), 4259648);
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    ActionResultType makeFarmland(ItemUseContext context, PlayerEntity player, BlockPos pos){
        return super.onItemUse(new ItemUseContext(player, context.getHand(),
            new BlockRayTraceResult(context.getHitVec(), context.getFace(), pos, context.isInside())));
    }
}
