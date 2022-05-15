package griglog.thaumcraft.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.math.MathHelper;

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

    public static int scaleColor(int from, int to, float t){
        t = MathHelper.clamp(t, 0, 1);
        int r1 = ColorHelper.PackedColor.getRed(from);
        int g1 = ColorHelper.PackedColor.getGreen(from);
        int b1 = ColorHelper.PackedColor.getBlue(from);
        int r2 = ColorHelper.PackedColor.getRed(to);
        int g2 = ColorHelper.PackedColor.getGreen(to);
        int b2 = ColorHelper.PackedColor.getBlue(to);
        return ColorHelper.PackedColor.packColor(255, (int)((1-t)*r1 + t*r2), (int)((1-t)*g1 + t*g2), (int)((1-t)*b1 + t*b2));
    }
}
