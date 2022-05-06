package griglog.thaumcraft.items.tools.void_;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class VoidHelper {
    public static void repairTick(ItemStack voidStack, Entity owner) {
        if (voidStack.isDamaged() && owner.ticksExisted % 20 == 0 && owner instanceof LivingEntity){
            voidStack.setDamage(voidStack.getDamage() - 1); // > 0 will be checked inside
        }
    }

    public static void applyWeakness(LivingEntity target, LivingEntity hitter) {
        if (!target.world.isRemote) {
            if (!(target instanceof PlayerEntity) || !(hitter instanceof PlayerEntity) || target.world.getServer().isPVPEnabled()) {
                target.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 60));
            }
        }
    }
}
