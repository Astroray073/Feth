package com.kyigames.feth.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.kyigames.feth.R;

public class SquaredImageView extends AppCompatImageView
{
    private int m_criteria;

    public SquaredImageView(Context context)
    {
        super(context);
    }

    public SquaredImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        getAttrs(attrs);
    }

    public SquaredImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs, defStyleAttr);
    }

    private void getAttrs(AttributeSet attrs)
    {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SquaredImageView);
        setAttrs(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle)
    {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SquaredImageView, defStyle, 0);
        setAttrs(typedArray);
    }

    private void setAttrs(TypedArray attr)
    {
        m_criteria = attr.getInt(R.styleable.SquaredImageView_criteria, 0);
        attr.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int value = m_criteria == 0 ? getMeasuredWidth() : getMeasuredHeight();
        setMeasuredDimension(value, value);
    }
}
