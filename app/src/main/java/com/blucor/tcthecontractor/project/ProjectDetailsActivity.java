package com.blucor.tcthecontractor.project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.blucor.tcthecontractor.BaseAppCompatActivity;
import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.helper.AppKeys;
import com.blucor.tcthecontractor.models.ProjectsModel;
import com.blucor.tcthecontractor.utility.ScreenHelper;

public class ProjectDetailsActivity extends BaseAppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private ProjectsModel project;
    private TextView btn_call;
    private TextView tv_project_id;
    private TextView tv_project_name;
    private TextView tv_project_type;
    private TextView tv_contract_type;
    private TextView tv_client_name;
    private TextView tv_client_mobile;
    private TextView tv_client_id;
    private TextView tv_client_password;
    private TextView tv_project_location;
    private TextView tv_project_start_end_date;
    private TextView tv_project_duration;
    private TextView tv_project_work_order;

    private CheckBox chk_password_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        btn_call = findViewById(R.id.btn_call);
        tv_project_id = findViewById(R.id.tv_project_id);
        tv_project_name = findViewById(R.id.tv_project_name);
        tv_project_type = findViewById(R.id.tv_project_type);
        tv_contract_type = findViewById(R.id.tv_contract_type);
        tv_client_name = findViewById(R.id.tv_client_name);
        tv_client_mobile = findViewById(R.id.tv_client_mobile);
        tv_client_id = findViewById(R.id.tv_client_id);
        tv_client_password = findViewById(R.id.tv_client_password);
        tv_project_location = findViewById(R.id.tv_project_location);
        tv_project_start_end_date = findViewById(R.id.tv_project_start_end_date);
        tv_project_duration = findViewById(R.id.tv_project_duration);
        tv_project_work_order = findViewById(R.id.tv_project_work_order);
        chk_password_show = findViewById(R.id.chk_password_show);

        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle.containsKey(AppKeys.PROJECT)) {
                project = bundle.getParcelable(AppKeys.PROJECT);
                setupProject();
            }
        } catch (Exception e) {

        }

    }

    private void setupProject() {
        tv_project_id.setText(""+project.id);
        tv_project_name.setText(""+project.project_name);
        tv_project_type.setText(""+project.project_type);
        tv_contract_type.setText(""+project.contract_type);
        tv_client_name.setText(""+project.client_fname+" "+project.client_lname);
        tv_client_mobile.setText(""+project.mobile);
        tv_client_id.setText(""+project.client_id);
        tv_project_location.setText(""+project.project_location);
        tv_project_start_end_date.setText(""+project.start_date+" to "+project.end_date);
        tv_project_duration.setText(""+project.duration);
        tv_project_work_order.setText("0 Rs.");


        chk_password_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    tv_client_password.setTransformationMethod(new PasswordTransformationMethod());
                }else{
                    tv_client_password.setTransformationMethod(null);
                }
            }
        });
    }


    public void onClickToCall(View view) {
        if (project != null) {
            Uri u = Uri.parse("tel:" + project.mobile);
            Intent i = new Intent(Intent.ACTION_DIAL, u);
            try {
                startActivity(i);
            } catch (SecurityException s) {
                Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void onClickToClientSMS(View view) {
        checkForSmsPermission();
        String destinationAddress = "smsto:" + project.mobile;
        String smsMessage = "Client ID: "+project.client_id+" \n Client Name: "+project.client_fname+" "+project.client_lname;
        // Create the intent.
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse(destinationAddress));
        smsIntent.putExtra("sms_body", smsMessage);
        startActivity(smsIntent);
       /* if (smsIntent.resolveActivity(getPackageManager()) != null) {
        } else {
            Log.e("ProjectDetails", "Can't resolve app for ACTION_SENDTO Intent.");
        }*/
    }

    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d("ProjectDetails", getString(R.string.permission_not_granted));
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the message button.
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (permissions[0].equalsIgnoreCase(Manifest.permission.SEND_SMS)
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted.
                } else {
                    // Permission denied.
                    Log.d("ProjectDetails", getString(R.string.failure_permission));
                    Toast.makeText(ProjectDetailsActivity.this,
                            getString(R.string.failure_permission),
                            Toast.LENGTH_SHORT).show();
                    // Disable the message button.
//                    disableSmsButton();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppKeys.PROJECT,project);
        ScreenHelper.redirectToClass(this,ProjectMenuActivity.class,bundle);
    }

}