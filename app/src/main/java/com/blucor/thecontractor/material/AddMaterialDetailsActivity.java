package com.blucor.thecontractor.material;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.helper.AppKeys;
import com.blucor.thecontractor.utility.ScreenHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AddMaterialDetailsActivity extends AppCompatActivity {

    private boolean isAddOrEdit = false;

    private TextInputEditText mEdtMDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material_details);

        mEdtMDate = (TextInputEditText) findViewById(R.id.edt_material_date);

       /* mEdtMDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                onClickDate(v);
            }
        });*/

        try {
            Bundle bundle = getIntent().getExtras();
            if (getIntent().hasExtra(AppKeys.ACTIVITY_DETAIL_TYPE)) {
                isAddOrEdit = bundle.getBoolean(AppKeys.ACTIVITY_DETAIL_TYPE);
            }
        } catch (Exception e) {
            isAddOrEdit = false;
        }
    }

    public void onClickDate(View view){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddMaterialDetailsActivity.this,
                new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String startDate = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear) + "/" + String.valueOf(year);
                        mEdtMDate.setText(startDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void onClickToSubmit(View view) {
        if (isAddOrEdit)
            ScreenHelper.redirectToClass(AddMaterialDetailsActivity.this, EditMaterialActivity.class);
        else
            ScreenHelper.redirectToClass(AddMaterialDetailsActivity.this, AddMaterialActivity.class);

    }
}