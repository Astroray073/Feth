package com.kyigames.feth.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;
import com.kyigames.feth.utils.ResourceUtils;

import java.util.List;

@IgnoreExtraProperties
public class CombatArts implements IDbEntity {

    @NotNull
    public String Name;
    @NotNull
    public String Weapon;
    @NotNull
    public int Power;
    @NotNull
    public int Accuracy;
    @NotNull
    public int Ciritical;
    @NotNull
    public int Avoidance;
    @NotNull
    public String Range;
    @Nullable
    public List<String> Effectiveness;
    @NotNull
    public int Cost;
    @Nullable
    public String Description;

    @Override
    public String getKey() {
        return Name;
    }

    public int getIcon() {
        return ResourceUtils.getIconRes("ic_arts_" + Weapon);
    }
}
