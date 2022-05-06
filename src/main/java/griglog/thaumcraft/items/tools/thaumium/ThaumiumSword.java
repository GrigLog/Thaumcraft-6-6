package griglog.thaumcraft.items.tools.thaumium;

import com.google.common.collect.ImmutableMultimap;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumTier;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.SwordItem;

public class ThaumiumSword extends SwordItem {
    public ThaumiumSword() {
        super(ThaumTier.THAUMIUM, 6, -2.4f, ModTab.props());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 6.5, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4f, AttributeModifier.Operation.ADDITION));
        attributeModifiers = builder.build();
        setRegistryName("thaumium_sword");
    }
}
