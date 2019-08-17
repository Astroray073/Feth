package com.kyigames.feth.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Tea implements IDbEntity {
    public String Name;
    public List<String> PreferredTeas;

    @Override
    public String getKey() {
        return Name;
    }
}
