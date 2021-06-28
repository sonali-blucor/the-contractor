package com.blucor.thecontractor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.blucor.thecontractor.account.UserTypeActivity;
import com.blucor.thecontractor.client.ClientProjectsActivity;
import com.blucor.thecontractor.database.DatabaseUtil;
import com.blucor.thecontractor.network.utils.Contants;
import com.blucor.thecontractor.utility.ScreenHelper;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    //private DbHandler mDbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView mImgLogo = findViewById(R.id.img_logo);
        //mDbHandler = DbHandler.getInstance(getApplicationContext());
        sharedPreferences = getSharedPreferences(Contants.USER_PREFERNCE_NAME, Context.MODE_PRIVATE);
        int width = ScreenHelper.getWidthInPercentage(getApplicationContext(), 60);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, width);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        mImgLogo.setLayoutParams(params);

        Animation slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide);
        mImgLogo.startAnimation(slideAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // mDbHandler.getReadableDb();
                if (DatabaseUtil.on().hasLogin()) {
                    if (sharedPreferences.getInt(Contants.USER_TYPE_KEY, 0) == Contants.USER_TYPE_CONTRACTOR) {
                        ScreenHelper.redirectToClass(SplashActivity.this, MenuActivity.class);
                    } else if (sharedPreferences.getInt(Contants.USER_TYPE_KEY, 0) == Contants.USER_TYPE_CLIENT) {
                        ScreenHelper.redirectToClass(SplashActivity.this, ClientMenuActivity.class);
                    }
                }
                else
                    ScreenHelper.redirectToClass(SplashActivity.this, UserTypeActivity.class);

                finish();
            }
        }, 1000);
    }
}