package com.blucor.tcthecontractor.custom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.blucor.tcthecontractor.project.activity.AddSubContractorActivity;

public class CustomSubContractorAutoCompleteTextChangedListener implements TextWatcher {

    public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
    Context context;

    public CustomSubContractorAutoCompleteTextChangedListener(Context context){
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
            AddSubContractorActivity mainActivity = ((AddSubContractorActivity) context);

            mainActivity.adapter.getFilter().filter(userInput);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
