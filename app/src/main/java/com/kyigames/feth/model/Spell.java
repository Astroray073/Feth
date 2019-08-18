package com.kyigames.feth.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;

@IgnoreExtraProperties
public class Spell implements IDbEntity {
    @NotNull
    public String Name;
    @NotNull
    public String Category;
    @NotNull
    public int Power;
    @NotNull
    public int Accuracy;
    @NotNull
    public int Critical;
    @NotNull
    public String Range;
    @NotNull
    public int Weight;
    @NotNull
    public String Level;
    @NotNull
    public int Capacity;
    @Nullable
    public String Description;

    @Override
    public String getKey() {
        return Name;
    }
}
