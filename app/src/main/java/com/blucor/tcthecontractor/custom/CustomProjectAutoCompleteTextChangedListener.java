package com.blucor.tcthecontractor.custom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.blucor.tcthecontractor.project.ProjectListActivity;

public class CustomProjectAutoCompleteTextChangedListener implements TextWatcher {

    public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
    Context context;

    public CustomProjectAutoCompleteTextChangedListener(Context context){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {

        try{
            ProjectListActivity mainActivity = ((ProjectListActivity) context);

            mainActivity.mAdapter.getFilter().filter(userInput);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
