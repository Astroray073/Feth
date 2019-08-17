package com.kyigames.feth.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Character {
    public String Name;
    public String Faction;
    public List<String> Crest;
    public String UniqueAbility;
    public String InitialClass;
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
}
