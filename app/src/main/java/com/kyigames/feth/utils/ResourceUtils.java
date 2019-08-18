package com.kyigames.feth.utils;

import android.content.Context;
import android.content.res.Resources;

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
}
