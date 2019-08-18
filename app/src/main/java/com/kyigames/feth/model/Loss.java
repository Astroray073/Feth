package com.kyigames.feth.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.annotations.NotNull;

@IgnoreExtraProperties
public class Loss implements IDbEntity, Comparable<Loss> {
    @NotNull
    public String Name;
    @NotNull
    public String Character;

    @Override
    public String getKey() {
        return Name;
    }

    @Override
    public int compareTo(Loss loss) {
        return Name.compareTo(loss.Name);
    }

}
