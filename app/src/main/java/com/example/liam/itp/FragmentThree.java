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
 * FragmentThree.java
 * @reference https://github.com/benitkibabu/mpad/tree/master/app/src/main/res/layout
 * 06/03/2016
 * @author Liam English, x14341261
 */
public class FragmentThree extends Fragment {

    /**
     * @reference https://github.com/benitkibabu/mpad/tree/master/app/src/main/res/layout
     */

    public static FragmentThree newInstance() {
        FragmentThree fragment = new FragmentThree();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_three_layout, container,false);


        ImageButton goTo = (ImageButton) view.findViewById(R.id.captains);
        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                i.putExtra("Extra", "Captains");
                getActivity().startActivity(i);
            }
        });
        ImageButton goTo2 = (ImageButton) view.findViewById(R.id.tgif);
        goTo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                i.putExtra("Extra", "Tgif");
                getActivity().startActivity(i);
            }
        });
        ImageButton goTo3 = (ImageButton) view.findViewById(R.id.counter);
        goTo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                i.putExtra("Extra", "Counter");
                getActivity().startActivity(i);
            }
        });
        ImageButton goTo4 = (ImageButton) view.findViewById(R.id.aussiebbq);
        goTo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                i.putExtra("Extra", "Aussiebbq");
                getActivity().startActivity(i);
            }
        });
        ImageButton goTo5 = (ImageButton) view.findViewById(R.id.itailian);
        goTo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                i.putExtra("Extra", "Italian");
                getActivity().startActivity(i);
            }
        });
        return view;
    }
}
