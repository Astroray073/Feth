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
    public List<String> MajorBearer;
    @Nullable
    public List<String> MinorBearer;
    @NotNull
    public String Description;

    @Override
    public String getKey() {
        return Name;
    }
}
