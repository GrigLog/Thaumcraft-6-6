package griglog.thaumcraft.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static CompoundNBT safeTag(ItemStack is){
        if (is.hasTag())
            return is.getTag();
        CompoundNBT tag = new CompoundNBT();
        is.setTag(tag);
        return tag;
    }

    public static <T> List<T> getFields(Class<?> cls, Class<?> fieldClass, @Nullable Object instance){
        List<T> res = new ArrayList<>();
        for (Field f : cls.getDeclaredFields()){
            try {
                Object o = f.get(instance);
                if (fieldClass.isInstance(o))
                    res.add((T) o);
            } catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        return res;
    }
}
