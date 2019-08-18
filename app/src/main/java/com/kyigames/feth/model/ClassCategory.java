package com.kyigames.feth.model;

import com.google.firebase.database.annotations.NotNull;

public class ClassCategory implements IDbEntity {
    @NotNull
    public String Name;
    @NotNull
    public int MinLevel;

    @Override
    public String getKey() {
        return Name;
    }
}
