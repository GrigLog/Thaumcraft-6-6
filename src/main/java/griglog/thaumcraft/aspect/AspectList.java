package griglog.thaumcraft.aspect;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.io.Serializable;
import java.util.*;

public class AspectList implements Serializable {
    public static AspectList EMPTY = new AspectList();
    public ArrayList<AspectEntry> aspects = new ArrayList<>();

    public AspectList(CompoundNBT tag) {
        this.read(tag);
    }

    public AspectList(){}


    public AspectList copy() {
        AspectList out = new AspectList();
        for (Aspect a : getAspects())
            out.add(a, getAmount(a));
        return out;
    }

    public AspectList multiply(float count){
        for (AspectEntry ae : aspects){
            ae.amount = (int) Math.ceil(ae.amount * count);
        }
        return this;
    }

    /**
     * @return the amount of different aspects in this collection
     */
    public int size() {
        return aspects.size();
    }

    public boolean empty(){
        return aspects.size() > 0;
    }

    /**
     * @return the amount of total vis in this collection
     */
    public int visSize() {
        int q = 0;
        for (AspectEntry as : aspects) {
            q += as.amount;
        }
        return q;
    }

    /**
     * @return an array of all the aspects in this collection
     */
    public Aspect[] getAspects() {
        return aspects.stream().map(a -> a.type).toArray(Aspect[]::new);
    }

    /**
     * @return an array of all the aspects in this collection sorted by name
     */
    public Aspect[] getAspectsSortedByName() {
        return aspects.stream().map(as -> as.type).sorted(Comparator.comparing(a -> a.tag)).toArray(Aspect[]::new);
    }

    /**
     * @return an array of all the aspects in this collection sorted by amount
     */
    public Aspect[] getAspectsSortedByAmount() {
        return aspects.stream().sorted(Comparator.comparingInt(a -> -a.amount)).map(a -> a.type).toArray(Aspect[]::new);
    }

    /**
     * @param key
     * @return the amount associated with the given aspect in this collection
     */
    public int getAmount(Aspect key) {
        for (AspectEntry a : aspects){
            if (a.type == key)
                return a.amount;
        }
        return 0;
    }

    /**
     * Reduces the amount of an aspect in this collection by the given amount.
     * @param key
     * @param amount
     * @return if successful
     */
    public boolean reduce(Aspect key, int amount) {
        for (AspectEntry a : aspects){
            if (a.type == key) {
                if (a.amount == amount){
                    aspects.remove(a);
                    return true;
                } else if (a.amount >= amount){
                    a.amount -= amount;
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Simply removes the aspect from the list
     * @param key
     * @return
     */
    public AspectList remove(Aspect key) {
        for (Iterator<AspectEntry> it = aspects.iterator(); it.hasNext();){
            AspectEntry ae = it.next();
            if (ae.type == key){
                it.remove();
                return this;
            }
        }
        return this;
    }

    /**
     * Adds this aspect and amount to the collection.
     * If the aspect exists then its value will be increased by the given amount.
     * @param aspect
     * @param amount
     * @return
     */
    public AspectList add(Aspect aspect, int amount) {
        for (AspectEntry ae : aspects){
            if (ae.type == aspect){
                ae.amount += amount;
                return this;
            }
        }
        aspects.add(new AspectEntry(aspect, amount));
        return this;
    }

    public boolean has(AspectList list){
        for (AspectEntry ae : list.aspects){
            if (getAmount(ae.type) < ae.amount)
                return false;
        }
        return true;
    }

    /**
     * Adds this aspect and amount to the collection.
     * If the aspect exists then only the highest of the old or new amount will be used.
     * @param aspect
     * @param amount
     * @return
     */
    public AspectList merge(Aspect aspect, int amount) {
        for (AspectEntry ae : aspects){
            if (ae.type == aspect){
                if (amount > ae.amount)
                    ae.amount = amount;
                return this;
            }
        }
        aspects.add(new AspectEntry(aspect, amount));
        return this;
    }

    public AspectList add(AspectEntry ae){
        return add(ae.type, ae.amount);
    }

    public AspectList add(AspectList in) {
        for (Aspect a : in.getAspects())
            add(a, in.getAmount(a));
        return this;
    }

    public AspectList reduce(AspectList in) {
        for (Aspect a : in.getAspects())
            reduce(a, in.getAmount(a));
        return this;
    }

    public AspectList merge(AspectList in) {
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
            aspects.add(new AspectEntry(tlist.getCompound(j)));
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
        for (AspectEntry ae : aspects){
            tlist.add(ae.getTag());
        }
    }

    @Override
    public String toString() {
        if (size() == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (AspectEntry ae : aspects){
            sb.append(ae.amount).append(' ').append(ae.type.tag).append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
