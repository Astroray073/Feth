package com.kyigames.feth.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.kyigames.feth.R;
import com.kyigames.feth.model.Ability;
import com.kyigames.feth.model.CombatArts;
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
    private static final int MAX_MASTER_SKILL_COUNT = 2;

    private UnitClass m_unitClass;
    private List<Ability> m_abilities = new ArrayList<>();
    private Ability m_masterAbility;
    private CombatArts m_masterArts;

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

        m_masterAbility = Database.findEntityByKey(Ability.class, m_unitClass.MasterAbility);
        m_masterArts = Database.findEntityByKey(CombatArts.class, m_unitClass.MasterArts);
    }

    class ViewHolder extends FlexibleViewHolder
    {
        // Class abilities
        ViewGroup ClassAbilityList;
        TextView ClassAbilityNoneText;
        ClassAbilityListItem[] ClassAbilities = new ClassAbilityListItem[MAX_ABILITY_COUNT];

        // Master skills
        ViewGroup ClassMasterSkillList;
        TextView ClassMasterSkillNoneText;
        ClassAbilityListItem[] ClassMasterSkills = new ClassAbilityListItem[MAX_MASTER_SKILL_COUNT];

        // Growth
        TableRow GrowthRate;

        // Skill bonus
        TableRow SkillBonus;

        ViewHolder(View view, FlexibleAdapter adapter)
        {
            super(view, adapter, false);

            // Class abilities
            ClassAbilityList = view.findViewById(R.id.class_ability_list);
            ClassAbilityNoneText = view.findViewById(R.id.class_ability_none_text);
            for (int i = 0; i < MAX_ABILITY_COUNT; ++i)
            {
                ClassAbilities[i] = (ClassAbilityListItem) ClassAbilityList.getChildAt(i);
            }

            // Master skills
            ClassMasterSkillList = view.findViewById(R.id.class_master_skill_list);
            ClassMasterSkillNoneText = view.findViewById(R.id.class_master_skill_none_text);
            for (int i = 0; i < MAX_MASTER_SKILL_COUNT; ++i)
            {
                ClassMasterSkills[i] = (ClassAbilityListItem) ClassMasterSkillList.getChildAt(i);
            }

            // Growth
            GrowthRate = view.findViewById(R.id.growth_table_value);
            SkillBonus = view.findViewById(R.id.class_skill_bonus_value);
        }
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
        bindClassMasterSkill(holder);
        bindGrowthRate(holder);
        bindSkillBonus(holder);
    }

    private void bindClassAbility(ViewHolder holder)
    {
        if (m_unitClass.Abilities == null || m_unitClass.Abilities.size() == 0)
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

    private void bindClassMasterSkill(ViewHolder holder)
    {
        if (m_masterAbility == null || m_masterArts == null)
        {
            holder.ClassMasterSkillNoneText.setVisibility(View.VISIBLE);
            for (int i = 0; i < MAX_MASTER_SKILL_COUNT; ++i)
            {
                holder.ClassMasterSkills[i].setVisibility(View.GONE);
            }
        } else
        {
            holder.ClassMasterSkillNoneText.setVisibility(View.GONE);

            // Master ability
            if (m_masterAbility == null)
            {
                holder.ClassMasterSkills[0].setVisibility(View.GONE);
            } else
            {
                holder.ClassMasterSkills[0].setVisibility(View.VISIBLE);
                holder.ClassMasterSkills[0].setAbilityName(m_masterAbility.Name);
                holder.ClassMasterSkills[0].setAbilityIcon(m_masterAbility.getIcon());
                holder.ClassMasterSkills[0].setAbilityDescription(m_masterAbility.Description);
            }

            // Master arts
            if (m_masterArts == null)
            {
                holder.ClassMasterSkills[1].setVisibility(View.GONE);
            } else
            {
                holder.ClassMasterSkills[1].setVisibility(View.VISIBLE);
                holder.ClassMasterSkills[1].setAbilityName(m_masterArts.Name);
                holder.ClassMasterSkills[1].setAbilityIcon(m_masterArts.getIcon());
                holder.ClassMasterSkills[1].setAbilityDescription(m_masterArts.Description);
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


}
