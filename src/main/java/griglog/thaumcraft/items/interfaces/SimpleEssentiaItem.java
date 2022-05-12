package griglog.thaumcraft.items.interfaces;

import griglog.thaumcraft.aspect.AspectHelper;
import griglog.thaumcraft.aspect.AspectList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SimpleEssentiaItem extends Item implements IEssentiaContainerItem {
    protected int amount;
    public SimpleEssentiaItem(Properties props, int base) {
        super(props.maxStackSize(64).maxDamage(0));
        this.amount = base;
    }

    @Override
    public AspectList getAspects(ItemStack itemstack) {
        return AspectHelper.getList(itemstack);
    }

    @Override
    public void setAspects(ItemStack itemstack, AspectList aspects) {
        AspectHelper.writeList(itemstack, aspects);
    }

    /*
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && !stack.hasTag()) {
            Aspect[] displayAspects = Aspects.aspects.values().toArray(new Aspect[] {});
            setAspects(stack, new AspectList().add(displayAspects[world.rand.nextInt(displayAspects.length)], amount));
        }
        super.inventoryTick(stack, world, entity, itemSlot, isSelected);
    }

    @Override
    public void onCreated(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isRemote && !stack.hasTag()) {
            Aspect[] displayAspects = Aspects.aspects.values().toArray(new Aspect[] {});
            setAspects(stack, new AspectList().add(displayAspects[world.rand.nextInt(displayAspects.length)], amount));
        }
    }*/
}
