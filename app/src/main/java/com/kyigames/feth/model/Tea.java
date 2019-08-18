package com.kyigames.feth.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.annotations.NotNull;

import java.util.List;

@IgnoreExtraProperties
public class Tea implements IDbEntity {

    @NotNull
    public String Name;
    @NotNull
    public List<String> PreferredTeas;

    @Override
    public String getKey() {
        return Name;
    }
}
