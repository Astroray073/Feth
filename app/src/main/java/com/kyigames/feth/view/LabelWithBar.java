package com.kyigames.feth.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyigames.feth.R;

public class LabelWithBar extends LinearLayout {
    private LinearLayout m_container;
    private TextView m_text;
    private View m_divider;

    public LabelWithBar(Context context) {
        super(context);
        initView();
    }

    public LabelWithBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public LabelWithBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        getAttrs(attrs, defStyleAttr);
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(infService);
        View v = inflater.inflate(R.layout.label_with_bar, this, false);
        addView(v);

        m_container = findViewById(R.id.label_container);
        m_text = findViewById(R.id.label_text);
        m_divider = findViewById(R.id.label_divider);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LabelWithBar);
        setAttrs(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LabelWithBar, defStyle, 0);
        setAttrs(typedArray);
    }

    private void setAttrs(TypedArray attr) {
        String textString = attr.getString(R.styleable.LabelWithBar_text);
        m_text.setText(textString);

        int barColor = attr.getColor(R.styleable.LabelWithBar_bar_color, Color.BLACK);
        m_divider.setBackgroundColor(barColor);

        attr.recycle();
    }

}
