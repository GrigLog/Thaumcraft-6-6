package griglog.thaumcraft.items.armor;

import griglog.thaumcraft.items.ModItems;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumMaterial;
import griglog.thaumcraft.items.interfaces.IVisDiscountGear;
import griglog.thaumcraft.utils.ItemUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public class ClothArmor extends DyeableArmorItem implements IVisDiscountGear {
    public ClothArmor(EquipmentSlotType slot) {
        super(ThaumMaterial.SPECIAL, slot, ModTab.props());
        setRegistryName("cloth_" + ItemUtils.slotName(slot));
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        switch (slot){
            default:
            case CHEST:
            case FEET:
                return (type == null) ? "thaumcraft:textures/entity/armor/robes_1.png" : "thaumcraft:textures/entity/armor/robes_1_overlay.png";
            case LEGS:
                return (type == null) ? "thaumcraft:textures/entity/armor/robes_2.png" : "thaumcraft:textures/entity/armor/robes_2_overlay.png";
        }
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.UNCOMMON;
    }

    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == ModItems.fabric || super.getIsRepairable(stack1, stack2);
    }

    public int getVisDiscount(ItemStack stack, PlayerEntity player) {
        switch (((ClothArmor)stack.getItem()).slot){
            default:
            case HEAD:
            case CHEST:
                return 3;
            case FEET:
                return 2;
        }
    }

    public int getColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 0x6A3880;
    }


    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getWorld();
        BlockState bs = world.getBlockState(context.getPos());
        if (!world.isRemote && bs.getBlock() == Blocks.CAULDRON){
            int level = bs.get(CauldronBlock.LEVEL);
            if (level > 0){
                removeColor(stack);
            }
            ((CauldronBlock)bs.getBlock()).setWaterLevel(world, context.getPos(), bs, level - 1);
            return ActionResultType.SUCCESS;
        }
        return super.onItemUseFirst(stack, context);
    }
}
