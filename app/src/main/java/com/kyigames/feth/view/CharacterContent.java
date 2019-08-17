package com.kyigames.feth.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.kyigames.feth.R;
import com.kyigames.feth.model.Character;
import com.kyigames.feth.model.Database;
import com.kyigames.feth.model.Present;
import com.kyigames.feth.model.Tea;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Predicate;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

public class CharacterContent extends AbstractFlexibleItem<CharacterContent.ViewHolder> {
    private static final String[] SpellLevels =
            {
                    "D", "D+", "C", "C+", "B", "B+", "A", "A+"
            };

    private Character m_character;
    private Present m_present;
    private Tea m_tea;

    public CharacterContent(Character character) {
        m_character = character;

        m_present = Database.getTable(Present.class)
                .stream()
                .filter(new Predicate<Present>() {
                    @Override
                    public boolean test(Present present) {
                        return present.Name.equals(m_character.Name);
                    }
                })
                .findAny()
                .orElse(null);

        m_tea = Database.getTable(Tea.class)
                .stream()
                .filter(new Predicate<Tea>() {
                    @Override
                    public boolean test(Tea tea) {
                        return tea.Name.equals(m_character.Name);
                    }
                })
                .findAny()
                .orElse(null);
    }

    class ViewHolder extends FlexibleViewHolder {
        TextView CrestName;
        TextView InitialClass;

        // Preferences
        TextView PreferredGifts;
        TextView NonPreferredGifts;
        TextView PreferredTeas;

        // Skill
        TableRow SkillLevel;
        TableRow SkillProficiency;
        TextView BuddingTalent;

        // Growth
        TableRow GrowthRate;

        // Spell
        List<TableRow> SpellTableRows = new ArrayList<>();

        ViewHolder(View view, FlexibleAdapter adapter, boolean stickyHeader) {
            super(view, adapter, stickyHeader);
            CrestName = view.findViewById(R.id.character_crest);
            InitialClass = view.findViewById(R.id.character_initial_class);


            // Preferences
            PreferredGifts = view.findViewById(R.id.preferred_gift);
            NonPreferredGifts = view.findViewById(R.id.non_preferred_gift);
            PreferredTeas = view.findViewById(R.id.preferred_tea);

            // Skill
            SkillLevel = view.findViewById(R.id.skill_table_level);
            SkillProficiency = view.findViewById(R.id.skill_table_proficiency);
            BuddingTalent = view.findViewById(R.id.budding_talent);

            // Growth
            GrowthRate = view.findViewById(R.id.growth_table_value);

            // Spell
            TableLayout spellTableLayout = view.findViewById(R.id.spell_table);
            for (int i = 0; i < SpellLevels.length; ++i) {
                SpellTableRows.add((TableRow) spellTableLayout.getChildAt(i + 1));
            }
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.character_character_content;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CharacterContent) {
            CharacterContent characterContent = (CharacterContent) o;
            return m_character.Name.equals(characterContent.m_character.Name);
        }
        return false;
    }


    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ViewHolder(view, adapter, false);
    }

    private int getSkillValueRes(int skillValue) {
        if (skillValue == 0) {
            return R.drawable.ic_none;
        } else if (skillValue == 1) {
            return R.drawable.ic_up;
        } else if (skillValue == 2) {
            return R.drawable.ic_down;
        } else if (skillValue == 4) {
            return R.drawable.ic_yellow_star;
        } else if (skillValue == 6) {
            return R.drawable.ic_red_star;
        } else {
            return R.drawable.ic_missing_content;
        }
    }


    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position, List<Object> payloads) {
        bindCharacterInfo(holder);
        bindCharacterSkill(holder);
        bindGrowthRate(holder);
        bindSpellAcquisition(holder);
    }

    private void bindCharacterInfo(ViewHolder holder) {
        TextView crest = holder.CrestName;

        if (m_character.Crest == null) {
            crest.setText("없음");
        } else if (m_character.Crest.size() == 2) {
            String text = String.format("%s의 문장, %s의 문장", m_character.Crest.get(0), m_character.Crest.get(1));
            crest.setText(text);
        } else {
            crest.setText(m_character.Crest.get(0) + "의 문장");
        }

        holder.InitialClass.setText(m_character.InitialClass);

        bindPreference(holder);
    }

    private void bindCharacterSkill(ViewHolder holder) {
        TableRow skillLevel = holder.SkillLevel;
        TableRow skillProficiency = holder.SkillProficiency;

        int childCount = skillProficiency.getVirtualChildCount();

        for (int i = 0; i < childCount; ++i) {
            TextView levelView = (TextView) skillLevel.getVirtualChildAt(i);
            ImageView proficiencyView = (ImageView) skillProficiency.getVirtualChildAt(i);

            levelView.setText(m_character.SkillLevel.get(i));
            proficiencyView.setImageResource(getSkillValueRes(m_character.SkillProficiency.get(i)));
        }

        if (m_character.BuddingTalent == null) {
            holder.BuddingTalent.setText("없음");
        } else {
            holder.BuddingTalent.setText(m_character.BuddingTalent);
        }
    }

    private void bindGrowthRate(ViewHolder holder) {
        TableRow growthRate = holder.GrowthRate;
        int count = growthRate.getVirtualChildCount();

        for (int i = 0; i < count; i++) {
            TextView valueText = (TextView) growthRate.getVirtualChildAt(i);

            valueText.setText(m_character.StatGrowth.get(i) + "%");
        }
    }

    private void bindPreference(ViewHolder holder) {
        if (m_present == null || m_present.PreferredGifts == null) {
            holder.PreferredGifts.setText("없음");
        } else {
            StringJoiner listString = new StringJoiner(", ");

            for (String gift : m_present.PreferredGifts) {
                listString.add(gift);
            }

            holder.PreferredGifts.setText(listString.toString());
        }

        if (m_present == null || m_present.NonpreferredGifts == null) {
            holder.NonPreferredGifts.setText("없음");
        } else {
            StringJoiner listString = new StringJoiner(", ");

            for (String gift : m_present.NonpreferredGifts) {
                listString.add(gift);
            }

            holder.NonPreferredGifts.setText(listString.toString());
        }

        if (m_tea == null || m_tea.PreferredTeas == null) {
            holder.PreferredTeas.setText("없음");
        } else {
            StringJoiner listString = new StringJoiner(", ");
            for (String tea : m_tea.PreferredTeas) {
                listString.add(tea);
            }
            holder.PreferredTeas.setText(listString.toString());
        }
    }

    private void bindSpellAcquisition(ViewHolder holder) {
        for (int i = 0; i < holder.SpellTableRows.size(); i++) {
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