package griglog.thaumcraft.items.tools.thaumium;

import com.google.common.collect.ImmutableSet;
import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumTier;
import griglog.thaumcraft.items.infusions.BurrowingInfusion;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class ThaumiumPickaxe extends PickaxeItem {
    public ThaumiumPickaxe() {
        super(ThaumTier.THAUMIUM, 4, -2.8f, ModTab.props());
        setRegistryName("thaumium_pick");
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return ImmutableSet.of(ToolType.PICKAXE);
    }
}
