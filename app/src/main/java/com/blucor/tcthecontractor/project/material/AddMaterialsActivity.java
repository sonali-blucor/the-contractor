package com.blucor.tcthecontractor.project.material;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddMaterialsActivity extends BaseAppCompatActivity {

    private TextInputEditText edt_material_bname;
    private TextInputEditText edt_material_des;
    private TextInputEditText edt_material_type;
    private TextInputEditText edt_material_unit;
    private Button btn_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_material);

        edt_material_bname = findViewById(R.id.edt_material_bname);
        edt_material_des = findViewById(R.id.edt_material_des);
        edt_material_type = findViewById(R.id.etd_material_type);
        edt_material_unit= findViewById(R.id.edt_material_unit);
        btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edt_material_bname.getText().toString();
                String des=edt_material_des.getText().toString();
                String mtype=edt_material_type.getText().toString();
                String unt=edt_material_unit.getText().toString();

                //make function for validation and pass all parameters

                validateinfo(name,des,mtype,unt);

            }

            private Boolean validateinfo(String name, String des, String mtype, String unt) {
                if (name.length() == 0) {
                    edt_material_bname.requestFocus();
                    edt_material_bname.setError("FIELD CANNOT BE EMPTY");
                    return false;
                } else if (!name.matches("[a-zA-z]+")) {
                    edt_material_bname.requestFocus();
                    edt_material_bname.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                    return false;
                } else if (des.length() == 0) {
                    edt_material_des.requestFocus();
                    edt_material_des.setError("FIELD CANNOT BE EMPTY");
                    return false;
                } else if (!des.matches("^[+][0-9]{10,13}$")) {
                    edt_material_des.requestFocus();
                    edt_material_des.setError("Correct format: +91xxxxxxxxxx");
                    return false;
                } else if (mtype.length() == 0) {
                    edt_material_type.requestFocus();
                    edt_material_type.setError("FIELD CANNOT BE EMPTY");
                    return false;
                }else if (unt.length() == 0) {
                    edt_material_unit.requestFocus();
                    edt_material_unit.setError("FIELD CANNOT BE EMPTY");
                    return false;
                }else {
                    return true;
                }
            }
        });



    }
}