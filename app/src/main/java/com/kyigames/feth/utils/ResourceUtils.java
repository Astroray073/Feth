package com.kyigames.feth.utils;

import android.content.Context;
import android.content.res.Resources;

import com.kyigames.feth.model.Ability;
import com.kyigames.feth.model.CombatArts;

public class ResourceUtils {
    private static final String m_drawable = "drawable";

    private static Context m_context;
    private static Resources m_resources;
    private static String m_packageName;

    public static void initialize(Context context) {
        m_context = context;
        m_resources = context.getResources();
        m_packageName = context.getPackageName();
    }

    public static int getIconRes(String name) {
        return m_resources.getIdentifier(name, m_drawable, m_packageName);
    }

    public static int getAbilityIconResId(Ability ability) {
        final String prefix = "ic_ability_";
        final String missingIconName = "ic_ability_missing";

        if (ability == null) {
            return getIconRes(missingIconName);
        }

        String iconName = prefix + ability.IconResId;
        return getIconRes(iconName);
    }

    public static int getArtsIconResId(CombatArts combatArts) {
        final String prefix = "ic_arts_";
        final String missingIconName = "ic_arts_missing";

        if (combatArts == null) {
            return getIconRes(missingIconName);
        }

        String iconName = prefix + combatArts.Weapon;
        return getIconRes(iconName);
    }
}
