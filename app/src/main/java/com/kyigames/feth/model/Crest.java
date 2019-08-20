package com.kyigames.feth.model;

import androidx.annotation.Nullable;

import com.kyigames.feth.R;

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

    public int getIcon()
    {
        return R.drawable.ic_missing_content;
    }
}
