package com.example.liam.itp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Whiskey.java
 * @reference https://github.com/benitkibabu/mpad/tree/master/app/src/main/res/layout
 * 06/03/2016
 * @author Liam English, x14341261
 */
public class Whiskey extends Fragment {

    /**
     * @reference https://github.com/benitkibabu/mpad/tree/master/app/src/main/res/layout
     */

    public static Whiskey newInstance() {
        Whiskey whiskey = new Whiskey();
        return whiskey;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homescreen, container,false);

        Button goTo = (Button) view.findViewById(R.id.moreDetails);
        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), FactsActivity.class);
                i.putExtra("Extra", "Whiskey");
                getActivity().startActivity(i);
            }
        });
        return view;
    }
}
