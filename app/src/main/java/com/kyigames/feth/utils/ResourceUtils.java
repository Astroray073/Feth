package com.kyigames.feth.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import com.kyigames.feth.R;

import java.util.List;
import java.util.StringJoiner;

public class ResourceUtils
{
    private static final String m_drawable = "drawable";

    private static Resources m_resources;
    private static PackageInfo m_packageInfo;

    public static void initialize(Context context) throws PackageManager.NameNotFoundException
    {
        m_resources = context.getResources();
        m_packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
    }

    public static String getPackageName()
    {
        return m_packageInfo.packageName;
    }

    public static int getVersionCode()
    {
        return m_packageInfo.versionCode;
    }

    public static int getIconRes(String name)
    {
        return m_resources.getIdentifier(name, m_drawable, getPackageName());
    }

    public static String getListItemText(List<String> list)
    {
        return getListItemText(list, ", ");
    }

    public static String getListItemText(List<String> list, String delimiter)
    {
        if (list == null || list.size() == 0)
        {
            return m_resources.getString(R.string.none);
        }

        StringJoiner joiner = new StringJoiner(delimiter);
        for (String item : list)
        {
            joiner.add(item);
        }
        return joiner.toString();
    }
}
