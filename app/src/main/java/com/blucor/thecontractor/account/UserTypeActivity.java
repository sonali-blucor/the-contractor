package com.blucor.thecontractor.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.network.utils.Contants;
import com.blucor.thecontractor.utility.ScreenHelper;

import androidx.appcompat.app.AppCompatActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class UserTypeActivity extends AppCompatActivity {
    private RadioGroup rg_user_type;
    private TextView tv_contractor;
    private TextView tv_client;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_type);

        rg_user_type = findViewById(R.id.rg_user_type);
        tv_contractor = findViewById(R.id.tv_contractor_user_type);
        tv_client = findViewById(R.id.tv_client_user_type);

        sharedPreferences = getSharedPreferences(Contants.USER_PREFERNCE_NAME, Context.MODE_PRIVATE);

        rg_user_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_contractor:
                        selectContractor();
                        break;
                    case R.id.rb_client:
                        selectClient();
                        break;
                    default:
                        Toast.makeText(UserTypeActivity.this, "Please select client or contractor", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void selectClient() {
        tv_client.setTextColor(getResources().getColor(R.color.yellow));
        tv_contractor.setTextColor(getResources().getColor(R.color.black));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Contants.USER_TYPE_KEY,Contants.USER_TYPE_CLIENT);
        editor.apply();

        ScreenHelper.redirectToClass(UserTypeActivity.this,LoginActivity.class);finish();;
    }

    private void selectContractor() {
        tv_client.setTextColor(getResources().getColor(R.color.black));
        tv_contractor.setTextColor(getResources().getColor(R.color.yellow));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Contants.USER_TYPE_KEY,Contants.USER_TYPE_CONTRACTOR);
        editor.apply();

        ScreenHelper.redirectToClass(UserTypeActivity.this,LoginActivity.class);finish();
    }

}