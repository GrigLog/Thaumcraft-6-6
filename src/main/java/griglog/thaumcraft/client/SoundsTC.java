package griglog.thaumcraft.client;

import griglog.thaumcraft.Thaumcraft;
import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;

public class SoundsTC {
    public static SoundEvent zap = makeSoundEvent("zap");
    public static SoundEvent heartbeat = makeSoundEvent("heartbeat");
    public static SoundEvent spill = makeSoundEvent("spill");
    public static SoundEvent bubble = makeSoundEvent("bubble");
    public static SoundEvent creak = makeSoundEvent("creak");
    public static SoundEvent squeek = makeSoundEvent("squeek");
    public static SoundEvent jar = makeSoundEvent("jar");
    public static SoundEvent swarm = makeSoundEvent("swarm");
    public static SoundEvent swarmattack = makeSoundEvent("swarmattack");
    public static SoundEvent fly = makeSoundEvent("fly");
    public static SoundEvent key = makeSoundEvent("key");
    public static SoundEvent ticks = makeSoundEvent("ticks");
    public static SoundEvent clack = makeSoundEvent("clack");
    public static SoundEvent pump = makeSoundEvent("pump");
    public static SoundEvent poof = makeSoundEvent("poof");
    public static SoundEvent page = makeSoundEvent("page");
    public static SoundEvent pageturn = makeSoundEvent("pageturn");
    public static SoundEvent learn = makeSoundEvent("learn");
    public static SoundEvent write = makeSoundEvent("write");
    public static SoundEvent erase = makeSoundEvent("erase");
    public static SoundEvent brain = makeSoundEvent("brain");
    public static SoundEvent crystal = makeSoundEvent("crystal");
    public static SoundEvent wispdead = makeSoundEvent("wispdead");
    public static SoundEvent wisplive = makeSoundEvent("wisplive");
    public static SoundEvent wand = makeSoundEvent("wand");
    public static SoundEvent wandfail = makeSoundEvent("wandfail");
    public static SoundEvent rumble = makeSoundEvent("rumble");
    public static SoundEvent ice = makeSoundEvent("ice");
    public static SoundEvent jacobs = makeSoundEvent("jacobs");
    public static SoundEvent hhoff = makeSoundEvent("hhoff");
    public static SoundEvent hhon = makeSoundEvent("hhon");
    public static SoundEvent pech_idle = makeSoundEvent("pech_idle");
    public static SoundEvent pech_trade = makeSoundEvent("pech_trade");
    public static SoundEvent pech_dice = makeSoundEvent("pech_dice");
    public static SoundEvent pech_hit = makeSoundEvent("pech_hit");
    public static SoundEvent pech_death = makeSoundEvent("pech_death");
    public static SoundEvent pech_charge = makeSoundEvent("pech_charge");
    public static SoundEvent shock = makeSoundEvent("shock");
    public static SoundEvent craftfail = makeSoundEvent("craftfail");
    public static SoundEvent scan = makeSoundEvent("scan");
    public static SoundEvent craftstart = makeSoundEvent("craftstart");
    public static SoundEvent runicShieldEffect = makeSoundEvent("runicshieldeffect");
    public static SoundEvent runicShieldCharge = makeSoundEvent("runicshieldcharge");
    public static SoundEvent wind = makeSoundEvent("wind");
    public static SoundEvent tool = makeSoundEvent("tool");
    public static SoundEvent gore = makeSoundEvent("gore");
    public static SoundEvent tentacle = makeSoundEvent("tentacle");
    public static SoundEvent upgrade = makeSoundEvent("upgrade");
    public static SoundEvent whispers = makeSoundEvent("whispers");
    public static SoundEvent monolith = makeSoundEvent("monolith");
    public static SoundEvent infuser = makeSoundEvent("infuser");
    public static SoundEvent infuserstart = makeSoundEvent("infuserstart");
    public static SoundEvent egidle = makeSoundEvent("egidle");
    public static SoundEvent egattack = makeSoundEvent("egattack");
    public static SoundEvent egdeath = makeSoundEvent("egdeath");
    public static SoundEvent egscreech = makeSoundEvent("egscreech");
    public static SoundEvent crabclaw = makeSoundEvent("crabclaw");
    public static SoundEvent crabdeath = makeSoundEvent("crabdeath");
    public static SoundEvent crabtalk = makeSoundEvent("crabtalk");
    public static SoundEvent chant = makeSoundEvent("chant");
    public static SoundEvent coins = makeSoundEvent("coins");
    public static SoundEvent urnbreak = makeSoundEvent("urnbreak");
    public static SoundEvent evilportal = makeSoundEvent("evilportal");
    public static SoundEvent grind = makeSoundEvent("grind");
    public static SoundEvent dust = makeSoundEvent("dust");

    public static SoundType GORE = new ForgeSoundType(0.5f, 1.0f, () -> gore, () -> gore, () -> gore, () -> gore, () -> gore);
    public static SoundType CRYSTAL = new ForgeSoundType(0.5f, 1.0f, () -> crystal, () -> crystal, () -> crystal, () -> crystal, () -> crystal);
    public static SoundType JAR = new ForgeSoundType(0.5f, 1.0f, () -> jar, () -> jar, () -> jar, () -> jar, () -> jar);
    public static SoundType URN = new ForgeSoundType(0.5f, 1.5f, () -> urnbreak, () -> urnbreak, () -> urnbreak, () -> urnbreak, () -> urnbreak);

    static SoundEvent makeSoundEvent(String name){
        return new SoundEvent(new ResourceLocation(Thaumcraft.id, name)).setRegistryName(name);
    }
}
