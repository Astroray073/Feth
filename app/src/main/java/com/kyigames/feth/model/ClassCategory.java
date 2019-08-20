package com.kyigames.feth.model;

public class ClassCategory implements IDbEntity
{

    public String Name;

    public int MinLevel;

    @Override
    public String getKey()
    {
        return Name;
    }
}
