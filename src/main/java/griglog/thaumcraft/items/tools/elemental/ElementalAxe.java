package griglog.thaumcraft.items.tools.elemental;

import com.google.common.collect.ImmutableSet;
import com.google.common.primitives.Doubles;
import griglog.thaumcraft.items.infusions.InfusionEnchantment;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumTier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.List;
import java.util.Set;

public class ElementalAxe extends AxeItem {
    public ElementalAxe() {
        super(ThaumTier.ELEMENTAL, 10, -3f, ModTab.props());
        setRegistryName("elemental_axe");
    }

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return ImmutableSet.of(ToolType.AXE);
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.RARE;
    }

    public UseAction getUseAction(ItemStack itemstack) {
        return UseAction.BOW;
    }

    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
        playerIn.setActiveHand(hand);
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(hand));
    }

    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        List<ItemEntity> stuff = player.world.getEntitiesWithinAABB(ItemEntity.class, player.getBoundingBox().grow(10));
        if (stuff.size() > 0) {
            for (ItemEntity e : stuff) {
                if (e.isAlive()) {
                    double dx = e.getPosX() - player.getPosX();
                    double dy = e.getPosY() - player.getPosY() + player.getEyeHeight() / 2.0f;
                    double dz = e.getPosZ() - player.getPosZ();
                    double len = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
                    dx /= len;
                    dy /= len;
                    dz /= len;
                    double k = 0.3;
                    double x = e.getMotion().x;
                    double y = e.getMotion().y;
                    double z = e.getMotion().z;
                    e.setMotion(e.getMotion().subtract(dx * k, dy * k - 0.1, dz * k));
                    e.setMotion(new Vector3d(
                        Doubles.constrainToRange(x - dx * k, -0.25, 0.25),
                        Doubles.constrainToRange(y - dy * k + 0.1, -0.25, 0.25),
                        Doubles.constrainToRange(z - dz * k, -0.25, 0.25)
                    ));
                    if (player.world.isRemote) {
                        //FXDispatcher.INSTANCE.crucibleBubble((float) e.getPosX() + (player.world.rand.nextFloat() - player.world.rand.nextFloat()) * 0.2f, (float) e.getPosY() + e.getHeight() + (player.world.rand.nextFloat() - player.world.rand.nextFloat()) * 0.2f, (float) e.getPosZ() + (player.world.rand.nextFloat() - player.world.rand.nextFloat()) * 0.2f, 0.33f, 0.33f, 1.0f);
                    }
                }
            }
        }
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)) {
            ItemStack is = new ItemStack(this);
            InfusionEnchantment.add(is, InfusionEnchantment.BURROWING, 1);
            InfusionEnchantment.add(is, InfusionEnchantment.COLLECTOR, 1);
            items.add(is);
        } else {
            super.fillItemGroup(group, items);
        }
    }
}
