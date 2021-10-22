package com.blucor.tcthecontractor.rv_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.models.MaterialsModal;
import com.blucor.tcthecontractor.models.MaterialsModal;

import java.util.List;

public class MaterialsAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<MaterialsModal> materials;

    public MaterialsAdapter(Context mContext, List<MaterialsModal> materials) {
        this.mContext = mContext;
        this.materials = materials;
    }

    @Override
    public int getCount() {
        return materials.size();
    }

    @Override
    public MaterialsModal getItem(int position) {
        return materials.get(position);
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
        MaterialsModal materials = getItem(position);
        TextView textView = convertView.findViewById(R.id.tv_unit_list_item);
        textView.setText(materials.material_brand);
        return convertView;
    }
}
