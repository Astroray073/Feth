package com.kyigames.feth.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kyigames.feth.R;
import com.kyigames.feth.model.UnitClass;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

public class ClassContent extends AbstractFlexibleItem<ClassContent.ViewHolder> {
    private static final String TAG = ClassContent.class.getSimpleName();

    private UnitClass m_unitClass;

    public ClassContent(UnitClass unitClass) {
        m_unitClass = unitClass;
    }

    class ViewHolder extends FlexibleViewHolder {
        ViewGroup ClassAbilityContainer;
        List<TextView> AbilityNames = null;
        List<TextView> AbilityDescriptions = null;

        ViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter, false);

            ClassAbilityContainer = view.findViewById(R.id.class_ability_table_container);
            final LayoutInflater inflater = LayoutInflater.from(ClassAbilityContainer.getContext());
            int viewType = ClassContent.this.getItemViewType();

            if (viewType == 0) {
                final TextView noneTextView = new TextView(ClassAbilityContainer.getContext());
                int padding_in_dp = 8;  // 6 dps
                final float scale = ClassAbilityContainer.getContext().getResources().getDisplayMetrics().density;
                int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
                noneTextView.setPadding(padding_in_px, padding_in_px, padding_in_px, padding_in_px);
                noneTextView.setText("없음");
                ClassAbilityContainer.addView(noneTextView);
            } else {
                AbilityNames = new ArrayList<>();
                AbilityDescriptions = new ArrayList<>();

                for (int i = 0; i < viewType; i++) {
                    View abilityLayout = inflater.inflate(R.layout.class_ability_item, null, false);
                    AbilityNames.add((TextView) abilityLayout.findViewById(R.id.class_ability_name));
                    AbilityDescriptions.add((TextView) abilityLayout.findViewById(R.id.class_ability_desc));
                    ClassAbilityContainer.addView(abilityLayout);
                }
            }
        }
    }

    @Override
    public int getItemViewType() {
        return m_unitClass.Abilities == null ? 0 : m_unitClass.Abilities.size();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ClassContent) {
            ClassContent classContent = (ClassContent) o;
            return m_unitClass.Name.equals(classContent.m_unitClass.Name);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.class_class_content;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position, List<Object> payloads) {
        bindClassAbility(holder, adapter.getItemViewType(position));
    }

    public void bindClassAbility(ViewHolder holder, int viewType) {
        Log.d(TAG, "ViewType : " + viewType);
        if (viewType > 0) {
            for (int i = 0; i < viewType; i++) {
                String ability = m_unitClass.Abilities.get(i);
                TextView abilityName = holder.AbilityNames.get(i);
                TextView abilityDesc = holder.AbilityDescriptions.get(i);

                abilityName.setText(ability);
                abilityDesc.setText("DESC");
            }
        }
    }
}
