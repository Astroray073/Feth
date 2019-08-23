package com.kyigames.feth.model;

import androidx.annotation.DrawableRes;

public interface ISkillInfo
{
    String getName();

    @DrawableRes
    int getIcon();

    String getDescription();
}
