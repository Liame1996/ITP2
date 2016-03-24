package com.example.liam.itp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ITP extends FragmentActivity {

    ViewPager viewpager;


    private HorizontalScrollView mHS;
    private LinearLayout mLL;
    private ImageButton homeBtn, addVenueBtn, locationBtn, cocktailsBtn;

    private View activity;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itp);
        viewpager = (ViewPager) findViewById(R.id.pager); //casting ViewPager
        PagerAdapter padapter = new PagerAdaptor(getSupportFragmentManager());
            viewpager.setAdapter(padapter);

        addVenueBtn = (ImageButton) findViewById(R.id.addVenueBtn);
        homeBtn = (ImageButton) findViewById(R.id.homeBtn);

//        homeBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(ITP.this, ITP.class);
//                startActivity(i);
//                finish();
//            }
//        });
//
//       addVenueBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(ITP.this, AddDetailActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_it, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public View getActivity() {
        return activity;
    }


}
