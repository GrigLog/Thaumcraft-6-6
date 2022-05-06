package griglog.thaumcraft.items;

import griglog.thaumcraft.items.tools.elemental.*;
import griglog.thaumcraft.items.tools.thaumium.*;
import griglog.thaumcraft.items.tools.void_.*;
import net.minecraft.item.Item;

public class ModItems {
    public static final Item voidSword = new VoidSword();
    public static final Item voidShovel = new VoidShovel();
    public static final Item voidPickaxe = new VoidPickaxe();
    public static final Item voidHoe = new VoidHoe();
    public static final Item voidAxe = new VoidAxe();

    public static final Item thaumiumSword = new ThaumiumSword();
    public static final Item thaumiumShovel = new ThaumiumShovel();
    public static final Item thaumiumPickaxe = new ThaumiumPickaxe();
    public static final Item thaumiumHoe = new ThaumiumHoe();
    public static final Item thaumiumAxe = new ThaumiumAxe();

    public static final Item elementalSword = new ElementalSword();
    public static final Item elementalShovel = new ElementalShovel();
    public static final Item elementalPickaxe = new ElementalPickaxe();
    public static final Item elementalHoe = new ElementalHoe();
    public static final Item elementalAxe = new ElementalAxe();

    public static final Item thaumiumIngot = ModTab.defaultItem("ingot_thaumium");
    public static final Item brassIngot = ModTab.defaultItem("ingot_brass");
    public static final Item voidIngot = ModTab.defaultItem("ingot_void");

    public static Item[] defaultModel = new Item[]{thaumiumIngot, brassIngot, voidIngot};
    public static Item[] toolModel = new Item[]{voidSword, voidShovel, voidPickaxe, voidHoe, voidAxe,
        thaumiumSword, thaumiumShovel, thaumiumPickaxe, thaumiumHoe, thaumiumAxe,
        elementalSword, elementalShovel, elementalPickaxe, elementalHoe, elementalAxe};
}