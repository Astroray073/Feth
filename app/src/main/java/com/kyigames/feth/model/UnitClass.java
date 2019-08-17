package com.kyigames.feth.model;

import androidx.annotation.Nullable;

import java.util.List;

public class UnitClass {
    public String Name;
    public String Category;
    public String Condition;
    @Nullable
    public List<String> Abilities;
    public List<Integer> GrowthRate;
    public List<Integer> SkillBonus;
    public String MasterAbility;
    public String MasterArts;
}