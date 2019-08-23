package com.kyigames.feth.model;

import com.kyigames.feth.utils.ResourceUtils;

public class Ability implements IDbEntity, ISkillInfo
{
    public String Name;
    public int IconResId;
    public String Description;
    public String Acquisition;

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
        return ResourceUtils.getIconRes("ic_ability_" + IconResId);
    }

    @Override
    public String getDescription()
    {
        return Description;
    }
}
