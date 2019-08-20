package com.kyigames.feth.model;

import androidx.annotation.Nullable;

import java.util.List;

public class UnitClass implements IDbEntity
{

    public String Name;

    public String Category;

    public String Condition;
    @Nullable
    public List<String> Abilities;

    public List<Integer> GrowthRate;
    // TODO: implements view for skill bonus.

    public List<Integer> SkillBonus;
    @Nullable
    public String MasterAbility;
    @Nullable
    public String MasterArts;

    @Override
    public String getKey()
    {
        return Name;
    }
}