package griglog.thaumcraft.aspect;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.io.Serializable;
import java.util.*;

public class AspectList implements Serializable {
    public static AspectList EMPTY = new AspectList();
    public ArrayList<AspectEntry> entries = new ArrayList<>();

    public AspectList(CompoundNBT tag) {
        this.read(tag);
    }

    public AspectList(){}

    public AspectList(String s){
        String[] parts = s.split(" ");
        for (int i = 0; i < parts.length; i += 2){
            add(Aspects.get(parts[i+1]), Integer.parseInt(parts[i]));
        }
    }


    public AspectList copy() {
        AspectList out = new AspectList();
        for (Aspect a : getEntries())
            out.add(a, getAmount(a));
        return out;
    }

    public AspectList multiply(float count){
        for (AspectEntry ae : entries){
            ae.amount = (int) Math.ceil(ae.amount * count);
        }
        return this;
    }

    /**
     * @return the amount of different aspects in this collection
     */
    public int size() {
        return entries.size();
    }

    public boolean empty(){
        return entries.size() > 0;
    }

    /**
     * @return the amount of total vis in this collection
     */
    public int visSize() {
        int q = 0;
        for (AspectEntry as : entries) {
            q += as.amount;
        }
        return q;
    }

    /**
     * @return an array of all the aspects in this collection
     */
    public Aspect[] getEntries() {
        return entries.stream().map(a -> a.type).toArray(Aspect[]::new);
    }

    /**
     * @return an array of all the aspects in this collection sorted by name
     */
    public Aspect[] getAspectsSortedByName() {
        return entries.stream().map(as -> as.type).sorted(Comparator.comparing(a -> a.tag)).toArray(Aspect[]::new);
    }

    /**
     * @return an array of all the aspects in this collection sorted by amount
     */
    public Aspect[] getAspectsSortedByAmount() {
        return entries.stream().sorted(Comparator.comparingInt(a -> -a.amount)).map(a -> a.type).toArray(Aspect[]::new);
    }

    /**
     * @param key
     * @return the amount associated with the given aspect in this collection
     */
    public int getAmount(Aspect key) {
        for (AspectEntry a : entries){
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
        for (AspectEntry a : entries){
            if (a.type == key) {
                if (a.amount == amount){
                    entries.remove(a);
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
        for (Iterator<AspectEntry> it = entries.iterator(); it.hasNext();){
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
        for (AspectEntry ae : entries){
            if (ae.type == aspect){
                ae.amount += amount;
                return this;
            }
        }
        entries.add(new AspectEntry(aspect, amount));
        return this;
    }

    public boolean has(AspectList list){
        for (AspectEntry ae : list.entries){
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
        for (AspectEntry ae : entries){
            if (ae.type == aspect){
                if (amount > ae.amount)
                    ae.amount = amount;
                return this;
            }
        }
        entries.add(new AspectEntry(aspect, amount));
        return this;
    }

    public AspectList add(AspectEntry ae){
        return add(ae.type, ae.amount);
    }

    public AspectList add(AspectList in) {
        for (Aspect a : in.getEntries())
            add(a, in.getAmount(a));
        return this;
    }

    public AspectList reduce(AspectList in) {
        for (Aspect a : in.getEntries())
            reduce(a, in.getAmount(a));
        return this;
    }

    public AspectList merge(AspectList in) {
        for (Aspect a : in.getEntries())
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
        entries.clear();
        ListNBT tlist = tag.getList(label, (byte) 10);
        for (int j = 0; j < tlist.size(); j++) {
            entries.add(new AspectEntry(tlist.getCompound(j)));
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
        for (AspectEntry ae : entries){
            tlist.add(ae.getTag());
        }
    }

    @Override
    public String toString() {
        if (size() == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (AspectEntry ae : entries){
            sb.append(ae.amount).append(' ').append(ae.type.tag).append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
