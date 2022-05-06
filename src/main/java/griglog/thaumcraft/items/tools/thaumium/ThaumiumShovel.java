package griglog.thaumcraft.items.tools.thaumium;

import com.google.common.collect.ImmutableSet;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class ThaumiumShovel extends ShovelItem {
    public ThaumiumShovel() {
        super(ThaumTier.THAUMIUM, 4.5f, -3f, ModTab.props());
        setRegistryName("thaumium_shovel");
    }

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return ImmutableSet.of(ToolType.SHOVEL);
    }
}
