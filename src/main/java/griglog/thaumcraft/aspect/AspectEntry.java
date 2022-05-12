package griglog.thaumcraft.aspect;

import net.minecraft.nbt.CompoundNBT;

public class AspectEntry {
    public Aspect type;
    public int amount;
    public static AspectEntry EMPTY = new AspectEntry(Aspects.AIR, 0);

    public AspectEntry(Aspect type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public AspectEntry(CompoundNBT tag){
        this(Aspects.get(tag.getString("key")), tag.getInt("amount"));
    }

    public void write(CompoundNBT tag){
        tag.putString("key", type.tag);
        tag.putInt("amount", amount);
    }

    public CompoundNBT getTag(){
        CompoundNBT tag = new CompoundNBT();
        write(tag);
        return tag;
    }

    public void read(CompoundNBT tag){
        type = Aspects.get(tag.getString("key"));
        amount = tag.getInt("amount");
    }
}
