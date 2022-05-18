package griglog.thaumcraft.entity;

import griglog.thaumcraft.utils.EntityWrapper;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class CrucibleItem extends ItemEntity {
    public static EntityType<CrucibleItem> type =
        new EntityWrapper<CrucibleItem>(CrucibleItem::new,
            EntityClassification.MISC, "special_item")
            .sized(0.25f, 0.25f)
            .clientTrackingRange(8)
            .updateInterval(20).get();
    public CrucibleItem(EntityType<? extends ItemEntity> type, World world) {
        super(type, world);
    }

    public CrucibleItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack) {
        super(type, par1World);
        setPosition(par2, par4, par6);
        setItem(par8ItemStack);
        rotationYaw = (float) (Math.random() * 360.0);
        setMotion(Math.random() * 0.2 - 0.1, 0.2, Math.random() * 0.2 - 0.1);
    }

    public CrucibleItem(World par1World) {
        this(type, par1World);
    }

    public void tick() {
        if (ticksExisted > 1) {
            double motionY = getMotion().y;
            if (motionY > 0.0) {
                motionY *= 0.9;
            }
            motionY += 0.04;
            setMotion(getMotion().x, motionY, getMotion().z);
            super.tick();
        }
    }

    public boolean attackEntityFrom(DamageSource source, float damage) {
        return !source.isExplosion() && super.attackEntityFrom(source, damage);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
