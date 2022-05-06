package griglog.thaumcraft.items.tools.elemental;

import griglog.thaumcraft.client.SoundsTC;
import griglog.thaumcraft.items.infusions.InfusionEnchantment;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumTier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class ElementalSword extends SwordItem{
    public ElementalSword() {
        super(ThaumTier.ELEMENTAL, 7, -2.4f, ModTab.props());
        setRegistryName("elemental_sword");
    }
    @Override
    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.RARE;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
        playerIn.setActiveHand(hand);
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(hand));
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);
        int ticks = getUseDuration(stack) - count;
        double dy = player.getMotion().y;
        if (dy < 0.0) {
            dy /= 1.2;
            player.fallDistance /= 1.2f;
        }
        dy += 0.08;
        if (dy > 0.5) {
            dy = 0.2;
        }
        player.setMotion(player.getMotion().x, dy, player.getMotion().z);
        if (player instanceof ServerPlayerEntity) {
            //EntityUtils.resetFloatCounter((ServerPlayerEntity) player); TODO: WTF???
        }
        List<Entity> targets = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().grow(2.5, 2.5, 2.5));
        if (targets.size() > 0) {
            for (Entity target : targets) {
                if (!(target instanceof PlayerEntity) && target instanceof LivingEntity && !target.removed && (player.getRidingEntity() == null || player.getRidingEntity() != target)) {
                    Vector3d p = new Vector3d(player.getPosX(), player.getPosY(), player.getPosZ());
                    Vector3d t = new Vector3d(target.getPosX(), target.getPosY(), target.getPosZ());
                    double distance = p.distanceTo(t) + 0.1;
                    Vector3d r = new Vector3d(t.x - p.x, t.y - p.y, t.z - p.z).scale(0.4 / distance);
                    target.setMotion(target.getMotion().add(r));
                }
            }
        }
        if (player.world.isRemote) {
            int miny = (int) (player.getBoundingBox().minY - 2.0);
            if (player.isOnGround()) {
                miny = MathHelper.floor(player.getBoundingBox().minY);
            }
            for (int a = 0; a < 5; ++a) {
                //FXDispatcher.INSTANCE.smokeSpiral(player.getPosX(), player.getBoundingBox().minY + player.getHeight() / 2.0f, player.getPosZ(), 1.5f, player.world.rand.nextInt(360), miny, 14540253);
            }
            if (player.isOnGround()) {
                float r2 = player.world.rand.nextFloat() * 360.0f;
                float mx = -MathHelper.sin(r2 / 180.0f * 3.1415927f) / 5.0f;
                float mz = MathHelper.cos(r2 / 180.0f * 3.1415927f) / 5.0f;
                player.world.addParticle(ParticleTypes.SMOKE, player.getPosX(), player.getBoundingBox().minY + 0.10000000149011612, player.getPosZ(), mx, 0.0, mz);
            }
        } else if (ticks % 20 == 0) {
            player.playSound(SoundsTC.wind, 0.5f, 0.9f + player.world.rand.nextFloat() * 0.2f);
        }
        if (ticks % 20 == 0) {
            stack.damageItem(1, player, (e)->{});
        }
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)) {
            ItemStack is = new ItemStack(this);
            InfusionEnchantment.add(is, InfusionEnchantment.ARCING, 2);
            items.add(is);
        } else {
            super.fillItemGroup(group, items);
        }
    }
}
