package com.kyigames.feth.model;

import androidx.annotation.Nullable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Character implements IDbEntity {
    public String Name;
    public String Faction;
    public List<String> Crest;
    // TODO: Add to view
    public String UniqueAbility;
    public String InitialClass;
    @Nullable
    public List<String> Scout;
    public List<String> SkillLevel;
    public List<Integer> SkillProficiency;
    public List<Integer> StatGrowth;
    public String BuddingTalent;
    public List<String> ReasonSpells;
    public List<String> FaithSpells;
    public List<String> SwordArts;
    public List<String> LanceArts;
    public List<String> AxeArts;
    public List<String> BowArts;
    public List<String> FistArts;

    @Override
    public String getKey() {
        return Name;
    }
}
