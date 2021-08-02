package com.blucor.thecontractor.rv_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.models.UnitModal;

import java.util.List;

public class UnitAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<UnitModal> units;

    public UnitAdapter(Context mContext, List<UnitModal> units) {
        this.mContext = mContext;
        this.units = units;
    }

    @Override
    public int getCount() {
        return units.size();
    }

    @Override
    public UnitModal getItem(int position) {
        return units.get(position);
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
        UnitModal unit = getItem(position);
        TextView textView = convertView.findViewById(R.id.tv_unit_list_item);
        textView.setText(unit.unit);
        return convertView;
    }
}
