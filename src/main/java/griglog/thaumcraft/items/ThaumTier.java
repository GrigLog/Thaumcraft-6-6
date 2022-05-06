package griglog.thaumcraft.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public class ThaumTier implements IItemTier {
    public static final ThaumTier THAUMIUM = new ThaumTier(3, 500, 7F, -1, 22, () -> Ingredient.fromItems(ModItems.thaumiumIngot));
    public static final ThaumTier ELEMENTAL = new ThaumTier(3, 1500, 9F, -1, 18, () -> Ingredient.fromItems(ModItems.thaumiumIngot));
    public static final ThaumTier VOID = new ThaumTier(4, 150, 12F, -1, 10, () -> Ingredient.fromItems(ModItems.voidIngot));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    private ThaumTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyValue<>(repairMaterialIn);
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }
}
