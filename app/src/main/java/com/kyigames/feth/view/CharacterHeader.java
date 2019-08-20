package com.kyigames.feth.view;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kyigames.feth.R;
import com.kyigames.feth.model.Character;
import com.kyigames.feth.utils.ResourceUtils;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.flexibleadapter.items.ISectionable;
import eu.davidea.viewholders.ExpandableViewHolder;

public class CharacterHeader extends AbstractExpandableItem<CharacterHeader.ViewHolder, CharacterContent>
        implements ISectionable<CharacterHeader.ViewHolder, FactionHeader>
{
    private static final String TAG = CharacterHeader.class.getSimpleName();

    private FactionHeader m_header;

    private String m_characterName;
    private int m_portraitResId;
    @Nullable
    private String m_scoutCondition;

    public CharacterHeader(FactionHeader header, Character character)
    {
        m_header = header;

        m_characterName = character.Name;
        m_portraitResId = character.getPortraitIcon();

        if (character.Scout == null)
        {
            m_scoutCondition = null;
        } else
        {
            m_scoutCondition = ResourceUtils.getListItemText(character.Scout, "/");
        }
    }


    class ViewHolder extends ExpandableViewHolder
    {
        View Container;
        ImageView CharacterPortrait;
        TextView CharacterName;
        TextView ScoutCondition;

        ViewHolder(View view, FlexibleAdapter adapter, boolean stickyHeader)
        {
            super(view, adapter, stickyHeader);
            Container = view.findViewById(R.id.character_header_container);
            CharacterPortrait = view.findViewById(R.id.character_header_character_portrait);
            CharacterName = view.findViewById(R.id.character_header_character_name);
            ScoutCondition = view.findViewById(R.id.character_header_scout_condition);

            Container.setOnClickListener(clickedView ->
            {
                Log.d(TAG, "onClick: toggle expansion");
                toggleExpansion();
            });
        }
    }

    @Override
    public FactionHeader getHeader()
    {
        return m_header;
    }

    @Override
    public void setHeader(FactionHeader header)
    {
        m_header = header;
    }

    @Override
    public int getExpansionLevel()
    {
        return 1;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof CharacterHeader)
        {
            CharacterHeader characterHeader = (CharacterHeader) o;
            return m_characterName.equals(characterHeader.m_characterName);
        }
        return false;
    }

    @Override
    public int getLayoutRes()
    {
        return R.layout.character_character_header;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter)
    {
        return new ViewHolder(view, adapter, false);
    }

    @Override
    public void bindViewHolder(final FlexibleAdapter<IFlexible> adapter, final ViewHolder holder, final int position, List<Object> payloads)
    {
        holder.CharacterPortrait.setImageResource(m_portraitResId);
        holder.CharacterName.setText(m_characterName);

        if (m_scoutCondition == null)
        {
            holder.ScoutCondition.setVisibility(View.GONE);
        } else
        {
            holder.ScoutCondition.setVisibility(View.VISIBLE);
            holder.ScoutCondition.setText(m_scoutCondition);
        }

        holder.Container.invalidate();
    }

}