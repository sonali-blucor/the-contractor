package com.blucor.tcthecontractor.rv_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.models.SupplierModal;
import com.blucor.tcthecontractor.models.UnitModal;

import java.util.List;

public class SupplierAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<SupplierModal> supplierModals;

    public SupplierAdapter(Context mContext, List<SupplierModal> supplierModals) {
        this.mContext = mContext;
        this.supplierModals = supplierModals;
    }

    @Override
    public int getCount() {
        return supplierModals.size();
    }

    @Override
    public SupplierModal getItem(int position) {
        return supplierModals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.unit_list_item,null);
        }
        SupplierModal supplier = getItem(position);
        TextView textView = convertView.findViewById(R.id.tv_unit_list_item);
        textView.setText(supplier.supplierName);
        return convertView;
    }
}
