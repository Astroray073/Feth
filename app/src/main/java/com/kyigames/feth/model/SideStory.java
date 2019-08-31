package com.kyigames.feth.model;

import java.util.List;
public class SideStory implements IDbEntity, Comparable<SideStory>
{
    public String Name;
    public List<String> Participants;
    public int Section;
    public int RecLevel;
    public String StartTime;

    public List<String> RewardKnights;
    public List<String> ClearRewards;
    public String Mission;
    public List<String> MissionRewards;
    public List<String> FieldItems;

    @Override
    public String getKey()
    {
        return Name;
    }

    @Override
    public int compareTo(SideStory sideStory)
    {
        return Name.compareTo(sideStory.Name);
    }
}
