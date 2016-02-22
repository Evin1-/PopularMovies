package mx.evin.udacity.popularmovies.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class FavSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int mSpace;

    public FavSpacesItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.bottom = mSpace;
        outRect.right = mSpace;

        if (parent.getChildAdapterPosition(view) < 1){
            outRect.top = mSpace;
        }
    }
}