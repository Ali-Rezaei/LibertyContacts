package com.sample.android.contact.behavior;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.sample.android.contact.R;

public class QuickHideBottomBarBehavior extends QuickHideBehavior {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private int bottomSpacing;

    //Required to instantiate as a default behavior
    @SuppressWarnings("unused")
    public QuickHideBottomBarBehavior() {
    }

    //Required to attach behavior via XML
    @SuppressWarnings("unused")
    public QuickHideBottomBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        bottomSpacing = (int) context.getResources().getDimension(R.dimen.dimen_recycler_view_spacing);
    }

    @Override
    protected float getTargetHideValue(ViewGroup parent, View target) {
        return parent.getHeight() - target.getTop();
    }

    @Override
    protected void removeSpace() {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mRecyclerView.getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        mRecyclerView.setLayoutParams(params);
    }

    @Override
    protected void setSpace() {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mRecyclerView.getLayoutParams();
        mHandler.postDelayed(() -> {
            mRecyclerView.setLayoutParams(params);
            params.setMargins(0, 0, 0, bottomSpacing);
        }, 250);
    }
}
