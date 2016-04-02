package com.example.liam.itp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Liam on 31/03/2016.
 */
public class Whiskey extends Fragment {

    public static Whiskey newInstance() {
        Whiskey whiskey = new Whiskey();
        return whiskey;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.homescreen, container, false);

    }
}
