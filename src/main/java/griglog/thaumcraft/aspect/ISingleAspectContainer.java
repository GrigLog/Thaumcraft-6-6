package griglog.thaumcraft.aspect;

public interface ISingleAspectContainer extends IAspectContainer{
    Aspect getAspect();
    void changeAspect(Aspect aspect);
    int getAmount();
    int getCapacity();
    void take(int amount);
    void add(int amount);


    @Override
    default AspectList readList(){
        Aspect as = getAspect();
        if (as == Aspects.EMPTY)
            return new AspectList();
        return new AspectList().add(as, getAmount());
    }

    @Override
    default int getAmount(Aspect a){
        return a == getAspect() ? getAmount() : 0;
    }

    @Override
    default boolean hasAmount(Aspect a, int amount){
        return a == getAspect() && getAmount() >= amount;
    }

    @Override
    default boolean tryTake(Aspect tag, int amount){
        if (tag != getAspect() || getAmount() < amount)
            return false;
        take(amount);
        if (getAmount() == 0)
            changeAspect(Aspects.EMPTY);
        return true;
    }

    @Override
    default int tryAdd(Aspect tag, int amount){
        if (getAspect() == Aspects.EMPTY){
            changeAspect(tag);
        }
        if (tag != getAspect())
            return 0;
        int toAdd = Math.min(getCapacity() - getAmount(), amount);
        add(toAdd);
        if (getAmount() == 0)
            changeAspect(Aspects.EMPTY);
        return toAdd;
    }
}
