package com.kyigames.feth.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.annotations.NotNull;

@IgnoreExtraProperties
public class Gift implements IDbEntity {

    @NotNull
    public String Name;
    @NotNull
    public int Grade;
    @NotNull
    public String Cost;

    @Override
    public String getKey() {
        return Name;
    }
}
