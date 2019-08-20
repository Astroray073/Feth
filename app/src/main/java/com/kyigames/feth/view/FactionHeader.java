package com.kyigames.feth.view;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyigames.feth.R;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableHeaderItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.ExpandableViewHolder;

public class FactionHeader extends AbstractExpandableHeaderItem<FactionHeader.ViewHolder, CharacterHeader>
{
    private static String TAG = FactionHeader.class.getSimpleName();

    private String m_factionName;
    private int m_factionColorResId;
    private int m_flagIconId;

    public FactionHeader(String factionName, int factionColorResId, int flagResId)
    {
        super();
        setEnabled(true);
        m_factionName = factionName;
        m_factionColorResId = factionColorResId;
        m_flagIconId = flagResId;
    }

    @Override
    public int getExpansionLevel()
    {
        return 0;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof FactionHeader)
        {
            FactionHeader factionHeader = (FactionHeader) o;
            return m_factionName.equals(factionHeader.m_factionName);
        }
        return false;
    }

    @Override
    public int getLayoutRes()
    {
        return R.layout.character_faction_header;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter)
    {
        return new ViewHolder(view, adapter, true);
    }

    @Override
    public void bindViewHolder(final FlexibleAdapter<IFlexible> adapter, ViewHolder holder, final int position, List<Object> payloads)
    {
        holder.Container.setBackgroundResource(m_factionColorResId);
        holder.FactionIcon.setImageResource(m_flagIconId);
        holder.FactionName.setText(m_factionName);
    }

    class ViewHolder extends ExpandableViewHolder
    {
        View Container;
        ImageView FactionIcon;
        TextView FactionName;

        ViewHolder(View view, final FlexibleAdapter adapter, boolean stickyHeader)
        {
            super(view, adapter, stickyHeader);
            Container = view.findViewById(R.id.faction_header_container);
            FactionIcon = view.findViewById(R.id.character_faction_icon);
            FactionName = view.findViewById(R.id.character_faction_name);

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
}
