package com.kyigames.feth.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kyigames.feth.R;
import com.kyigames.feth.model.Ability;
import com.kyigames.feth.model.CombatArts;
import com.kyigames.feth.model.Database;
import com.kyigames.feth.model.UnitClass;

import java.util.List;
import java.util.function.Predicate;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.flexibleadapter.items.ISectionable;
import eu.davidea.viewholders.ExpandableViewHolder;

public class ClassHeader extends AbstractExpandableItem<ClassHeader.ViewHolder, ClassContent>
        implements ISectionable<ClassHeader.ViewHolder, ClassCategoryHeader> {
    private static final String TAG = ClassHeader.class.getSimpleName();

    private UnitClass m_unitClass;
    private ClassCategoryHeader m_header;

    private boolean bIsMasterAbilityExists;
    private boolean bIsMasterArtsExists;

    public ClassHeader(ClassCategoryHeader header, UnitClass unitClass) {
        m_header = header;
        m_unitClass = unitClass;

        bIsMasterAbilityExists = m_unitClass.MasterAbility != null;
        bIsMasterArtsExists = m_unitClass.MasterArts != null;
    }

    @Override
    public ClassCategoryHeader getHeader() {
        return m_header;
    }

    @Override
    public void setHeader(ClassCategoryHeader header) {
        m_header = header;
    }

    class ViewHolder extends ExpandableViewHolder {
        View Container;
        TextView Name;
        TextView Condition;
        ViewGroup MasterSkillContainer;
        AbilityIconHeader[] MasterSkills = new AbilityIconHeader[2];

        ViewHolder(View view, FlexibleAdapter adapter, boolean stickyHeader) {
            super(view, adapter, stickyHeader);
            Container = view.findViewById(R.id.class_header_container);
            Name = view.findViewById(R.id.class_header_name);
            Condition = view.findViewById(R.id.class_header_condition);

            MasterSkillContainer = view.findViewById(R.id.class_header_master_ability_container);

            MasterSkills[0] = (AbilityIconHeader) MasterSkillContainer.getChildAt(0);
            MasterSkills[1] = (AbilityIconHeader) MasterSkillContainer.getChildAt(1);

            Container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: toggle expansion");
                    toggleExpansion();
                }
            });
        }
    }

    private int getMasterSkillCount() {
        int count = 0;
        if (bIsMasterAbilityExists) {
            ++count;
        }
        if (bIsMasterArtsExists) {
            ++count;
        }
        return count;
    }

    @Override
    public int getExpansionLevel() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ClassHeader) {
            ClassHeader characterHeader = (ClassHeader) o;
            return m_unitClass.Name.equals(characterHeader.m_unitClass.Name);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.class_class_header;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ViewHolder(view, adapter, false);
    }

    private String getAbilityIconIndex(final String abilityName) {
        final List<Ability> abilities = Database.getTable(Ability.class);

        Ability found = abilities.stream()
                .filter(new Predicate<Ability>() {
                    @Override
                    public boolean test(Ability ability) {
                        return abilityName.equals(ability.Name);
                    }
                })
                .findAny()
                .orElse(null);

        return found == null ? "missing" : Integer.toString(found.IconResId);
    }

    private String getArtsIconIndex(final String combarArtsName) {
        final List<CombatArts> abilities = Database.getTable(CombatArts.class);

        CombatArts found = abilities.stream()
                .filter(new Predicate<CombatArts>() {
                    @Override
                    public boolean test(CombatArts combatArts) {
                        return combarArtsName.equals(combatArts.Name);
                    }
                })
                .findAny()
                .orElse(null);

        return found == null ? "missing" : Integer.toString(found.IconResId);
    }

    @Override
    public void bindViewHolder(final FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position, List<Object> payloads) {

        holder.Name.setText(m_unitClass.Name);
        holder.Condition.setText(m_unitClass.Condition == null ? "없음" : m_unitClass.Condition);

        holder.MasterSkills[0].setVisibility(View.VISIBLE);
        holder.MasterSkills[1].setVisibility(View.VISIBLE);

        if (bIsMasterAbilityExists && bIsMasterArtsExists) {
            bindMasterAbility(holder.MasterSkills[0]);
            bindMasterArts(holder.MasterSkills[1]);
        } else if (bIsMasterAbilityExists) {
            bindMasterAbility(holder.MasterSkills[0]);
            holder.MasterSkills[1].setVisibility(View.GONE);
        } else if (bIsMasterArtsExists) {
            bindMasterArts(holder.MasterSkills[0]);
            holder.MasterSkills[1].setVisibility(View.GONE);
        }

        holder.MasterSkillContainer.invalidate();
    }

    private void bindMasterAbility(AbilityIconHeader header) {
        final Context context = header.getContext();

        String abilityIconName = "ic_ability_" + getAbilityIconIndex(m_unitClass.MasterAbility);
        int iconRes = context.getResources().getIdentifier(abilityIconName, "drawable", context.getPackageName());

        header.setIcon(iconRes);
        header.setName(m_unitClass.MasterAbility);
    }

    private void bindMasterArts(AbilityIconHeader header) {
        final Context context = header.getContext();

        String artsIconName = "ic_arts_" + getArtsIconIndex(m_unitClass.MasterArts);
        int iconRes = context.getResources().getIdentifier(artsIconName, "drawable", context.getPackageName());

        header.setIcon(iconRes);
        header.setName(m_unitClass.MasterArts);
    }
}
