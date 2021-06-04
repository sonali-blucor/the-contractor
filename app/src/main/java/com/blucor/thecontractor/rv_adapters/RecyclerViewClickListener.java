package com.blucor.thecontractor.rv_adapters;

import android.view.View;

public interface RecyclerViewClickListener {
    public void recyclerViewListClicked(View v, int position);
    public void addViewListClicked(View v, int position);
    public void editViewListClicked(View v, int position);
}
