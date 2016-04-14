package com.example.liam.itp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @reference https://github.com/benitkibabu/mpad/tree/master/app/src/main/res/layout
 * Created by Liam English, x14341261, on 06/03/2016.
 */
public class Vodka extends Fragment {

    /**
     * @reference https://github.com/benitkibabu/mpad/tree/master/app/src/main/res/layout
     */

    public static Vodka newInstance() {
        Vodka vodka = new Vodka();
        return vodka;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vodka, container,false);

        Button goTo = (Button) view.findViewById(R.id.moreDetails);
        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), FactsActivity.class);
                i.putExtra("Extra", "Vodka");
                getActivity().startActivity(i);
            }
        });
        return view;    }
}
