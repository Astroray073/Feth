package com.kyigames.feth.view;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyigames.feth.R;
import com.kyigames.feth.model.Database;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.flexibleadapter.items.ISectionable;
import eu.davidea.viewholders.ExpandableViewHolder;

public class CharacterHeader extends AbstractExpandableItem<CharacterHeader.ViewHolder, CharacterContent>
        implements ISectionable<CharacterHeader.ViewHolder, FactionHeader> {
    private static final String TAG = CharacterHeader.class.getSimpleName();

    public String Name;

    private FactionHeader m_header;

    public CharacterHeader(FactionHeader header, String name) {
        m_header = header;
        Name = name;
    }

    @Override
    public FactionHeader getHeader() {
        return m_header;
    }

    @Override
    public void setHeader(FactionHeader header) {
        m_header = header;
    }

    class ViewHolder extends ExpandableViewHolder {
        public View ly_container;
        public ImageView ic_character;
        public TextView tv_name;

        public ViewHolder(View view, FlexibleAdapter adapter, boolean stickyHeader) {
            super(view, adapter, stickyHeader);
            ly_container = view.findViewById(R.id.character_header_container);
            ic_character = view.findViewById(R.id.character_icon);
            tv_name = view.findViewById(R.id.character_name);

            ly_container.setOnClickListener(new View.OnClickListener() {
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
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CharacterHeader) {
            CharacterHeader characterHeader = (CharacterHeader) o;
            return Name.equals(characterHeader.Name);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.character_character_header;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ViewHolder(view, adapter, false);
    }

    @Override
    public void bindViewHolder(final FlexibleAdapter<IFlexible> adapter, final ViewHolder holder, final int position, List<Object> payloads) {
        holder.ic_character.setImageResource(Database.getPortrait(Name));
        holder.tv_name.setText(Name);
    }
}