package com.kyigames.feth.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.kyigames.feth.R;
import com.kyigames.feth.model.Ability;
import com.kyigames.feth.model.Database;
import com.kyigames.feth.model.UnitClass;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

public class ClassContent extends AbstractFlexibleItem<ClassContent.ViewHolder>
{
    private static final String TAG = ClassContent.class.getSimpleName();
    private static final int MAX_ABILITY_COUNT = 3;

    private UnitClass m_unitClass;
    private List<Ability> m_abilities = new ArrayList<>();

    public ClassContent(UnitClass unitClass)
    {
        m_unitClass = unitClass;

        if (m_unitClass.Abilities != null)
        {

            for (String abilityName : m_unitClass.Abilities)
            {
                m_abilities.add(Database.findEntityByKey(Ability.class, abilityName));
            }
        }
    }

    @Override
    public int getItemViewType()
    {
        return m_unitClass.Abilities == null ? 0 : m_unitClass.Abilities.size();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof ClassContent)
        {
            ClassContent classContent = (ClassContent) o;
            return m_unitClass.Name.equals(classContent.m_unitClass.Name);
        }
        return false;
    }

    @Override
    public int getLayoutRes()
    {
        return R.layout.class_class_content;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter)
    {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position, List<Object> payloads)
    {
        bindClassAbility(holder);
        bindGrowthRate(holder);
        bindSkillBonus(holder);
    }

    private void bindClassAbility(ViewHolder holder)
    {

        if (m_unitClass.Abilities == null)
        {
            holder.ClassAbilityNoneText.setVisibility(View.VISIBLE);
            for (int i = 0; i < MAX_ABILITY_COUNT; ++i)
            {
                holder.ClassAbilities[i].setVisibility(View.GONE);
            }
        } else
        {

            holder.ClassAbilityNoneText.setVisibility(View.GONE);
            for (int i = 0; i < MAX_ABILITY_COUNT; i++)
            {
                if (i < m_unitClass.Abilities.size())
                {
                    String abilityName = m_unitClass.Abilities.get(i);
                    Ability ability = Database.findEntityByKey(Ability.class, abilityName);

                    holder.ClassAbilities[i].setVisibility(View.VISIBLE);

                    if (ability == null)
                    {
                        holder.ClassAbilities[i].setAbilityName(abilityName);
                        holder.ClassAbilities[i].setAbilityIcon(R.drawable.ic_missing_content);
                        holder.ClassAbilities[i].setAbilityDescription("missing");
                    } else
                    {
                        holder.ClassAbilities[i].setAbilityName(ability.Name);
                        holder.ClassAbilities[i].setAbilityIcon(ability.getIcon());
                        holder.ClassAbilities[i].setAbilityDescription(ability.Description);
                    }

                } else
                {
                    holder.ClassAbilities[i].setVisibility(View.GONE);
                }
            }
        }
    }

    private void bindGrowthRate(ViewHolder holder)
    {
        TableRow growthRate = holder.GrowthRate;
        int count = growthRate.getVirtualChildCount();

        for (int i = 0; i < count; i++)
        {
            TextView valueText = (TextView) growthRate.getVirtualChildAt(i);

            valueText.setText(m_unitClass.GrowthRate.get(i) + "%");
        }
    }

    private void bindSkillBonus(ViewHolder holder)
    {
        for (int i = 0; i < m_unitClass.SkillBonus.size(); ++i)
        {
            int bonus = m_unitClass.SkillBonus.get(i);
            TextView valueText = (TextView) holder.SkillBonus.getVirtualChildAt(i);
            String text = bonus > 0 ? "+" + bonus : Integer.toString(bonus);

            valueText.setText(text);
        }
    }

    class ViewHolder extends FlexibleViewHolder
    {
        ViewGroup ClassAbilityContainer;
        TextView ClassAbilityNoneText;
        ClassAbilityListItem[] ClassAbilities = new ClassAbilityListItem[MAX_ABILITY_COUNT];
        // Growth
        TableRow GrowthRate;

        TableRow SkillBonus;

        ViewHolder(View view, FlexibleAdapter adapter)
        {
            super(view, adapter, false);

            ClassAbilityNoneText = view.findViewById(R.id.class_ability_none_text);

            ClassAbilityContainer = view.findViewById(R.id.class_ability_list);
            for (int i = 0; i < MAX_ABILITY_COUNT; ++i)
            {
                ClassAbilities[i] = (ClassAbilityListItem) ClassAbilityContainer.getChildAt(i);
            }

            // Growth
            GrowthRate = view.findViewById(R.id.growth_table_value);
            SkillBonus = view.findViewById(R.id.class_skill_bonus_value);
        }
    }
}
