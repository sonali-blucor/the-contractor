package com.blucor.thecontractor.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blucor.thecontractor.R;
import com.blucor.thecontractor.network.utils.Contants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenHelper {

    public static int getWidthInPercentage(Context context, int percentage) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int screen_width = display.getWidth();
        int control_width = (screen_width * percentage) / 100;
        return control_width;
    }

    public static int getHeightInPercentage(Context context, int percentage) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int screen_height = display.getHeight();
        int control_height = (screen_height * percentage) / 100;
        return control_height;
    }

    public static void redirectToClass(Activity context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
        context.overridePendingTransition(0, 0);
    }

    public static void redirectToClass(Activity context, Class cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
        context.overridePendingTransition(0, 0);
    }

    public static void exitApp(final Activity context) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(context.getString(R.string.exit_app_string));

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();

                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(startMain);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    context.finishAffinity();
                } else {
                    context.finish();
                }

                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public static void goToDialPad(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    public static String findDifference(String start_date, String end_date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d1 = sdf.parse(start_date + "00:00:00");
            Date d2 = sdf.parse(end_date + "00:00:00");
            long difference_In_Time
                    = d2.getTime() - d1.getTime();
            long difference_In_Seconds
                    = (difference_In_Time
                    / 1000)
                    % 60;

            long difference_In_Minutes
                    = (difference_In_Time
                    / (1000 * 60))
                    % 60;

            long difference_In_Hours
                    = (difference_In_Time
                    / (1000 * 60 * 60))
                    % 24;

            long difference_In_Years
                    = (difference_In_Time
                    / (1000l * 60 * 60 * 24 * 365));

            long difference_In_Days
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24))
                    % 365;
            System.out.print("Difference " + "between two dates is: ");
            System.out.println(
                    difference_In_Years
                            + " years, "
                            + difference_In_Days
                            + " days, "
                            + difference_In_Hours
                            + " hours, "
                            + difference_In_Minutes
                            + " minutes, "
                            + difference_In_Seconds
                            + " seconds");
            return String.valueOf(difference_In_Days);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(0);
    }

    public static void setThumbImageUriInView(ImageView imageView, String imageUri) {
        String imageUrl = Contants.IMAGE_URL + imageUri;
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.ic_user);
        options.error(R.drawable.ic_camera);

        Glide.with(imageView)
                .load(imageUrl)
                .apply(options)
                .fitCenter()
                .into(imageView);
    }

}
