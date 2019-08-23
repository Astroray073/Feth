package com.kyigames.feth.view;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kyigames.feth.R;
import com.kyigames.feth.model.ClassCategory;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableHeaderItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.ExpandableViewHolder;

public class ClassCategoryHeader extends AbstractExpandableHeaderItem<ClassCategoryHeader.ViewHolder, ClassHeader>
{
    private static final String TAG = ClassCategoryHeader.class.getSimpleName();

    private ClassCategory m_classCategory;
    private String m_categoryColor;

    public ClassCategoryHeader(ClassCategory classCategory, String categoryColor)
    {
        m_classCategory = classCategory;
        m_categoryColor = categoryColor;
    }

    class ViewHolder extends ExpandableViewHolder
    {
        View Container;
        TextView CategoryName;

        ViewHolder(View view, FlexibleAdapter adapter)
        {
            super(view, adapter, true);
            Container = view.findViewById(R.id.section_header_without_icon_container);
            CategoryName = view.findViewById(R.id.section_header_without_icon_name);

            Container.setOnClickListener(view1 ->
            {
                Log.d(TAG, "toggle expansion :" + m_classCategory.Name);
                toggleExpansion();
            });
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
        if (o instanceof ClassCategoryHeader)
        {
            ClassCategoryHeader categoryHeader = (ClassCategoryHeader) o;
            return m_classCategory.Name.equals(categoryHeader.m_classCategory.Name);
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
        String text;
        if (m_classCategory.MinLevel == 0)
        {
            text = m_classCategory.Name;
        } else
        {
            text = String.format("%s [레벨 %s 이상]", m_classCategory.Name, m_classCategory.MinLevel);
        }

        holder.Container.setBackgroundColor(Color.parseColor(m_categoryColor));
        holder.CategoryName.setText(text);
    }


}
