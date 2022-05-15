package griglog.thaumcraft.api;

import griglog.thaumcraft.aspect.AspectList;
import net.minecraft.item.ItemStack;

/**
 * @author azanor
 *
 * Used by wispy essences and essentia phials to hold their aspects.
 * Useful for similar item containers that store their aspect information in nbt form so TC
 * automatically picks up the aspects they contain.
 */
public interface IEssentiaContainerItem {
    AspectList getAspects(ItemStack itemstack);
    void setAspects(ItemStack itemstack, AspectList aspects);
}
// Example implementation
/*
	@Override
	public AspectList getAspects(ItemStack itemstack) {
		if (itemstack.hasTag()) {
			AspectList aspects = new AspectList();
			aspects.readFromNBT(itemstack.getTag());
			return aspects.size()>0?aspects:null;
		}
		return null;
	}

	@Override
	public void setAspects(ItemStack itemstack, AspectList aspects) {
		if (!itemstack.hasTag()) itemstack.setTag(new NBTTagCompound());
		aspects.writeToNBT(itemstack.getTag());
	}

	@Override
	public boolean ignoreContainedAspects() {return false;}
*/

