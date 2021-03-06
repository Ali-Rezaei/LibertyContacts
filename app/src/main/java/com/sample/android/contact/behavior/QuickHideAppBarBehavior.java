package com.sample.android.contact.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.android.contact.R;

public class QuickHideAppBarBehavior extends QuickHideBehavior {

    private int actionBarHeight;

    //Required to instantiate as a default behavior
    @SuppressWarnings("unused")
    public QuickHideAppBarBehavior() {
    }

    //Required to attach behavior via XML
    @SuppressWarnings("unused")
    public QuickHideAppBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Calculate ActionBar height
        TypedValue tv = new TypedValue();
        actionBarHeight = context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true) ?
                TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics()) :
                (int) context.getResources().getDimension(R.dimen.dimen_recycler_view_spacing);
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                 @NonNull View target, float velocityX, float velocityY,
                                 boolean consumed) {
        if (!(target instanceof RecyclerView)) {
            return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
        }
        return false;
    }

    @Override
    protected float getTargetHideValue(ViewGroup parent, View target) {
        return -target.getHeight();
    }

    @Override
    protected void removeSpace(View view) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        view.setLayoutParams(params);
    }

    @Override
    protected void addSpace(View view) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        mHandler.postDelayed(() -> {
            view.setLayoutParams(params);
            params.setMargins(0, actionBarHeight, 0, 0);
        }, 100);
    }
}
