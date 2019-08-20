package com.kyigames.feth.model;

import androidx.annotation.Nullable;
public class Spell implements IDbEntity {

    public String Name;

    public String Category;

    public int Power;

    public int Accuracy;

    public int Critical;

    public String Range;

    public int Weight;

    public String Level;

    public int Capacity;
    @Nullable
    public String Description;

    @Override
    public String getKey() {
        return Name;
    }
}
