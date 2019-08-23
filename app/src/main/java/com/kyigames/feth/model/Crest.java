package com.kyigames.feth.model;

import androidx.annotation.Nullable;

import com.kyigames.feth.utils.ResourceUtils;

import java.util.List;

public class Crest implements IDbEntity, ISkillInfo
{

    public String Name;
    public int IconResId;
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

    @Override
    public String getName()
    {
        return Name;
    }

    public int getIcon()
    {
        return ResourceUtils.getIconRes("ic_crest_" + IconResId);
    }

    @Override
    public String getDescription()
    {
        return Effect;
    }
}
