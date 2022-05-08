package griglog.thaumcraft.items;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class ThaumMaterial implements IArmorMaterial {
    public static ThaumMaterial THAUMIUM = new ThaumMaterial("THAUMIUM", 25, new int[] { 2, 5, 6, 2 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F, 0, () -> Ingredient.fromItems(ModItems.thaumiumIngot));
    public static ThaumMaterial SPECIAL = new ThaumMaterial("SPECIAL", 25, new int[] { 1, 2, 3, 1 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 0, () -> null);
    public static ThaumMaterial VOID = new ThaumMaterial("VOID", 10, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 1F, 0, () -> Ingredient.fromItems(ModItems.voidIngot));
    public static ThaumMaterial VOID_ROBE = new ThaumMaterial("VOIDROBE", 18, new int[] { 4, 7, 9, 4 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2f, 0, () -> Ingredient.fromItems(ModItems.voidIngot));
    public static ThaumMaterial FORTRESS = new ThaumMaterial("FORTRESS", 40, new int[] { 3, 6, 7, 3 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3f, 0, () -> Ingredient.fromItems(ModItems.thaumiumIngot));
    public static ThaumMaterial CULTIST_PLATE = new ThaumMaterial("CULTIST_PLATE", 18, new int[]{2, 5, 6, 2}, 13, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 0, () -> Ingredient.fromItems(Items.IRON_INGOT));
    public static ThaumMaterial CULTIST_ROBE = new ThaumMaterial("CULTIST_ROBE", 17, new int[]{2, 4, 5, 2}, 13, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F, 0, () -> Ingredient.fromItems(Items.IRON_INGOT));
    public static ThaumMaterial CULTIST_LEADER = new ThaumMaterial("CULTIST_LEADER", 30, new int[] { 3, 6, 7, 3 }, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1F, 0, () -> Ingredient.fromItems(Items.IRON_INGOT));


    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairMaterial;

    private ThaumMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    public int getDurability(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    /**
     * Gets the percentage of knockback resistance provided by armor of the material.
     */
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
