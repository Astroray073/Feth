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

public class FactionHeader extends AbstractExpandableHeaderItem<FactionHeader.ViewHolder, CharacterHeader> {
    private static String TAG = FactionHeader.class.getSimpleName();

    public String FactionName;
    public int FactionColorRes;
    public int FlagRes;

    public FactionHeader(String factionName, int factionColorRes, int flagRes) {
        super();
        setEnabled(true);
        FactionName = factionName;
        FactionColorRes = factionColorRes;
        FlagRes = flagRes;
    }

    class ViewHolder extends ExpandableViewHolder {
        public View ly_headerContainer;
        public ImageView ic_faction;
        public TextView tv_faction;

        public ViewHolder(View view, final FlexibleAdapter adapter, boolean stickyHeader) {
            super(view, adapter, stickyHeader);
            ly_headerContainer = view.findViewById(R.id.faction_header_container);
            ic_faction = view.findViewById(R.id.character_faction_icon);
            tv_faction = view.findViewById(R.id.character_faction_name);

            ly_headerContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: toggle expansion");
                    toggleExpansion();
                }
            });
        }
    }

    @Override
    public int getExpansionLevel() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FactionHeader) {
            FactionHeader factionHeader = (FactionHeader) o;
            return FactionName.equals(factionHeader.FactionName);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.character_faction_header;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ViewHolder(view, adapter, true);
    }

    @Override
    public void bindViewHolder(final FlexibleAdapter<IFlexible> adapter, ViewHolder holder, final int position, List<Object> payloads) {
        holder.ly_headerContainer.setBackgroundResource(FactionColorRes);
        holder.ic_faction.setImageResource(FlagRes);
        holder.tv_faction.setText(FactionName);
    }
}
