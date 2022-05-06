package griglog.thaumcraft.items.tools.thaumium;

import com.google.common.collect.ImmutableSet;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.HoeItem;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class ThaumiumHoe extends HoeItem {
    public ThaumiumHoe() {
        super(ThaumTier.THAUMIUM, 1, -1, ModTab.props());
        setRegistryName("thaumium_hoe");
    }

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return ImmutableSet.of(ToolType.HOE);
    }
}
