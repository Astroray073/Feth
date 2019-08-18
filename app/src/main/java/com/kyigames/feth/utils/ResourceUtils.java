package com.kyigames.feth.utils;

import android.content.Context;
import android.content.res.Resources;

import com.kyigames.feth.R;

import java.util.List;
import java.util.StringJoiner;

public class ResourceUtils {
    private static final String m_drawable = "drawable";

    private static Resources m_resources;
    private static String m_packageName;

    public static void initialize(Context context) {
        m_resources = context.getResources();
        m_packageName = context.getPackageName();
    }

    public static int getIconRes(String name) {
        return m_resources.getIdentifier(name, m_drawable, m_packageName);
    }

    public static String getListItemText(List<String> list) {
        return getListItemText(list, ", ");
    }

    public static String getListItemText(List<String> list, String delimiter) {
        if (list == null) {
            return m_resources.getString(R.string.none);
        }

        StringJoiner joiner = new StringJoiner(delimiter);
        for (String item : list) {
            joiner.add(item);
        }
        return joiner.toString();
    }
}
