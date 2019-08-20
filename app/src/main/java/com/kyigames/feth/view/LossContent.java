package com.kyigames.feth.view;

import android.icu.lang.UCharacter;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.kimkevin.hangulparser.HangulParser;
import com.github.kimkevin.hangulparser.HangulParserException;
import com.kyigames.feth.R;
import com.kyigames.feth.model.Loss;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

public class LossContent extends AbstractFlexibleItem<LossContent.ViewHolder>
{
    private Loss m_loss;

    public LossContent(Loss loss)
    {
        this.m_loss = loss;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof LossContent)
        {
            LossContent content = (LossContent) o;
            return m_loss.Name.equals(content.m_loss.Name);
        }
        return false;
    }

    @Override
    public int getLayoutRes()
    {
        return R.layout.loss_row;
    }

    @Override
    public LossContent.ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter)
    {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position, List<Object> payloads)
    {
        holder.tv_name.setText(m_loss.Name);
        holder.tv_character.setText(m_loss.Character);
    }

    public static class Adapter extends FlexibleAdapter<IFlexible>
    {
        public Adapter(@Nullable List<IFlexible> items, @Nullable Object listeners, boolean stableIds)
        {
            super(items, listeners, stableIds);
        }

        @Override
        public String onCreateBubbleText(int position)
        {
            // Get and show the first character
            LossContent item = (LossContent) getItem(position);
            int codePoint = item.m_loss.Name.codePointAt(0);

            if (UCharacter.isDigit(codePoint))
            {
                return "0-9";
            } else
            {
                try
                {
                    return HangulParser.disassemble(UCharacter.toString(codePoint)).get(0);
                } catch (HangulParserException e)
                {
                    e.printStackTrace();
                    return "";
                }
            }
        }
    }

    class ViewHolder extends FlexibleViewHolder
    {
        TextView tv_name;
        TextView tv_character;

        ViewHolder(View view, FlexibleAdapter adapter)
        {
            super(view, adapter);
            tv_name = view.findViewById(R.id.loss_name);
            tv_character = view.findViewById(R.id.loss_character);
        }
    }
}