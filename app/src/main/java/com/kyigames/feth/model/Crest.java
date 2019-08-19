package com.kyigames.feth.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;

import java.util.List;

@IgnoreExtraProperties
public class Crest implements IDbEntity {
    @NotNull
    public String Name;
    @Nullable
    public List<String> Bearer;
    @Nullable
    public String Memento;
    @NotNull
    public String Description;
    @NotNull
    public String Effect;

    @Override
    public String getKey() {
        return Name;
    }
}
