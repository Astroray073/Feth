package com.kyigames.feth.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyigames.feth.R;

public class AbilityIconHeader extends LinearLayout
{
    private ImageView m_icon;
    private TextView m_text;

    public AbilityIconHeader(Context context)
    {
        super(context);
        initView();
    }

    public AbilityIconHeader(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public AbilityIconHeader(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView();
        getAttrs(attrs, defStyleAttr);
    }

    private void initView()
    {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.ability_icon_header, this, false);
        addView(v);

        m_icon = findViewById(R.id.ability_icon_header_icon);
        m_text = findViewById(R.id.ability_icon_header_name);
    }

    private void getAttrs(AttributeSet attrs)
    {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AbilityIconHeader);
        setAttrs(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle)
    {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AbilityIconHeader, defStyle, 0);
        setAttrs(typedArray);
    }

    private void setAttrs(TypedArray attr)
    {
        int iconRes = attr.getResourceId(R.styleable.AbilityIconHeader_ability_icon, 0);
        setIcon(iconRes);

        String textString = attr.getString(R.styleable.AbilityIconHeader_ability_name);
        setName(textString);

        attr.recycle();
    }

    public void setIcon(int iconRes)
    {
        m_icon.setImageResource(iconRes);
    }

    public void setName(String text)
    {
        m_text.setText(text);
    }
}
