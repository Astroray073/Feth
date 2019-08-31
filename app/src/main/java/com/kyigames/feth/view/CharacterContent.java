package com.kyigames.feth.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.kyigames.feth.R;
import com.kyigames.feth.model.Ability;
import com.kyigames.feth.model.Character;
import com.kyigames.feth.model.CombatArts;
import com.kyigames.feth.model.Crest;
import com.kyigames.feth.model.Database;
import com.kyigames.feth.model.ISkillInfo;
import com.kyigames.feth.model.Present;
import com.kyigames.feth.model.TeaParty;
import com.kyigames.feth.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

public class CharacterContent extends AbstractFlexibleItem<CharacterContent.ViewHolder>
{
    private static final int SpellLevelCount = 8; // "D", "D+", "C", "C+", "B", "B+", "A", "A+"
    private static final int MAX_CREST_COUNT = 2;

    private Character m_character;
    private List<Crest> m_crests = new ArrayList<>();
    private ISkillInfo m_buddingTalent;
    private Present m_present;
    private TeaParty m_teaParty;
    private Ability m_uniqueAbility;

    public CharacterContent(Character character)
    {
        m_character = character;

        m_buddingTalent = Database.findEntityByKey(Ability.class, character.BuddingTalent);
        if (m_buddingTalent == null)
        {
            m_buddingTalent = Database.findEntityByKey(CombatArts.class, character.BuddingTalent);
        }

        m_present = Database.findEntityByKey(Present.class, character.Name);
        m_teaParty = Database.findEntityByKey(TeaParty.class, character.Name);
        m_uniqueAbility = Database.findEntityByKey(Ability.class, character.UniqueAbility);

        if (m_character.Crest != null)
        {
            for (String crestName : m_character.Crest)
            {
                m_crests.add(Database.findEntityByKey(Crest.class, crestName));
            }
        }
    }

    class ViewHolder extends FlexibleViewHolder
    {
        // Character info
        TextView CrestNoneText;
        SkillInfoView[] Crests = new SkillInfoView[MAX_CREST_COUNT];
        TextView InitialClass;
        TextView PreferredGifts;
        TextView NonPreferredGifts;
        TextView PreferredTeas;
        SkillInfoView UniqueAbility;

        // Skill
        TableRow SkillLevel;
        TableRow SkillProficiency;
        ViewGroup BuddingTalentContainer;
        SkillInfoView BuddingTalentContent;

        // Growth
        TableRow GrowthRate;

        // Spell
        List<TableRow> SpellTableRows = new ArrayList<>();

        ViewHolder(View view, FlexibleAdapter adapter, boolean stickyHeader)
        {
            super(view, adapter, stickyHeader);

            // Character info
            CrestNoneText = view.findViewById(R.id.character_crest_none_text);
            ViewGroup crestList = view.findViewById(R.id.character_crest_list);
            for (int i = 0; i < MAX_CREST_COUNT; ++i)
            {
                Crests[i] = (SkillInfoView) crestList.getChildAt(i);
            }

            InitialClass = view.findViewById(R.id.character_initial_class);
            PreferredGifts = view.findViewById(R.id.preferred_gift);
            NonPreferredGifts = view.findViewById(R.id.non_preferred_gift);
            PreferredTeas = view.findViewById(R.id.preferred_tea);
            UniqueAbility = view.findViewById(R.id.character_info_unique_ability);

            // Skill
            SkillLevel = view.findViewById(R.id.skill_table_level);
            SkillProficiency = view.findViewById(R.id.skill_table_proficiency);
            BuddingTalentContainer = view.findViewById(R.id.character_budding_talent_container);
            BuddingTalentContent = view.findViewById(R.id.character_budding_talent_content);

            // Growth
            GrowthRate = view.findViewById(R.id.growth_table_value);

            // Spell
            TableLayout spellTableLayout = view.findViewById(R.id.spell_table);
            for (int i = 0; i < SpellLevelCount; ++i)
            {
                SpellTableRows.add((TableRow) spellTableLayout.getChildAt(i + 1));
            }
        }
    }

    @Override
    public int getLayoutRes()
    {
        return R.layout.character_character_content;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof CharacterContent)
        {
            CharacterContent characterContent = (CharacterContent) o;
            return m_character.Name.equals(characterContent.m_character.Name);
        }
        return false;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter)
    {
        return new ViewHolder(view, adapter, false);
    }

    private int getSkillValueRes(int skillValue)
    {
        if (skillValue == 0)
        {
            return R.drawable.ic_none;
        } else if (skillValue == 1)
        {
            return R.drawable.ic_up;
        } else if (skillValue == 2)
        {
            return R.drawable.ic_down;
        } else if (skillValue == 4)
        {
            return R.drawable.ic_yellow_star;
        } else if (skillValue == 6)
        {
            return R.drawable.ic_red_star;
        } else
        {
            return R.drawable.ic_missing_content;
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position, List<Object> payloads)
    {
        bindCharacterInfo(holder);
        bindCharacterSkill(holder);
        bindGrowthRate(holder);
        bindSpellAcquisition(holder);
    }

    private void bindCharacterInfo(ViewHolder holder)
    {
        if (m_crests.size() == 0)
        {
            holder.CrestNoneText.setVisibility(View.VISIBLE);
            for (int i = 0; i < MAX_CREST_COUNT; ++i)
            {
                holder.Crests[i].setVisibility(View.GONE);
            }
        } else
        {
            holder.CrestNoneText.setVisibility(View.GONE);

            for (int i = 0; i < MAX_CREST_COUNT; ++i)
            {
                if (i < m_crests.size())
                {
                    holder.Crests[i].setVisibility(View.VISIBLE);

                    Crest crest = m_crests.get(i);
                    holder.Crests[i].setName(crest.Name);
                    holder.Crests[i].setIcon(crest.getIcon());
                    holder.Crests[i].setDescription(crest.Effect);
                } else
                {
                    holder.Crests[i].setVisibility(View.GONE);
                }
            }
        }

        holder.InitialClass.setText(m_character.InitialClass);

        bindPreference(holder);

        holder.UniqueAbility.setIcon(m_uniqueAbility.getIcon());
        holder.UniqueAbility.setName(m_uniqueAbility.Name);
        holder.UniqueAbility.setDescription(m_uniqueAbility.Description);
    }

    private void bindCharacterSkill(ViewHolder holder)
    {
        TableRow skillLevel = holder.SkillLevel;
        TableRow skillProficiency = holder.SkillProficiency;

        int childCount = skillProficiency.getVirtualChildCount();

        for (int i = 0; i < childCount; ++i)
        {
            TextView levelView = (TextView) skillLevel.getVirtualChildAt(i);
            ImageView proficiencyView = (ImageView) skillProficiency.getVirtualChildAt(i);

            levelView.setText(m_character.SkillLevel.get(i));
            proficiencyView.setImageResource(getSkillValueRes(m_character.SkillProficiency.get(i)));
        }

        if (m_buddingTalent == null)
        {
            holder.BuddingTalentContainer.setVisibility(View.GONE);
        } else
        {
            holder.BuddingTalentContainer.setVisibility(View.VISIBLE);
            holder.BuddingTalentContent.setInfo(m_buddingTalent);
        }
    }

    private void bindGrowthRate(ViewHolder holder)
    {
        TableRow growthRate = holder.GrowthRate;
        int count = growthRate.getVirtualChildCount();

        for (int i = 0; i < count; i++)
        {
            TextView valueText = (TextView) growthRate.getVirtualChildAt(i);

            valueText.setText(m_character.StatGrowth.get(i) + "%");
        }
    }

    private void bindPreference(ViewHolder holder)
    {
        if (m_present == null || m_present.PreferredGifts == null)
        {
            holder.PreferredGifts.setText("없음");
        } else
        {
            String text = ResourceUtils.getListItemText(m_present.PreferredGifts);
            holder.PreferredGifts.setText(text);
        }
        holder.PreferredGifts.invalidate();

        if (m_present == null || m_present.NonpreferredGifts == null)
        {
            holder.NonPreferredGifts.setText("없음");
        } else
        {
            String text = ResourceUtils.getListItemText(m_present.NonpreferredGifts);
            holder.NonPreferredGifts.setText(text);
        }
        holder.NonPreferredGifts.invalidate();

        if (m_teaParty == null || m_teaParty.PreferredTeas == null)
        {
            holder.PreferredTeas.setText("없음");
        } else
        {
            String text = ResourceUtils.getListItemText(m_teaParty.PreferredTeas);
            holder.PreferredTeas.setText(text);
        }
        holder.PreferredTeas.invalidate();
    }

    private void bindSpellAcquisition(ViewHolder holder)
    {
        for (int i = 0; i < holder.SpellTableRows.size(); i++)
        {
            TableRow row = holder.SpellTableRows.get(i);
            String reasonSpell = m_character.ReasonSpells.get(i);
            String faithSpell = m_character.FaithSpells.get(i);

            TextView reasonText = (TextView) row.getVirtualChildAt(1);
            TextView faithText = (TextView) row.getVirtualChildAt(2);

            reasonText.setText(reasonSpell);
            faithText.setText(faithSpell);
        }
    }

}