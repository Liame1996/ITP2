package com.example.liam.itp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by johncahill1 on 07/04/16.
 */
public class FactFragment extends Fragment{


    public static FactFragment newInstance() {
        FactFragment fragment = new FactFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_facts, container, false);


        return view;

    }
}
