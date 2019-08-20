package com.kyigames.feth.model;

import androidx.annotation.Nullable;

import com.kyigames.feth.utils.ResourceUtils;

import java.util.List;

public class CombatArts implements IDbEntity
{
    public String Name;
    public String Weapon;
    public int Power;
    public int Accuracy;
    public int Ciritical;
    public int Avoidance;
    public String Range;
    @Nullable
    public List<String> Effectiveness;
    public int Cost;
    @Nullable
    public String Description;

    @Override
    public String getKey()
    {
        return Name;
    }

    public int getIcon()
    {
        return ResourceUtils.getIconRes("ic_arts_" + Weapon);
    }
}
