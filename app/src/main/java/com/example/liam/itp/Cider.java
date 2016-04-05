package com.example.liam.itp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Liam on 31/03/2016.
 */
public class Cider extends Fragment {

    public static Cider newInstance() {
        Cider cider = new Cider();
        return cider;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cider, container,false);

        Button goTo = (Button) view.findViewById(R.id.moreDetails);
        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(), FactsActivity.class);
                i.putExtra("Extra", "Cider");
                getActivity().startActivity(i);
            }
        });
        return view;    }
}
