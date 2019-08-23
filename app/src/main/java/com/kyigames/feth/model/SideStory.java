package com.kyigames.feth.model;

import java.util.List;
public class SideStory implements IDbEntity, Comparable<SideStory>
{
    public String Name;
    public List<String> Participants;
    public int Section;
    public String StartTime;
    public List<String> ExpireTime;
    public String RecLevel;
    public List<String> RewardKnights;
    public List<String> ClearRewards;
    public List<String> MissionRewards;
    public List<String> DropItems;
    public List<String> ChestItems;

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
