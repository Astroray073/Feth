package com.kyigames.feth.model;

public class Loss implements IDbEntity, Comparable<Loss>
{
    public String Name;
    public String Character;

    @Override
    public String getKey()
    {
        return Name;
    }

    @Override
    public int compareTo(Loss loss)
    {
        return Name.compareTo(loss.Name);
    }

}
