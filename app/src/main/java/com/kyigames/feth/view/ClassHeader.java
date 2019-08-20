package com.kyigames.feth.view;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kyigames.feth.R;
import com.kyigames.feth.model.Ability;
import com.kyigames.feth.model.CombatArts;
import com.kyigames.feth.model.Database;
import com.kyigames.feth.model.UnitClass;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.flexibleadapter.items.ISectionable;
import eu.davidea.viewholders.ExpandableViewHolder;

public class ClassHeader extends AbstractExpandableItem<ClassHeader.ViewHolder, ClassContent>
        implements ISectionable<ClassHeader.ViewHolder, ClassCategoryHeader>
{
    private static final String TAG = ClassHeader.class.getSimpleName();

    private ClassCategoryHeader m_header;

    private String m_name;
    private String m_condition;
    @Nullable
    private Ability m_masterAbility;
    @Nullable
    private CombatArts m_masterArts;

    public ClassHeader(ClassCategoryHeader header, UnitClass unitClass)
    {
        m_header = header;
        m_name = unitClass.Name;
        m_condition = unitClass.Condition;
        m_masterAbility = Database.findEntityByKey(Ability.class, unitClass.MasterAbility);
        m_masterArts = Database.findEntityByKey(CombatArts.class, unitClass.MasterArts);
    }

    @Override
    public ClassCategoryHeader getHeader()
    {
        return m_header;
    }

    @Override
    public void setHeader(ClassCategoryHeader header)
    {
        m_header = header;
    }

    class ViewHolder extends ExpandableViewHolder
    {
        View Container;
        TextView Name;
        TextView Condition;
        ViewGroup MasterSkillContainer;
        AbilityIconHeader[] MasterSkills = new AbilityIconHeader[2];

        ViewHolder(View view, FlexibleAdapter adapter, boolean stickyHeader)
        {
            super(view, adapter, stickyHeader);
            Container = view.findViewById(R.id.class_header_container);
            Name = view.findViewById(R.id.class_header_name);
            Condition = view.findViewById(R.id.class_header_condition);

            MasterSkillContainer = view.findViewById(R.id.class_header_master_ability_container);

            MasterSkills[0] = (AbilityIconHeader) MasterSkillContainer.getChildAt(0);
            MasterSkills[1] = (AbilityIconHeader) MasterSkillContainer.getChildAt(1);

            Container.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Log.d(TAG, "onClick: toggle expansion");
                    toggleExpansion();
                }
            });
        }
    }

    @Override
    public int getExpansionLevel()
    {
        return 1;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof ClassHeader)
        {
            ClassHeader characterHeader = (ClassHeader) o;
            return m_name.equals(characterHeader.m_name);
        }
        return false;
    }

    @Override
    public int getLayoutRes()
    {
        return R.layout.class_class_header;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter)
    {
        return new ViewHolder(view, adapter, false);
    }

    @Override
    public void bindViewHolder(final FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position, List<Object> payloads)
    {

        holder.Name.setText(m_name);
        holder.Condition.setText(m_condition);

        if (m_masterAbility == null)
        {
            holder.MasterSkills[0].setVisibility(View.GONE);
        } else
        {
            holder.MasterSkills[0].setVisibility(View.VISIBLE);
            holder.MasterSkills[0].setIcon(m_masterAbility.getIcon());
            holder.MasterSkills[0].setName(m_masterAbility.Name);
        }

        if (m_masterArts == null)
        {
            holder.MasterSkills[1].setVisibility(View.GONE);
        } else
        {
            holder.MasterSkills[1].setVisibility(View.VISIBLE);
            holder.MasterSkills[1].setIcon(m_masterArts.getIcon());
            holder.MasterSkills[1].setName(m_masterArts.Name);
        }

        holder.MasterSkillContainer.invalidate();
    }

}
