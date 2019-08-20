package com.kyigames.feth.model;

import androidx.annotation.Nullable;

import java.util.List;

public class Crest implements IDbEntity
{

    public String Name;
    @Nullable
    public List<String> Bearer;
    @Nullable
    public String Memento;
    public String Description;
    public String Effect;

    @Override
    public String getKey()
    {
        return Name;
    }
}
