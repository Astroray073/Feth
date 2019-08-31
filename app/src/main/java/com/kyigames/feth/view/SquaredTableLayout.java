package com.kyigames.feth.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableLayout;
import android.widget.TableRow;

public class SquaredTableLayout extends TableLayout
{
    public SquaredTableLayout(Context context)
    {
        super(context);
    }

    public SquaredTableLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int childCount = getChildCount();

        int maxHeight = 0;
        for (int i = 0; i < childCount; ++i)
        {
            TableRow row = (TableRow) getChildAt(i);
            maxHeight = Math.max(maxHeight, row.getMeasuredHeight());
        }

        for (int i = 0; i < childCount; ++i)
        {
            TableRow row = (TableRow) getChildAt(i);
            row.setMinimumHeight(maxHeight);
        }
    }
}
