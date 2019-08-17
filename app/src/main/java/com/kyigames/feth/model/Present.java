package com.kyigames.feth.model;

import java.util.List;

public class Present implements IDbEntity {
    public String Name;
    public List<String> PreferredGifts;
    public List<String> NonpreferredGifts;

    @Override
    public String getKey() {
        return Name;
    }
}
