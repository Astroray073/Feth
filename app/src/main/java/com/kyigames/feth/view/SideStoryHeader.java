package com.kyigames.feth.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyigames.feth.R;
import com.kyigames.feth.model.Character;
import com.kyigames.feth.model.Database;
import com.kyigames.feth.model.SideStory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.flexibleadapter.items.ISectionable;
import eu.davidea.viewholders.ExpandableViewHolder;

public class SideStoryHeader extends AbstractExpandableItem<SideStoryHeader.ViewHolder, SideStoryContent>
        implements ISectionable<SideStoryHeader.ViewHolder, SideStorySectionHeader>
{

    private static final int MAX_PARTICIPANT_COUNT = 2;
    private SideStorySectionHeader m_header;
    private String m_name;
    private String m_startDate;
    private int m_recLevel;
    private List<Integer> m_participants = new ArrayList<>();

    public SideStoryHeader(SideStorySectionHeader header, SideStory sideStory)
    {
        setHeader(header);
        m_name = sideStory.Name;
        m_startDate = sideStory.StartTime;
        m_recLevel = sideStory.RecLevel;

        for (String participant : sideStory.Participants)
        {
            Character found = Database.findEntityByKey(Character.class, participant);
            m_participants.add(found.getIcon());
        }
    }

    @Override
    public SideStorySectionHeader getHeader()
    {
        return m_header;
    }

    @Override
    public void setHeader(SideStorySectionHeader header)
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
        if (o instanceof SideStoryHeader)
        {
            SideStoryHeader sideStoryHeader = (SideStoryHeader) o;
            return m_name.equals(sideStoryHeader.m_name);
        }
        return false;
    }

    @Override
    public int getLayoutRes()
    {
        return R.layout.side_stroy_header;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter)
    {
        return new ViewHolder(view, adapter);
    }


    class ViewHolder extends ExpandableViewHolder
    {
        ViewGroup Container;
        TextView Name;
        TextView SubText;
        ViewGroup ParticipantsContainer;
        ImageView[] Participants = new ImageView[MAX_PARTICIPANT_COUNT];

        public ViewHolder(View view, FlexibleAdapter adapter)
        {
            super(view, adapter, false);
            Container = view.findViewById(R.id.side_story_header_container);
            Name = view.findViewById(R.id.side_story_header_name);
            SubText = view.findViewById(R.id.side_story_header_date);
            ParticipantsContainer = view.findViewById(R.id.side_story_participants);

            for (int i = 0; i < Participants.length; ++i)
            {
                Participants[i] = (ImageView) ParticipantsContainer.getChildAt(i);
            }

            Container.setOnClickListener(clickedView -> toggleExpansion());
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position, List<Object> payloads)
    {
        holder.Name.setText(m_name);
        holder.SubText.setText(String.format(Locale.KOREAN, "%s - 레벨 %d 이상", m_startDate, m_recLevel));

        for (int i = 0; i < MAX_PARTICIPANT_COUNT; ++i)
        {
            if (i < m_participants.size())
            {
                holder.Participants[i].setVisibility(View.VISIBLE);
                holder.Participants[i].setImageResource(m_participants.get(i));
            } else
            {
                holder.Participants[i].setVisibility(View.INVISIBLE);
            }
        }
    }
}
