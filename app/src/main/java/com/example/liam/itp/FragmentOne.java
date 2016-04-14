package com.example.liam.itp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * @reference https://github.com/benitkibabu/mpad/tree/master/app/src/main/res/layout
 * Created by Liam English, x14341261, on 06/03/2016.
 */
public class FragmentOne extends Fragment {

    /**
     * @reference https://github.com/benitkibabu/mpad/tree/master/app/src/main/res/layout
     */

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
                    i.putExtra("Extra", "Diceys");
                    getActivity().startActivity(i);
                }
            });
            ImageButton goTo2 = (ImageButton) view.findViewById(R.id.palace);
            goTo2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent i = new Intent(getActivity(), DisplayActivity.class);
                    i.putExtra("Extra", "Palace");
                    getActivity().startActivity(i);
                }
            });
            ImageButton goTo3 = (ImageButton) view.findViewById(R.id.dtwo);
            goTo3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent i = new Intent(getActivity(), DisplayActivity.class);
                    i.putExtra("Extra", "Dtwo");
                    getActivity().startActivity(i);
                }
            });
            ImageButton goTo4 = (ImageButton) view.findViewById(R.id.everleigh);
            goTo4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent i = new Intent(getActivity(), DisplayActivity.class);
                    i.putExtra("Extra", "Everleigh");
                    getActivity().startActivity(i);
                }
            });
            ImageButton goTo5 = (ImageButton) view.findViewById(R.id.coppers);
            goTo5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent i = new Intent(getActivity(), DisplayActivity.class);
                    i.putExtra("Extra", "Coppers");
                    getActivity().startActivity(i);
                }
            });

        return view;
    }


}
