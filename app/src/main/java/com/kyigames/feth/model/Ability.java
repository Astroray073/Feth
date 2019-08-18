package com.kyigames.feth.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;
import com.kyigames.feth.utils.ResourceUtils;

@IgnoreExtraProperties
public class Ability implements IDbEntity {
    @NotNull
    public String Name;
    @NotNull
    public int IconResId;
    @NotNull
    public String Description;
    @Nullable
    public String Acquisition;

    @Override
    public String getKey() {
        return Name;
    }

    public int getIcon() {
        return ResourceUtils.getIconRes("ic_ability_" + IconResId);
    }
}
