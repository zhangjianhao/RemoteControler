package com.zjianhao.adapter.recyclerview.wrapper;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int top;
    private int bottom;
    private int left;
    private int right;


    public SpaceItemDecoration(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.left = left;
        outRect.right = right;
        outRect.top = top;
        outRect.bottom = bottom;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0) {
            outRect.left = 0;
            outRect.top = 0;
        }

    }
}