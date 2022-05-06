package griglog.thaumcraft.items.tools.thaumium;

import com.google.common.collect.ImmutableSet;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.AxeItem;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class ThaumiumAxe extends AxeItem {
    public ThaumiumAxe() {
        super(ThaumTier.THAUMIUM, 9, -3.1f, ModTab.props());
        setRegistryName("thaumium_axe");
    }

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return ImmutableSet.of(ToolType.AXE);
    }
}
