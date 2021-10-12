package com.blucor.tcthecontractor.project.material;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddSupplierActivity extends BaseAppCompatActivity {


    private TextInputEditText edt_material_supplier_name;
    private TextInputEditText edt_supplier_contact_no;
    private TextInputEditText edt_supplier_email;
    private TextInputEditText edt_supplier_address;
    private Button btn_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_suppiler);

        edt_material_supplier_name = findViewById(R.id.edt_material_supplier_name);
        edt_supplier_contact_no = findViewById(R.id.edt_supplier_contact_no);
        edt_supplier_email = findViewById(R.id.edt_supplier_email);
        edt_supplier_address = findViewById(R.id.edt_supplier_address);
        btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edt_material_supplier_name.getText().toString();
                String cnt=edt_supplier_contact_no.getText().toString();
                String email=edt_supplier_email.getText().toString();
                String adr=edt_supplier_address.getText().toString();

                //make function for validation and pass all parameters

                 validateinfo(name,cnt,email,adr);

            }

            private Boolean validateinfo(String name, String cnt, String email, String adr) {
                if (name.length() == 0) {
                    edt_material_supplier_name.requestFocus();
                    edt_material_supplier_name.setError("FIELD CANNOT BE EMPTY");
                    return false;
                } else if (!name.matches("[a-zA-z]+")) {
                    edt_material_supplier_name.requestFocus();
                    edt_material_supplier_name.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                    return false;
                } else if (cnt.length() == 0) {
                    edt_supplier_contact_no.requestFocus();
                    edt_supplier_contact_no.setError("FIELD CANNOT BE EMPTY");
                    return false;
                } else if (!cnt.matches("^[+][0-9]{10,13}$")) {
                    edt_supplier_contact_no.requestFocus();
                    edt_supplier_contact_no.setError("Correct format: +91xxxxxxxxxx");
                    return false;
                } else if (email.length() == 0) {
                    edt_supplier_email.requestFocus();
                    edt_supplier_email.setError("FIELD CANNOT BE EMPTY");
                    return false;
                }else {
                    return true;
                }
            }
        });



    }
}