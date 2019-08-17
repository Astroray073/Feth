package com.kyigames.feth.view;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kyigames.feth.R;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

public class LicenseItem extends AbstractFlexibleItem<LicenseItem.ViewHolder> {
    private String m_name;
    private String m_type;
    private String m_url;

    public LicenseItem(String name, String type, String url) {
        m_name = name;
        m_type = type;
        m_url = url;
    }

    class ViewHolder extends FlexibleViewHolder {

        ViewGroup LicenseItemContainer;
        TextView LicenseName;
        TextView LicenseType;

        public ViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            LicenseItemContainer = view.findViewById(R.id.license_item_container);
            LicenseName = view.findViewById(R.id.license_name);
            LicenseType = view.findViewById(R.id.license_type);

            LicenseItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof LicenseItem) {
            LicenseItem item = (LicenseItem) o;
            return m_name.equals(item.m_name);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.license_item;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, final ViewHolder holder, int position, List<Object> payloads) {
        holder.LicenseName.setText(m_name);
        holder.LicenseType.setText(m_type);
        holder.LicenseItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(m_url));
                holder.getContentView().getContext().startActivity(browserIntent);
            }
        });
    }
}
