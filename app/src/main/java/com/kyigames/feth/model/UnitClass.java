package com.kyigames.feth.model;

import androidx.annotation.Nullable;

import com.google.firebase.database.annotations.NotNull;

import java.util.List;

public class UnitClass implements IDbEntity {
    @NotNull
    public String Name;
    @NotNull
    public String Category;
    @NotNull
    public String Condition;
    @Nullable
    public List<String> Abilities;
    @NotNull
    public List<Integer> GrowthRate;
    // TODO: implements view for skill bonus.
    @NotNull
    public List<Integer> SkillBonus;
    @Nullable
    public String MasterAbility;
    @Nullable
    public String MasterArts;

    @Override
    public String getKey() {
        return Name;
    }
}