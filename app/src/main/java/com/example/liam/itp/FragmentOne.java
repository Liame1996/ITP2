package com.example.liam.itp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Liam on 06/03/2016.
 */
public class FragmentOne extends Fragment {

    public static FragmentOne newInstance() {
        FragmentOne fragment = new FragmentOne();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one_layout, container,false);

            ImageButton goTo = (ImageButton) view.findViewById(R.id.diceys);
            goTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent i = new Intent(getActivity(), DisplayActivity.class);
                    getActivity().startActivity(i);
                }
            });
        return view;
    }


}
