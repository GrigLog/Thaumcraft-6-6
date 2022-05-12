package griglog.thaumcraft.aspect;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedHashMap;

public class AspectListOld implements Serializable {

    // aspects associated with this object
    public LinkedHashMap<Aspect, Integer> aspects = new LinkedHashMap<Aspect, Integer>();

    public AspectListOld(CompoundNBT tag) {
        this.read(tag);
    }

    public AspectListOld(){}


    public AspectListOld copy() {
        AspectListOld out = new AspectListOld();
        for (Aspect a : getAspects())
            out.add(a, getAmount(a));
        return out;
    }

    /**
     * @return the amount of different aspects in this collection
     */
    public int size() {
        return aspects.size();
    }

    /**
     * @return the amount of total vis in this collection
     */
    public int visSize() {
        int q = 0;
        for (Aspect as : aspects.keySet()) {
            q += getAmount(as);
        }
        return q;
    }

    /**
     * @return an array of all the aspects in this collection
     */
    public Aspect[] getAspects() {
        return aspects.keySet().toArray(new Aspect[] {});
    }

    /**
     * @return an array of all the aspects in this collection sorted by name
     */
    public Aspect[] getAspectsSortedByName() {
        return aspects.keySet().stream().sorted(Comparator.comparing(a -> a.tag)).toArray(Aspect[]::new);
    }

    /**
     * @return an array of all the aspects in this collection sorted by amount
     */
    public Aspect[] getAspectsSortedByAmount() {
        return aspects.keySet().stream().sorted(Comparator.comparingInt(this::getAmount)).toArray(Aspect[]::new);
    }

    /**
     * @param key
     * @return the amount associated with the given aspect in this collection
     */
    public int getAmount(Aspect key) {
        return aspects.get(key) == null ? 0 : aspects.get(key);
    }

    /**
     * Reduces the amount of an aspect in this collection by the given amount.
     * @param key
     * @param amount
     * @return
     */
    public boolean reduce(Aspect key, int amount) {
        int c = getAmount(key);
        if (c >= amount) {
            int updated = c - amount;
            if (updated <= 0)
                aspects.remove(key);
            else
                aspects.put(key, updated);
            return true;
        }
        return false;
    }

    /**
     * Simply removes the aspect from the list
     * @param key
     * @param amount
     * @return
     */
    public AspectListOld remove(Aspect key) {
        aspects.remove(key);
        return this;
    }

    /**
     * Adds this aspect and amount to the collection.
     * If the aspect exists then its value will be increased by the given amount.
     * @param aspect
     * @param amount
     * @return
     */
    public AspectListOld add(Aspect aspect, int amount) {
        if (aspects.containsKey(aspect)) {
            int oldamount = aspects.get(aspect);
            amount += oldamount;
        }
        aspects.put(aspect, amount);
        return this;
    }

    /**
     * Adds this aspect and amount to the collection.
     * If the aspect exists then only the highest of the old or new amount will be used.
     * @param aspect
     * @param amount
     * @return
     */
    public AspectListOld merge(Aspect aspect, int amount) {
        if (aspects.containsKey(aspect)) {
            int oldamount = aspects.get(aspect);
            if (amount < oldamount)
                amount = oldamount;
        }
        aspects.put(aspect, amount);
        return this;
    }

    public AspectListOld add(AspectListOld in) {
        for (Aspect a : in.getAspects())
            add(a, in.getAmount(a));
        return this;
    }

    public AspectListOld reduce(AspectListOld in) {
        for (Aspect a : in.getAspects())
            reduce(a, in.getAmount(a));
        return this;
    }

    public AspectListOld merge(AspectListOld in) {
        for (Aspect a : in.getAspects())
            merge(a, in.getAmount(a));
        return this;
    }

    /**
     * Reads the list of aspects from nbt
     * @param tag
     * @return
     */
    public void read(CompoundNBT tag) {
        read(tag, "Aspects");
    }

    public void read(CompoundNBT tag, String label) {
        aspects.clear();
        ListNBT tlist = tag.getList(label, (byte) 10);
        for (int j = 0; j < tlist.size(); j++) {
            CompoundNBT rs = tlist.getCompound(j);

            if (rs.contains("key")) {
                add(Aspects.get(rs.getString("key")), rs.getInt("amount"));
            }
        }
    }

    /**
     * Writes the list of aspects to nbt
     * @param tag
     * @return
     */
    public void write(CompoundNBT tag) {
        write(tag, "Aspects");
    }

    public void write(CompoundNBT tag, String label) {
        ListNBT tlist = new ListNBT();
        tag.put(label, tlist);
        for (Aspect aspect : getAspects()) if (aspect != null) {
            CompoundNBT f = new CompoundNBT();
            f.putString("key", aspect.tag);
            f.putInt("amount", getAmount(aspect));
            tlist.add(f);
        }
    }
}
