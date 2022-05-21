package griglog.thaumcraft.aspect;

import griglog.thaumcraft.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class AspectHelper {
    public static AspectList getList(ItemStack is){
        if (is.hasTag()) {
            return new AspectList(is.getTag());
        }
        return new AspectList();
    }

    public static void writeList(ItemStack is, AspectList list){
        list.write(Utils.safeTag(is));
    }

    public static void deleteList(ItemStack is, String key){
        if (is.hasTag()){
            CompoundNBT tag = is.getTag();
            if (tag.contains(key)){
                tag.remove(key);
            }
        }
    }

    public static void deleteList(ItemStack is){
        deleteList(is, "Aspects");
    }

    public static AspectList keepMostValuable(AspectList temp) {
        return keepMostValuable(temp, 7);
    }

    public static AspectList keepMostValuable(AspectList list, int cap) {
        //AspectList copy = list.copy();
        while (list.size() > cap) {
            Aspect lowest = null;
            float low = Short.MAX_VALUE;
            for (Aspect tag : list.getEntries()) {
                float amount = list.getAmount(tag);
                if (tag.isPrimal()) {
                    amount *= .9f;
                } else {
                    if (!tag.components[0].isPrimal()) {
                        amount *= 1.1f;
                        if (!tag.components[0].components[0].isPrimal()) {
                            amount *= 1.05f;
                        }
                        if (!tag.components[0].components[1].isPrimal()) {
                            amount *= 1.05f;
                        }
                    }
                    if (!tag.components[1].isPrimal()) {
                        amount *= 1.1f;
                        if (!tag.components[1].components[0].isPrimal()) {
                            amount *= 1.05f;
                        }
                        if (!tag.components[1].components[1].isPrimal()) {
                            amount *= 1.05f;
                        }
                    }
                }
                if (amount < low) {
                    low = amount;
                    lowest = tag;
                }
            }
            list.remove(lowest);
        }
        return list;
    }

    public static Aspect getCombinationResult(Aspect aspect1, Aspect aspect2) {
        Collection<Aspect> aspects = Aspects.aspects.values();
        for (Aspect aspect : aspects) {
            if (aspect.components != null && ((aspect.components[0] == aspect1 && aspect.components[1] == aspect2) || (aspect.components[0] == aspect2 && aspect.components[1] == aspect1))) {
                return aspect;
            }
        }
        return null;
    }

    public static Aspect getRandomPrimal(Random rand, Aspect aspect) {
        ArrayList<Aspect> list = new ArrayList<Aspect>();
        if (aspect != null) {
            AspectList temp = new AspectList();
            temp.add(aspect, 1);
            AspectList temp2 = AspectHelper.reduceToPrimals(temp);
            for (Aspect a : temp2.getEntries()) {
                for (int b = 0; b < temp2.getAmount(a); b++) {
                    list.add(a);
                }
            }
        }
        return list.size() > 0 ? list.get(rand.nextInt(list.size())) : null;
    }

    public static AspectList reduceToPrimals(AspectList in) {
        AspectList out = new AspectList();
        for (Aspect aspect : in.getEntries()) {
            if (aspect != null) {
                if (aspect.isPrimal()) {
                    out.add(aspect, in.getAmount(aspect));
                } else {
                    AspectList temp = new AspectList();
                    temp.add(aspect.components[0], in.getAmount(aspect));
                    temp.add(aspect.components[1], in.getAmount(aspect));
                    AspectList temp2 = reduceToPrimals(temp);
                    for (Aspect a : temp2.getEntries()) {
                        out.add(a, temp2.getAmount(a));
                    }
                }
            }
        }
        return out;
    }

    public static AspectList getPrimalAspects(AspectList in) {
        AspectList t = new AspectList();
        t.add(Aspects.AIR, in.getAmount(Aspects.AIR));
        t.add(Aspects.FIRE, in.getAmount(Aspects.FIRE));
        t.add(Aspects.WATER, in.getAmount(Aspects.WATER));
        t.add(Aspects.EARTH, in.getAmount(Aspects.EARTH));
        t.add(Aspects.ORDER, in.getAmount(Aspects.ORDER));
        t.add(Aspects.ENTROPY, in.getAmount(Aspects.ENTROPY));
        return t;
    }

    public static AspectList getAuraAspects(AspectList in) {
        AspectList t = new AspectList();
        t.add(Aspects.AIR, in.getAmount(Aspects.AIR));
        t.add(Aspects.FIRE, in.getAmount(Aspects.FIRE));
        t.add(Aspects.WATER, in.getAmount(Aspects.WATER));
        t.add(Aspects.EARTH, in.getAmount(Aspects.EARTH));
        t.add(Aspects.ORDER, in.getAmount(Aspects.ORDER));
        t.add(Aspects.ENTROPY, in.getAmount(Aspects.ENTROPY));
        t.add(Aspects.FLUX, in.getAmount(Aspects.FLUX));
        return t;
    }
}
