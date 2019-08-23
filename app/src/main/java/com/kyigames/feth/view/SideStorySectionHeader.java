package com.kyigames.feth.view;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.kyigames.feth.R;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableHeaderItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.ExpandableViewHolder;

public class SideStorySectionHeader extends AbstractExpandableHeaderItem<SideStorySectionHeader.ViewHolder, SideStoryHeader>
{
    private static final String TAG = SideStorySectionHeader.class.getSimpleName();

    private String m_sectionName;
    private String m_sectionColor;

    public SideStorySectionHeader(String sectionName, String color)
    {
        m_sectionName = sectionName;
        m_sectionColor = color;
    }

    class ViewHolder extends ExpandableViewHolder
    {
        View Container;
        TextView SectionName;

        public ViewHolder(View view, FlexibleAdapter adapter)
        {
            super(view, adapter, true);

            Container = view.findViewById(R.id.section_header_without_icon_container);
            SectionName = view.findViewById(R.id.section_header_without_icon_name);

            Container.setOnClickListener(clickedView -> toggleExpansion());
        }
    }

    @Override
    public int getExpansionLevel()
    {
        return 0;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof SideStorySectionHeader)
        {
            SideStorySectionHeader sectionHeader = (SideStorySectionHeader) o;
            return m_sectionName.equals(sectionHeader.m_sectionName);
        }
        return false;
    }

    @Override
    public int getLayoutRes()
    {
        return R.layout.section_header_without_icon;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter)
    {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position, List<Object> payloads)
    {
        holder.Container.setBackgroundColor(Color.parseColor(m_sectionColor));
        holder.SectionName.setText(m_sectionName);
    }
}
