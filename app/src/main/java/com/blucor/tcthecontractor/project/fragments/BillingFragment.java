package com.blucor.tcthecontractor.project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blucor.tcthecontractor.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BillingFragment extends Fragment {

    public BillingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_billing, container, false);
    }
}