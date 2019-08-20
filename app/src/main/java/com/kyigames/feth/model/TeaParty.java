package com.kyigames.feth.model;

import java.util.List;

public class TeaParty implements IDbEntity
{
    public String Name;
    public List<String> PreferredTeas;

    @Override
    public String getKey()
    {
        return Name;
    }
}
