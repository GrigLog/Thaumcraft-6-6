package griglog.thaumcraft.items.tools.elemental;

import com.google.common.collect.ImmutableSet;
import griglog.thaumcraft.items.infusions.InfusionEnchantment;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumTier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class ElementalPickaxe extends PickaxeItem{
    public ElementalPickaxe() {
        super(ThaumTier.ELEMENTAL, 5, -2.8f, ModTab.props());
        setRegistryName("elemental_pick");
    }

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return ImmutableSet.of(ToolType.PICKAXE);
    }

    @Override
    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.RARE;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (!player.world.isRemote) {
            if (!(entity instanceof PlayerEntity) || entity.getServer().isPVPEnabled()) {
                entity.setFire(2);
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)) {
            ItemStack is = new ItemStack(this);
            InfusionEnchantment.add(is, InfusionEnchantment.REFINING, 1);
            InfusionEnchantment.add(is, InfusionEnchantment.SOUNDING, 2);
            items.add(is);
        } else {
            super.fillItemGroup(group, items);
        }
    }
}
