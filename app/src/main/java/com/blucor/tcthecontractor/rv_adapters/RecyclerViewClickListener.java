package com.blucor.tcthecontractor.rv_adapters;

import android.view.View;

public interface RecyclerViewClickListener {
    void recyclerViewListClicked(View v, int position);
    void addViewListClicked(View v, int position);
    void editViewListClicked(View v, int position);
}
