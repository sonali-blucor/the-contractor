package com.blucor.thecontractor.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.network.utils.Contants;
import com.blucor.thecontractor.utility.ScreenHelper;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class UserTypeActivity extends AppCompatActivity {
    private int backPressed = 0;

    private TextView tv_contractor;
    private TextView tv_client;
    private CardView cv_client;
    private CardView cv_contractor;
    private TextView btn_go;
    private SharedPreferences sharedPreferences;

    private LinearLayout ll_contractor, ll_client;
    private ImageView img_contractor, img_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_type);

        //rg_user_type = findViewById(R.id.rg_user_type);

        tv_contractor = findViewById(R.id.tv_contractor_user_type);
        tv_client = findViewById(R.id.tv_client_user_type);
//        cv_client = findViewById(R.id.cv_1);
//        cv_contractor = findViewById(R.id.cv_2);
        btn_go = findViewById(R.id.btn_go);

        ll_contractor = findViewById(R.id.cv_1);
        ll_client = findViewById(R.id.cv_2);
        img_contractor = findViewById(R.id.img_contractor);
        img_client = findViewById(R.id.img_client);

        ll_contractor.setOnClickListener(onClickListener);
        ll_client.setOnClickListener(onClickListener);
        img_contractor.setOnClickListener(onClickListener);
        img_client.setOnClickListener(onClickListener);

        sharedPreferences = getSharedPreferences(Contants.USER_PREFERNCE_NAME, Context.MODE_PRIVATE);
     /*   cv_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectClient();
            }
        });

        cv_contractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectContractor();
            }
        });*/

       /* try {
            if (sharedPreferences.getInt(Contants.USER_TYPE_KEY, 0) == Contants.USER_TYPE_CONTRACTOR) {
                ((RadioButton) findViewById(R.id.rb_contractor)).setChecked(true);
            }
            if (sharedPreferences.getInt(Contants.USER_TYPE_KEY, 1) == Contants.USER_TYPE_CLIENT) {
                ((RadioButton) findViewById(R.id.rb_client)).setChecked(true);
            }
        } catch (Exception e) {
        }*/
        /*rg_user_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        });*/
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            switch (v.getId()) {
                case R.id.cv_1:
                case R.id.img_contractor:
                    selectContractor();
                    break;
                case R.id.cv_2:
                case R.id.img_client:
                    selectClient();
                    break;
            }
        }
    };

    private void selectClient() {
        tv_client.setTextColor(getResources().getColor(R.color.yellow));
        tv_contractor.setTextColor(getResources().getColor(R.color.black));
        btn_go.setVisibility(View.VISIBLE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Contants.USER_TYPE_KEY, Contants.USER_TYPE_CLIENT);
        editor.apply();
    }

    private void selectContractor() {
        tv_client.setTextColor(getResources().getColor(R.color.black));
        tv_contractor.setTextColor(getResources().getColor(R.color.yellow));
        btn_go.setVisibility(View.VISIBLE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Contants.USER_TYPE_KEY, Contants.USER_TYPE_CONTRACTOR);
        editor.apply();
    }

    public void onClickToGo(View view) {
        ScreenHelper.redirectToClass(UserTypeActivity.this, LoginActivity.class);
        finish();
    }

    @Override
    public void onBackPressed() {
        backPressed = backPressed + 1;
        if (backPressed == 1) {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            new CountDownTimer(5000, 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    backPressed = 0;
                }
            }.start();
        }
        if (backPressed == 2) {
            backPressed = 0;
            this.finishAffinity();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}