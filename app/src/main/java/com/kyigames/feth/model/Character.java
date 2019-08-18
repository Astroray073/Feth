package com.kyigames.feth.model;

import androidx.annotation.Nullable;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.annotations.NotNull;
import com.kyigames.feth.utils.ResourceUtils;

import java.util.List;

@IgnoreExtraProperties
public class Character implements IDbEntity {

    @NotNull
    public String Name;
    @NotNull
    public String PortraitResId;
    @NotNull
    public String Faction;
    @Nullable
    public List<String> Crest;
    @NotNull
    public String UniqueAbility;
    @NotNull
    public String InitialClass;
    @Nullable
    public List<String> Scout;
    @NotNull
    public List<String> SkillLevel;
    @NotNull
    public List<Integer> SkillProficiency;
    @NotNull
    public List<Integer> StatGrowth;
    @NotNull
    public String BuddingTalent;
    @NotNull
    public List<String> ReasonSpells;
    @NotNull
    public List<String> FaithSpells;
    @NotNull
    public List<String> SwordArts;
    @NotNull
    public List<String> LanceArts;
    @NotNull
    public List<String> AxeArts;
    @NotNull
    public List<String> BowArts;
    @NotNull
    public List<String> FistArts;

    @Override
    public String getKey() {
        return Name;
    }

    public int getPortraitIcon() {
        return ResourceUtils.getIconRes(PortraitResId);
    }
}
