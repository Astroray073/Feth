package com.kyigames.feth.model;

import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;

import java.util.List;

public class Present implements IDbEntity {
    @NotNull
    public String Name;
    @Nullable
    public List<String> PreferredGifts;
    @Nullable
    public List<String> NonpreferredGifts;

    @Override
    public String getKey() {
        return Name;
    }
}
