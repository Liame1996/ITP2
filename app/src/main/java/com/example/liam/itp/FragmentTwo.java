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
public class FragmentTwo extends Fragment{

    /**
     * @reference https://github.com/benitkibabu/mpad/tree/master/app/src/main/res/layout
     */

    public static FragmentTwo newInstance() {
        FragmentTwo fragment = new FragmentTwo();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_two_layout, container,false);


        ImageButton goTo = (ImageButton) view.findViewById(R.id.sinnotts);
        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                i.putExtra("Extra", "Sinnotts");
                getActivity().startActivity(i);
            }
        });
        ImageButton goTo2 = (ImageButton) view.findViewById(R.id.oreillys);
        goTo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                i.putExtra("Extra", "Oreillys");
                getActivity().startActivity(i);
            }
        });
        ImageButton goTo3 = (ImageButton) view.findViewById(R.id.trinity);
        goTo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                i.putExtra("Extra", "Trinity");
                getActivity().startActivity(i);
            }
        });
        ImageButton goTo4 = (ImageButton) view.findViewById(R.id.lagoona);
        goTo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                i.putExtra("Extra", "Lagoona");
                getActivity().startActivity(i);
            }
        });
        ImageButton goTo5 = (ImageButton) view.findViewById(R.id.temple);
        goTo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), DisplayActivity.class);
                i.putExtra("Extra", "Temple");
                getActivity().startActivity(i);
            }
        });
        return view;
    }

}
