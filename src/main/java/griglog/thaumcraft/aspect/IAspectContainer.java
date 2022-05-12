package griglog.thaumcraft.aspect;

/**
 * @author azanor
 *
 * Used by blocks like the crucible and alembic to hold their aspects.
 * Tiles extending this interface will have their aspects show up when viewed by goggles of revealing
 */
public interface IAspectContainer {
    AspectList readList();
    /**
     * This method is used to add a certain amount of an aspect to the tile entity.
     * @param tag
     * @param amount
     * @return the amount of aspect added.
     */
    int tryAdd(Aspect tag, int amount);

    /**
     * Removes a certain amount of a specific aspect from the tile entity
     * @param tag
     * @param amount
     * @return true if that amount of aspect was available and was removed
     */
    boolean tryTake(Aspect tag, int amount);
    

    /**
     * Checks if the tile entity contains the listed amount (or more) of the aspect
     * @param tag
     * @param amount
     * @return
     */
    boolean hasAmount(Aspect tag, int amount);

    /**
     * Returns how much of the aspect this tile entity contains
     * @param tag
     * @return the amount of that aspect found
     */
    int getAmount(Aspect tag);


}
