package com.blucor.tcthecontractor.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.models.UnitModal;
import com.blucor.tcthecontractor.network.retrofit.RetrofitClient;
import com.blucor.tcthecontractor.rv_adapters.UnitAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UnitView extends LinearLayout {
    // internal components


    private Context context;

    private TextInputLayout til_unit;
    private TextInputEditText edt_unit;

    private ImageView img_add_unit;
    private ImageView img_search_unit;
    private ImageView img_close;
    private LinearLayout llv_unit_item;
    private TextView tv_unit_item;
    private ListView lst_unit;

    private TextInputLayout til_add_unit;
    private TextInputEditText edt_add_unit;
    private Button btn_submit;

    private BottomSheetDialog bottomSheetDialog;

    private String selectedUnit = "";
    private int selectedUnitId = 0;

    List<UnitModal> units;

    public UnitView(Context context) {
        super(context);
        initControl(context);
        this.context = context;
    }

    public UnitView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
        this.context = context;
    }

    /**
     * Load component XML layout
     */
    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.control_unit, this);

        til_unit = findViewById(R.id.til_unit);
        edt_unit = findViewById(R.id.edt_unit);
        til_unit.setEndIconOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetUnit();
            }
        });

        getUnits();
    }

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_add_close:
                    bottomSheetDialog.dismiss();
                    break;
                case R.id.img_close:
                    bottomSheetDialog.dismiss();
                    break;
                case R.id.img_add_unit:
                    bottomSheetDialog.dismiss();
                    showBottomSheetAddUnit();
                    break;
                case R.id.img_search_unit:
                    break;
                case R.id.til_unit:
//                    bottomSheetDialog.dismiss();
                    showBottomSheetUnit();
                    break;
                case R.id.btn_submit:
                    bottomSheetDialog.dismiss();
                    break;
            }
        }
    };

    private void showBottomSheetAddUnit() {
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_add_unit);
        img_close = bottomSheetDialog.findViewById(R.id.img_add_close);
        til_add_unit = bottomSheetDialog.findViewById(R.id.til_add_unit);
        edt_add_unit = bottomSheetDialog.findViewById(R.id.edt_add_unit);
        btn_submit = bottomSheetDialog.findViewById(R.id.btn_submit);

        img_close.setOnClickListener(onClickListener);
        btn_submit.setOnClickListener(onClickListener);

        bottomSheetDialog.show();
    }

    private void showBottomSheetUnit() {
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_unit);
        img_add_unit = bottomSheetDialog.findViewById(R.id.img_add_unit);
        img_search_unit = bottomSheetDialog.findViewById(R.id.img_search_unit);
        img_close = bottomSheetDialog.findViewById(R.id.img_close);
        llv_unit_item = bottomSheetDialog.findViewById(R.id.llv_unit_item);
        tv_unit_item = bottomSheetDialog.findViewById(R.id.tv_unit_item);
        lst_unit = bottomSheetDialog.findViewById(R.id.lst_unit);

        if (getSelectedUnit().equals("")) {
            llv_unit_item.setVisibility(GONE);
        } else {
            llv_unit_item.setVisibility(VISIBLE);
            tv_unit_item.setText(getSelectedUnit());
        }

        img_add_unit.setOnClickListener(onClickListener);
        img_search_unit.setOnClickListener(onClickListener);
        img_close.setOnClickListener(onClickListener);

        UnitAdapter adapter = new UnitAdapter(context, units);
        lst_unit.setAdapter(adapter);
        lst_unit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUnit = units.get(position).unit;
                edt_unit.setText(selectedUnit);
                tv_unit_item.setText(selectedUnit);
                setSelectedUnitId(units.get(position).id);
//                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }


    public String getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(String selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public int getSelectedUnitId() {
        return selectedUnitId;
    }

    public void setSelectedUnitId(int selectedUnitId) {
        this.selectedUnitId = selectedUnitId;
    }

    private void getUnits() {
        RetrofitClient.getApiService().getUnits().enqueue(new Callback<List<UnitModal>>() {
            @Override
            public void onResponse(Call<List<UnitModal>> call, Response<List<UnitModal>> response) {
                if (response.code() == 200) {
                    List<UnitModal> server_units = response.body();
                    if (server_units != null && server_units.size() > 0) {
                        units = server_units;
                    } else {
                        units = new ArrayList<>();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UnitModal>> call, Throwable t) {
                Log.e("get units", "" + t.getMessage());
                units = new ArrayList<>();
            }
        });
    }

}
