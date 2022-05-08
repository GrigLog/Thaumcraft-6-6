package griglog.thaumcraft.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AuraHelper {

    /**
     * Consume vis from the aura at the given location
     * @param world
     * @param pos
     * @param amount
     * @param simulate
     * @return how much was actually drained
     */
    public static float drainVis(World world, BlockPos pos, float amount, boolean simulate) {
        return 0;
    }

    /**
     * Consume flux from the aura at the given location
     * Added for completeness, but should really not be used. Add instability instead.
     * @param world
     * @param pos
     * @param amount
     * @param simulate
     * @return how much was actually drained
     */
    public static float drainFlux(World world, BlockPos pos, float amount, boolean simulate) {
        return 0;
    }

    /**
     * Adds vis to the aura at the given location.
     *
     * @param world
     * @param pos
     * @param amount
     */
    public static void addVis(World world, BlockPos pos, float amount) {

    }

    /**
     * Get how much vis is in the aura at the given location.
     * @param world
     * @param pos
     * @return
     */
    public static float getVis(World world, BlockPos pos) {
        return 0;
    }

    /**
     * Adds flux to the aura at the specified block position.
     * @param world
     * @param pos
     * @param amount how much stability to remove
     * @param showEffect if set to true, a flux smoke effect and sound will also be displayed. Use in moderation.
     */
    public static void polluteAura(World world, BlockPos pos, float amount, boolean showEffect) {

    }

    /**
     * Get how much flux is in the aura at the given location.
     * @param world
     * @param pos
     * @return
     */
    public static float getFlux(World world, BlockPos pos) {
        return 0;
    }

    /**
     * Gets the general aura baseline at the given location
     * @param world
     * @param pos
     * @return
     */
    public static int getAuraBase(World world, BlockPos pos) {
        return 0;
    }

    /**
     * Gets if the local aura for the given aspect is below 10% and that the player has the node preserver research.
     * If the passed in player is null it will ignore the need for the research to be completed and just assume it is.
     * @param world
     * @param player
     * @param pos
     * @return
     */
    public static boolean shouldPreserveAura(World world, PlayerEntity player, BlockPos pos) {
        return true;
    }
}

