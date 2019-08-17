package com.kyigames.feth.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Ability implements IDbEntity {
    public String Name;
    public int IconResId;
    public String Description;
    public String Acquisition;

    @Override
    public String getKey() {
        return Name;
    }
}
