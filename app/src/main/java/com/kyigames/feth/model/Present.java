package com.kyigames.feth.model;

import androidx.annotation.Nullable;

import java.util.List;

public class Present implements IDbEntity
{

    public String Name;
    @Nullable
    public List<String> PreferredGifts;
    @Nullable
    public List<String> NonpreferredGifts;

    @Override
    public String getKey()
    {
        return Name;
    }
}
