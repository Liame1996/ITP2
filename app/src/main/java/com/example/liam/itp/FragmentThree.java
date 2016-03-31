package com.example.liam.itp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Liam on 06/03/2016.
 */
public class FragmentThree extends Fragment {

    public static FragmentThree newInstance() {
        FragmentThree fragment = new FragmentThree();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one_layout, container,false);


        ImageButton goTo = (ImageButton) view.findViewById(R.id.captains);
        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                getActivity().startActivity(i);
            }
        });
        ImageButton goTo2 = (ImageButton) view.findViewById(R.id.tgif);
        goTo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                getActivity().startActivity(i);
            }
        });
        ImageButton goTo3 = (ImageButton) view.findViewById(R.id.counter);
        goTo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                getActivity().startActivity(i);
            }
        });
        ImageButton goTo4 = (ImageButton) view.findViewById(R.id.aussiebbq);
        goTo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                getActivity().startActivity(i);
            }
        });
        ImageButton goTo5 = (ImageButton) view.findViewById(R.id.itailian);
        goTo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                getActivity().startActivity(i);
            }
        });
        return view;
    }
}
