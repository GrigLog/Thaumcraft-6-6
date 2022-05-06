package griglog.thaumcraft.utils;

import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3i;

public class MathUtils {
    public static Vector3i direction(Direction.Axis axis, int scale){
        switch (axis){
            default:
            case X:
                return new Vector3i(scale, 0, 0);
            case Y:
                return new Vector3i(0, scale, 0);
            case Z:
                return new Vector3i(0, 0, scale);
        }
    }
}
