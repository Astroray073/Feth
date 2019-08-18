package com.kyigames.feth.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.annotations.NotNull;
import com.kyigames.feth.R;
import com.kyigames.feth.model.Ability;
import com.kyigames.feth.model.Character;
import com.kyigames.feth.model.Database;
import com.kyigames.feth.model.Present;
import com.kyigames.feth.model.Tea;
import com.kyigames.feth.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

public class CharacterContent extends AbstractFlexibleItem<CharacterContent.ViewHolder> {
    private static final int SpellLevelCount = 8; // "D", "D+", "C", "C+", "B", "B+", "A", "A+"

    private Character m_character;
    private Present m_present;
    private Tea m_tea;
    private Ability m_uniqueAbility;

    public CharacterContent(@NotNull Character character) {
        m_character = character;
        m_present = Database.findEntityByKey(Present.class, m_character.Name);
        m_tea = Database.findEntityByKey(Tea.class, m_character.Name);
        m_uniqueAbility = Database.findEntityByKey(Ability.class, m_character.UniqueAbility);
    }

    class ViewHolder extends FlexibleViewHolder {

        // Character info
        TextView CrestName;
        TextView InitialClass;
        TextView PreferredGifts;
        TextView NonPreferredGifts;
        TextView PreferredTeas;
        ClassAbilityListItem UniqueAbility;

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

            // Character info
            CrestName = view.findViewById(R.id.character_crest);
            InitialClass = view.findViewById(R.id.character_initial_class);
            PreferredGifts = view.findViewById(R.id.preferred_gift);
            NonPreferredGifts = view.findViewById(R.id.non_preferred_gift);
            PreferredTeas = view.findViewById(R.id.preferred_tea);
            UniqueAbility = view.findViewById(R.id.character_info_unique_ability);

            // Skill
            SkillLevel = view.findViewById(R.id.skill_table_level);
            SkillProficiency = view.findViewById(R.id.skill_table_proficiency);
            BuddingTalent = view.findViewById(R.id.budding_talent);

            // Growth
            GrowthRate = view.findViewById(R.id.growth_table_value);

            // Spell
            TableLayout spellTableLayout = view.findViewById(R.id.spell_table);
            for (int i = 0; i < SpellLevelCount; ++i) {
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

    private String getCrestText() {
        if (m_character.Crest == null) {
            return "없음";
        }
        StringJoiner joiner = new StringJoiner(", ");
        for (String crest : m_character.Crest) {
            joiner.add(crest);
        }
        return joiner.toString();
    }

    private void bindCharacterInfo(ViewHolder holder) {
        holder.CrestName.setText(getCrestText());
        holder.InitialClass.setText(m_character.InitialClass);

        bindPreference(holder);

        holder.UniqueAbility.setAbilityIcon(m_uniqueAbility.getIcon());
        holder.UniqueAbility.setAbilityName(m_uniqueAbility.Name);
        holder.UniqueAbility.setAbilityDescription(m_uniqueAbility.Description);
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
            String text = ResourceUtils.getListItemText(m_present.PreferredGifts);
            holder.PreferredGifts.setText(text);
        }
        holder.PreferredGifts.invalidate();

        if (m_present == null || m_present.NonpreferredGifts == null) {
            holder.NonPreferredGifts.setText("없음");
        } else {
            String text = ResourceUtils.getListItemText(m_present.NonpreferredGifts);
            holder.NonPreferredGifts.setText(text);
        }
        holder.NonPreferredGifts.invalidate();

        if (m_tea == null || m_tea.PreferredTeas == null) {
            holder.PreferredTeas.setText("없음");
        } else {
            String text = ResourceUtils.getListItemText(m_tea.PreferredTeas);
            holder.PreferredTeas.setText(text);
        }
        holder.PreferredTeas.invalidate();
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