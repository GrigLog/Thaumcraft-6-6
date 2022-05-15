package griglog.thaumcraft.items;

import griglog.thaumcraft.items.armor.*;
import griglog.thaumcraft.items.misc.Phial;
import griglog.thaumcraft.items.tools.elemental.*;
import griglog.thaumcraft.items.tools.thaumium.*;
import griglog.thaumcraft.items.tools.void_.*;
import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

import static griglog.thaumcraft.blocks.ModBlocks.*;
import static griglog.thaumcraft.blocks.ModBlocks.crucible;

public class ModItems {
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

    public static final Item voidSword = new VoidSword();
    public static final Item voidShovel = new VoidShovel();
    public static final Item voidPickaxe = new VoidPickaxe();
    public static final Item voidHoe = new VoidHoe();
    public static final Item voidAxe = new VoidAxe();

    public static final Item primalCrusher = new PrimalCrusher();

    public static final Item thaumiumIngot = defaultItem("ingot_thaumium");
    public static final Item brassIngot = defaultItem("ingot_brass");
    public static final Item voidIngot = defaultItem("ingot_void");
    public static final Item quicksilver = defaultItem("quicksilver");
    public static final Item ironCluster = defaultItem("cluster_iron");
    public static final Item goldCluster = defaultItem("cluster_gold");
    public static final Item copperCluster = defaultItem("cluster_copper");
    public static final Item tinCluster = defaultItem("cluster_tin");
    public static final Item silverCluster = defaultItem("cluster_silver");
    public static final Item leadCluster = defaultItem("cluster_lead");
    public static final Item cinnabarCluster = defaultItem("cluster_cinnabar");
    public static final Item quartzCluster = defaultItem("cluster_quartz");
    //"iron","copper","tin","silver","lead","quicksilver","thaumium","void","brass","quartz","rareearth"
    public static final Item quicksilverNugget = defaultItem("nugget_quicksilver");
    public static final Item thaumiumNugget = defaultItem("nugget_thaumium");
    public static final Item voidNugget = defaultItem("nugget_void");
    public static final Item brassNugget = defaultItem("nugget_brass");
    public static final Item quartzNugget = defaultItem("nugget_quartz");

    public static final Item rareEarth = defaultItem("rare_earth");
    public static final Item fabric = defaultItem("fabric");

    public static final Item travellerBoots = new TravellerBoots();
    public static final Item thaumiumHelmet = new ThaumiumArmor(EquipmentSlotType.HEAD);
    public static final Item thaumiumChestplate = new ThaumiumArmor(EquipmentSlotType.CHEST);
    public static final Item thaumiumLeggings = new ThaumiumArmor(EquipmentSlotType.LEGS);
    public static final Item thaumiumBoots = new ThaumiumArmor(EquipmentSlotType.FEET);
    public static final Item voidHelmet = new VoidArmor(EquipmentSlotType.HEAD);
    public static final Item voidChestplate = new VoidArmor(EquipmentSlotType.CHEST);
    public static final Item voidLeggings = new VoidArmor(EquipmentSlotType.LEGS);
    public static final Item voidBoots = new VoidArmor(EquipmentSlotType.FEET);
    public static final Item cultBoots = new CultistBoots();
    public static final Item cultHelm = new CultistPlateArmor(EquipmentSlotType.HEAD);
    public static final Item cultChest = new CultistPlateArmor(EquipmentSlotType.CHEST);
    public static final Item cultLegs = new CultistPlateArmor(EquipmentSlotType.LEGS);
    public static final Item cultRobeHelm = new CultistRobeArmor(EquipmentSlotType.HEAD);
    public static final Item cultRobeChest = new CultistRobeArmor(EquipmentSlotType.CHEST);
    public static final Item cultRobeLegs = new CultistRobeArmor(EquipmentSlotType.LEGS);
    public static final Item clothChest = new ClothArmor(EquipmentSlotType.CHEST);
    public static final Item clothLegs = new ClothArmor(EquipmentSlotType.LEGS);
    public static final Item clothBoots = new ClothArmor(EquipmentSlotType.FEET);
    public static final Item fortressHelm = new FortressArmor(EquipmentSlotType.HEAD);
    public static final Item fortressChest = new FortressArmor(EquipmentSlotType.CHEST);
    public static final Item fortressLegs = new FortressArmor(EquipmentSlotType.LEGS);
    public static final Item voidRobeHelm = new VoidRobeArmor(EquipmentSlotType.HEAD);
    public static final Item voidRobeChest = new VoidRobeArmor(EquipmentSlotType.CHEST);
    public static final Item voidRobeLegs = new VoidRobeArmor(EquipmentSlotType.LEGS);

    public static final Item phialFull = new Phial(true);
    public static final Item phialEmpty = new Phial(false);

    public static final Block[] blockItems =  new Block[]{silverLog, silverLeaves, silverSapling, greatLeaves, greatLog, greatSapling, jar, crucible};

    public static Item defaultItem(String id){
        return new Item(ModTab.props()).setRegistryName(id);
    }
}
