package com.kyigames.feth.model;

public class Gift implements IDbEntity
{
    public String Name;
    public int Grade;
    public String Cost;

    @Override
    public String getKey()
    {
        return Name;
    }
}
