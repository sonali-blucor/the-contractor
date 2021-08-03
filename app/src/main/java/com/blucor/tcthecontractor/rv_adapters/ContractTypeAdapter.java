package com.blucor.tcthecontractor.rv_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.models.Contract_Type;

import java.util.List;

public class ContractTypeAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<Contract_Type> contract_types;

    public ContractTypeAdapter(Context mContext, List<Contract_Type> contract_types) {
        this.mContext = mContext;
        this.contract_types = contract_types;
    }

    @Override
    public int getCount() {
        return contract_types.size();
    }

    @Override
    public Contract_Type getItem(int position) {
        return contract_types.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.contract_type_list_item,null);
        }
        Contract_Type contract_type = getItem(position);
        TextView textView = convertView.findViewById(R.id.tv_contract_type_list_item);
        textView.setText(contract_type.contract_type);
        return convertView;
    }
}
