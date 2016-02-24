package com.loopcupcakes.udacity.popularmovies.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int mSpace;

    public SpacesItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.bottom = mSpace;

        if (parent.getChildAdapterPosition(view) % 2 == 1){
            outRect.right = mSpace;
        }

        if (parent.getChildAdapterPosition(view) < 2){
            outRect.top = mSpace;
        }
    }
}