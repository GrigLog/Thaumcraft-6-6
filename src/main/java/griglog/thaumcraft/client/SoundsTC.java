package griglog.thaumcraft.client;

import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;

public class SoundsTC
{
    public static SoundEvent zap;
    public static SoundEvent heartbeat;
    public static SoundEvent spill;
    public static SoundEvent bubble;
    public static SoundEvent creak;
    public static SoundEvent squeek;
    public static SoundEvent jar;
    public static SoundEvent swarm;
    public static SoundEvent swarmattack;
    public static SoundEvent fly;
    public static SoundEvent key;
    public static SoundEvent ticks;
    public static SoundEvent clack;
    public static SoundEvent pump;
    public static SoundEvent poof;
    public static SoundEvent page;
    public static SoundEvent learn;
    public static SoundEvent write;
    public static SoundEvent erase;
    public static SoundEvent brain;
    public static SoundEvent crystal;
    public static SoundEvent wispdead;
    public static SoundEvent wisplive;
    public static SoundEvent wand;
    public static SoundEvent wandfail;
    public static SoundEvent rumble;
    public static SoundEvent ice;
    public static SoundEvent jacobs;
    public static SoundEvent hhoff;
    public static SoundEvent hhon;
    public static SoundEvent pech_idle;
    public static SoundEvent pech_trade;
    public static SoundEvent pech_dice;
    public static SoundEvent pech_hit;
    public static SoundEvent pech_death;
    public static SoundEvent pech_charge;
    public static SoundEvent shock;
    public static SoundEvent craftfail;
    public static SoundEvent scan;
    public static SoundEvent craftstart;
    public static SoundEvent runicShieldEffect;
    public static SoundEvent runicShieldCharge;
    public static SoundEvent wind;
    public static SoundEvent tool;
    public static SoundEvent gore;
    public static SoundEvent tentacle;
    public static SoundEvent upgrade;
    public static SoundEvent whispers;
    public static SoundEvent monolith;
    public static SoundEvent infuser;
    public static SoundEvent infuserstart;
    public static SoundEvent egidle;
    public static SoundEvent egattack;
    public static SoundEvent egdeath;
    public static SoundEvent egscreech;
    public static SoundEvent crabclaw;
    public static SoundEvent crabdeath;
    public static SoundEvent crabtalk;
    public static SoundEvent chant;
    public static SoundEvent coins;
    public static SoundEvent urnbreak;
    public static SoundEvent evilportal;
    public static SoundEvent grind;
    public static SoundEvent dust;
    public static SoundEvent pageturn;
    public static SoundType GORE;
    public static SoundType CRYSTAL;
    public static SoundType JAR;
    public static SoundType URN;

    public static void registerSoundTypes() {
        SoundsTC.GORE = new SoundType(0.5f, 1.0f, SoundsTC.gore, SoundsTC.gore, SoundsTC.gore, SoundsTC.gore, SoundsTC.gore);
        SoundsTC.CRYSTAL = new SoundType(0.5f, 1.0f, SoundsTC.crystal, SoundsTC.crystal, SoundsTC.crystal, SoundsTC.crystal, SoundsTC.crystal);
        SoundsTC.JAR = new SoundType(0.5f, 1.0f, SoundsTC.jar, SoundsTC.jar, SoundsTC.jar, SoundsTC.jar, SoundsTC.jar);
        SoundsTC.URN = new SoundType(0.5f, 1.5f, SoundsTC.urnbreak, SoundsTC.urnbreak, SoundsTC.urnbreak, SoundsTC.urnbreak, SoundsTC.urnbreak);
    }

    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        SoundsTC.zap = getRegisteredSoundEvent(event, "thaumcraft:zap");
        SoundsTC.heartbeat = getRegisteredSoundEvent(event, "thaumcraft:heartbeat");
        SoundsTC.spill = getRegisteredSoundEvent(event, "thaumcraft:spill");
        SoundsTC.bubble = getRegisteredSoundEvent(event, "thaumcraft:bubble");
        SoundsTC.creak = getRegisteredSoundEvent(event, "thaumcraft:creak");
        SoundsTC.squeek = getRegisteredSoundEvent(event, "thaumcraft:squeek");
        SoundsTC.jar = getRegisteredSoundEvent(event, "thaumcraft:jar");
        SoundsTC.swarm = getRegisteredSoundEvent(event, "thaumcraft:swarm");
        SoundsTC.swarmattack = getRegisteredSoundEvent(event, "thaumcraft:swarmattack");
        SoundsTC.fly = getRegisteredSoundEvent(event, "thaumcraft:fly");
        SoundsTC.key = getRegisteredSoundEvent(event, "thaumcraft:key");
        SoundsTC.ticks = getRegisteredSoundEvent(event, "thaumcraft:ticks");
        SoundsTC.clack = getRegisteredSoundEvent(event, "thaumcraft:clack");
        SoundsTC.pump = getRegisteredSoundEvent(event, "thaumcraft:pump");
        SoundsTC.poof = getRegisteredSoundEvent(event, "thaumcraft:poof");
        SoundsTC.page = getRegisteredSoundEvent(event, "thaumcraft:page");
        SoundsTC.pageturn = getRegisteredSoundEvent(event, "thaumcraft:pageturn");
        SoundsTC.learn = getRegisteredSoundEvent(event, "thaumcraft:learn");
        SoundsTC.write = getRegisteredSoundEvent(event, "thaumcraft:write");
        SoundsTC.erase = getRegisteredSoundEvent(event, "thaumcraft:erase");
        SoundsTC.brain = getRegisteredSoundEvent(event, "thaumcraft:brain");
        SoundsTC.crystal = getRegisteredSoundEvent(event, "thaumcraft:crystal");
        SoundsTC.wispdead = getRegisteredSoundEvent(event, "thaumcraft:wispdead");
        SoundsTC.wisplive = getRegisteredSoundEvent(event, "thaumcraft:wisplive");
        SoundsTC.wand = getRegisteredSoundEvent(event, "thaumcraft:wand");
        SoundsTC.wandfail = getRegisteredSoundEvent(event, "thaumcraft:wandfail");
        SoundsTC.rumble = getRegisteredSoundEvent(event, "thaumcraft:rumble");
        SoundsTC.ice = getRegisteredSoundEvent(event, "thaumcraft:ice");
        SoundsTC.jacobs = getRegisteredSoundEvent(event, "thaumcraft:jacobs");
        SoundsTC.hhoff = getRegisteredSoundEvent(event, "thaumcraft:hhoff");
        SoundsTC.hhon = getRegisteredSoundEvent(event, "thaumcraft:hhon");
        SoundsTC.pech_idle = getRegisteredSoundEvent(event, "thaumcraft:pech_idle");
        SoundsTC.pech_trade = getRegisteredSoundEvent(event, "thaumcraft:pech_trade");
        SoundsTC.pech_dice = getRegisteredSoundEvent(event, "thaumcraft:pech_dice");
        SoundsTC.pech_hit = getRegisteredSoundEvent(event, "thaumcraft:pech_hit");
        SoundsTC.pech_death = getRegisteredSoundEvent(event, "thaumcraft:pech_death");
        SoundsTC.pech_charge = getRegisteredSoundEvent(event, "thaumcraft:pech_charge");
        SoundsTC.shock = getRegisteredSoundEvent(event, "thaumcraft:shock");
        SoundsTC.craftfail = getRegisteredSoundEvent(event, "thaumcraft:craftfail");
        SoundsTC.scan = getRegisteredSoundEvent(event, "thaumcraft:scan");
        SoundsTC.craftstart = getRegisteredSoundEvent(event, "thaumcraft:craftstart");
        SoundsTC.runicShieldEffect = getRegisteredSoundEvent(event, "thaumcraft:runicshieldeffect");
        SoundsTC.runicShieldCharge = getRegisteredSoundEvent(event, "thaumcraft:runicshieldcharge");
        SoundsTC.wind = getRegisteredSoundEvent(event, "thaumcraft:wind");
        SoundsTC.tool = getRegisteredSoundEvent(event, "thaumcraft:tool");
        SoundsTC.gore = getRegisteredSoundEvent(event, "thaumcraft:gore");
        SoundsTC.tentacle = getRegisteredSoundEvent(event, "thaumcraft:tentacle");
        SoundsTC.upgrade = getRegisteredSoundEvent(event, "thaumcraft:upgrade");
        SoundsTC.whispers = getRegisteredSoundEvent(event, "thaumcraft:whispers");
        SoundsTC.monolith = getRegisteredSoundEvent(event, "thaumcraft:monolith");
        SoundsTC.infuser = getRegisteredSoundEvent(event, "thaumcraft:infuser");
        SoundsTC.infuserstart = getRegisteredSoundEvent(event, "thaumcraft:infuserstart");
        SoundsTC.egidle = getRegisteredSoundEvent(event, "thaumcraft:egidle");
        SoundsTC.egattack = getRegisteredSoundEvent(event, "thaumcraft:egattack");
        SoundsTC.egdeath = getRegisteredSoundEvent(event, "thaumcraft:egdeath");
        SoundsTC.egscreech = getRegisteredSoundEvent(event, "thaumcraft:egscreech");
        SoundsTC.crabclaw = getRegisteredSoundEvent(event, "thaumcraft:crabclaw");
        SoundsTC.crabdeath = getRegisteredSoundEvent(event, "thaumcraft:crabdeath");
        SoundsTC.crabtalk = getRegisteredSoundEvent(event, "thaumcraft:crabtalk");
        SoundsTC.chant = getRegisteredSoundEvent(event, "thaumcraft:chant");
        SoundsTC.coins = getRegisteredSoundEvent(event, "thaumcraft:coins");
        SoundsTC.urnbreak = getRegisteredSoundEvent(event, "thaumcraft:urnbreak");
        SoundsTC.evilportal = getRegisteredSoundEvent(event, "thaumcraft:evilportal");
        SoundsTC.grind = getRegisteredSoundEvent(event, "thaumcraft:grind");
        SoundsTC.dust = getRegisteredSoundEvent(event, "thaumcraft:dust");
    }



    private static SoundEvent getRegisteredSoundEvent(RegistryEvent.Register<SoundEvent> event, String id) {
        SoundEvent soundevent = new SoundEvent(new ResourceLocation(id));
        soundevent.setRegistryName(new ResourceLocation(id));
        event.getRegistry().register(soundevent);
        return soundevent;
    }
}
