package com.shustanov.lorimobile.fragment.week;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * WeekItemDecorator
 * </p>
 * alexander.shustanov on 06.11.16
 */
public class WeekItemDecorator extends RecyclerView.ItemDecoration {
    private final Drawable divider;

    public WeekItemDecorator(Drawable divider) {
        this.divider = divider;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int dividerLeft = parent.getPaddingLeft();
        int dividerRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 1; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (!(parent.getChildViewHolder(child) instanceof WeekAdapter.DayHolder)) {
                continue;
            }

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int dividerTop = child.getTop() - params.topMargin;
            int dividerBottom = dividerTop + 2;


            divider.setBounds(dividerLeft + 10, dividerTop, dividerRight - 10, dividerBottom);
            divider.draw(c);
        }
    }
}
