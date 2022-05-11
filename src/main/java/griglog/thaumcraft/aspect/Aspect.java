package griglog.thaumcraft.aspect;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Aspect {
    String tag;
    Aspect[] components;
    int color;
    private String chatcolor;
    ResourceLocation image;
    int blend;
    /**
     * For easy reference of what compounds are made up of
     */
    public static HashMap<Integer, Aspect> mixList = new HashMap<Integer, Aspect>();

    /**
     * Use this constructor to register your own aspects.
     *
     * @param tag        the key that will be used to reference this aspect, as well as its latin display name
     * @param color      color to display the tag in
     * @param components the aspects this one is formed from
     * @param image      ResourceLocation pointing to a 32x32 icon of the aspect
     * @param blend      GL11 blendmode (1 or 771). Used for rendering nodes. Default is 1
     */
    public Aspect(String tag, int color, Aspect[] components, ResourceLocation image, int blend) {
        if (aspects.containsKey(tag))
            throw new IllegalArgumentException(tag + " already registered!");
        this.tag = tag;
        this.components = components;
        this.color = color;
        this.image = image;
        this.blend = blend;
        aspects.put(tag, this);
        //ScanningManager.addScannableThing(new ScanAspect("!" + tag, this));
        if (components != null) {
            int h = (components[0].getTag() + components[1].getTag()).hashCode();
            mixList.put(h, this);
        }
    }

    /**
     * Shortcut constructor I use for the default aspects - you shouldn't be using this.
     */
    public Aspect(String tag, int color, Aspect[] components) {
        this(tag, color, components, new ResourceLocation("thaumcraft", "textures/aspects/" + tag.toLowerCase() + ".png"), 1);
    }

    /**
     * Shortcut constructor I use for the default aspects - you shouldn't be using this.
     */
    public Aspect(String tag, int color, Aspect[] components, int blend) {
        this(tag, color, components, new ResourceLocation("thaumcraft", "textures/aspects/" + tag.toLowerCase() + ".png"), blend);
    }

    /**
     * Shortcut constructor I use for the primal aspects -
     * you shouldn't use this as making your own primal aspects will break all the things.
     */
    public Aspect(String tag, int color, String chatcolor, int blend) {
        this(tag, color, (Aspect[]) null, blend);
        setChatcolor(chatcolor);
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return WordUtils.capitalizeFully(tag);
    }

    public ITextComponent getLocalizedDescription() {
        return new TranslationTextComponent("tc.aspect." + tag);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Aspect[] getComponents() {
        return components;
    }

    public void setComponents(Aspect[] components) {
        this.components = components;
    }

    public ResourceLocation getImage() {
        return image;
    }

    public static Aspect getAspect(String tag) {
        return aspects.get(tag);
    }

    public int getBlend() {
        return blend;
    }

    public void setBlend(int blend) {
        this.blend = blend;
    }

    public boolean isPrimal() {
        return getComponents() == null || getComponents().length != 2;
    }

    // /////////////////////////////
    private static ArrayList<Aspect> primals = new ArrayList<Aspect>();

    public static ArrayList<Aspect> getPrimalAspects() {
        if (primals.isEmpty()) {
            Collection<Aspect> pa = aspects.values();
            for (Aspect aspect : pa) {
                if (aspect.isPrimal())
                    primals.add(aspect);
            }
        }
        return primals;
    }

    private static ArrayList<Aspect> compounds = new ArrayList<Aspect>();

    public static ArrayList<Aspect> getCompoundAspects() {
        if (compounds.isEmpty()) {
            Collection<Aspect> pa = aspects.values();
            for (Aspect aspect : pa) {
                if (!aspect.isPrimal())
                    compounds.add(aspect);
            }
        }
        return compounds;
    }

    public String getChatcolor() {
        return chatcolor;
    }

    public void setChatcolor(String chatcolor) {
        this.chatcolor = chatcolor;
    }

    // /////////////////////////////
    public static LinkedHashMap<String, Aspect> aspects = new LinkedHashMap<String, Aspect>();
    // PRIMAL
    public static Aspect AIR = new Aspect("aer", 0xffff7e, "e", 1);
    public static Aspect EARTH = new Aspect("terra", 0x56c000, "2", 1);
    public static Aspect FIRE = new Aspect("ignis", 0xff5a01, "c", 1);
    public static Aspect WATER = new Aspect("aqua", 0x3cd4fc, "3", 1);
    public static Aspect ORDER = new Aspect("ordo", 0xd5d4ec, "7", 1);
    public static Aspect ENTROPY = new Aspect("perditio", 0x404040, "8", 771);
    // SECONDARY (PRIMAL + PRIMAL)
    public static Aspect VOID = new Aspect("vacuos", 0x888888, new Aspect[]{AIR, ENTROPY}, 771);
    public static Aspect LIGHT = new Aspect("lux", 0xffffc0, new Aspect[]{AIR, FIRE});
    public static Aspect MOTION = new Aspect("motus", 0xcdccf4, new Aspect[]{AIR, ORDER});
    public static Aspect COLD = new Aspect("gelum", 0xe1ffff, new Aspect[]{FIRE, ENTROPY});
    public static Aspect CRYSTAL = new Aspect("vitreus", 0x80ffff, new Aspect[]{EARTH, AIR});
    public static Aspect METAL = new Aspect("metallum", 0xb5b5cd, new Aspect[]{EARTH, ORDER});
    public static Aspect LIFE = new Aspect("victus", 0xde0005, new Aspect[]{EARTH, WATER});
    public static Aspect DEATH = new Aspect("mortuus", 0x6a0005, new Aspect[]{WATER, ENTROPY});
    public static Aspect ENERGY = new Aspect("potentia", 0xc0ffff, new Aspect[]{ORDER, FIRE});
    public static Aspect EXCHANGE = new Aspect("permutatio", 0x578357, new Aspect[]{ENTROPY, ORDER});
    // public static Aspect WEATHER = new Aspect("tempestas",0xFFFFFF, new Aspect[] {AIR, WATER});
    // public static Aspect ?? = new Aspect("??",0xcdccf4, new Aspect[] {FIRE, EARTH});
    // public static Aspect ?? = new Aspect("??",0xcdccf4, new Aspect[] {FIRE, WATER});
    // public static Aspect ?? = new Aspect("??",0xcdccf4, new Aspect[] {ORDER, WATER});
    // public static Aspect ?? = new Aspect("??",0xcdccf4, new Aspect[] {EARTH, ENTROPY});
    // TERTIARY
    public static Aspect MAGIC = new Aspect("praecantatio", 0xcf00ff, new Aspect[]{ENERGY, AIR});
    public static Aspect AURA = new Aspect("auram", 0xffc0ff, new Aspect[]{MAGIC, AIR});
    public static Aspect ALCHEMY = new Aspect("alkimia", 0x23ac9d, new Aspect[]{MAGIC, WATER});
    public static Aspect FLUX = new Aspect("vitium", 0x800080, new Aspect[]{ENTROPY, MAGIC});
    public static Aspect DARKNESS = new Aspect("tenebrae", 0x222222, new Aspect[]{VOID, LIGHT});
    public static Aspect ELDRITCH = new Aspect("alienis", 0x805080, new Aspect[]{VOID, DARKNESS});
    public static Aspect FLIGHT = new Aspect("volatus", 0xe7e7d7, new Aspect[]{AIR, MOTION});
    public static Aspect PLANT = new Aspect("herba", 0x01ac00, new Aspect[]{LIFE, EARTH});
    public static Aspect TOOL = new Aspect("instrumentum", 0x4040ee, new Aspect[]{METAL, ENERGY});
    public static Aspect CRAFT = new Aspect("fabrico", 0x809d80, new Aspect[]{EXCHANGE, TOOL});
    public static Aspect MECHANISM = new Aspect("machina", 0x8080a0, new Aspect[]{MOTION, TOOL});
    public static Aspect TRAP = new Aspect("vinculum", 0x9a8080, new Aspect[]{MOTION, ENTROPY});
    public static Aspect SOUL = new Aspect("spiritus", 0xebebfb, new Aspect[]{LIFE, DEATH});
    public static Aspect MIND = new Aspect("cognitio", 0xf9967f, new Aspect[]{FIRE, SOUL});
    public static Aspect SENSES = new Aspect("sensus", 0xc0ffc0, new Aspect[]{AIR, SOUL});
    public static Aspect AVERSION = new Aspect("aversio", 0xc05050, new Aspect[]{SOUL, ENTROPY});
    public static Aspect PROTECT = new Aspect("praemunio", 0x00c0c0, new Aspect[]{SOUL, EARTH});
    public static Aspect DESIRE = new Aspect("desiderium", 0xe6be44, new Aspect[]{SOUL, VOID});
    public static Aspect UNDEAD = new Aspect("exanimis", 0x3a4000, new Aspect[]{MOTION, DEATH});
    public static Aspect BEAST = new Aspect("bestia", 0x9f6409, new Aspect[]{MOTION, LIFE});
    public static Aspect MAN = new Aspect("humanus", 0xffd7c0, new Aspect[]{SOUL, LIFE});
}