package com.blucor.tcthecontractor;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blucor.tcthecontractor.network.utils.NetworkHelper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BaseAppCompatActivity extends AppCompatActivity {
    private AlertDialog dialog;

    public void stopLoader() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void showLoader() {
        if (NetworkHelper.hasNetworkAccess(BaseAppCompatActivity.this)) {
            dialog = new AlertDialog.Builder(BaseAppCompatActivity.this).create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ProgressBar pb = new ProgressBar(BaseAppCompatActivity.this);
            dialog.setView(pb);
            dialog.show();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}