package griglog.thaumcraft.aspect;

import griglog.thaumcraft.Thaumcraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.text.WordUtils;

public class Aspect {
    public String tag;
    public Aspect[] components;
    public int color;
    public String chatcolor;
    public ResourceLocation image;
    public int blend;

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
        if (Aspects.get(tag) != null)
            throw new IllegalArgumentException(tag + " already registered!");
        this.tag = tag;
        this.components = components;
        this.color = color;
        this.image = image;
        this.blend = blend;
        Aspects.aspects.put(tag, this);
        //ScanningManager.addScannableThing(new ScanAspect("!" + tag, this));
        if (components != null) {
            int h = (components[0].tag + components[1].tag).hashCode();
            Aspects.mixList.put(h, this);
        }
    }

    /**
     * Shortcut constructor I use for the default aspects - you shouldn't be using this.
     */
    public Aspect(String tag, int color, Aspect[] components) {
        this(tag, color, components, new ResourceLocation(Thaumcraft.id, "textures/aspects/" + tag.toLowerCase() + ".png"), 1);
    }

    /**
     * Shortcut constructor I use for the default aspects - you shouldn't be using this.
     */
    public Aspect(String tag, int color, Aspect[] components, int blend) {
        this(tag, color, components, new ResourceLocation(Thaumcraft.id, "textures/aspects/" + tag.toLowerCase() + ".png"), blend);
    }

    /**
     * Shortcut constructor I use for the primal aspects -
     * you shouldn't use this as making your own primal aspects will break all the things.
     */
    public Aspect(String tag, int color, String chatcolor, int blend) {
        this(tag, color, (Aspect[]) null, blend);
        this.chatcolor = chatcolor;
    }

    public String getName() {
        return WordUtils.capitalizeFully(tag);
    }

    public ITextComponent getLocalizedDescription() {
        return new TranslationTextComponent("tc.aspect." + tag);
    }

    public boolean isPrimal() {
        return components == null;
    }

}