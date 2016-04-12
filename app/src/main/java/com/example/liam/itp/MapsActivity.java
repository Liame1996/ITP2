package com.example.liam.itp;

//author: @caoimhemalone x14447022
// Ref: https://www.youtube.com/watch?v=NHXa96-r8TY YouTube: TechAcademy


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


// Code imported for zoom
import com.example.liam.itp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.UiSettings;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class MapsActivity extends AppCompatActivity {
    //implements GoogleMap.InfoWindowAdapter{
    //implements OnMapReadyCallback {

    private GoogleMap mMap;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;


    HashMap<String, String> markerMap = new HashMap<String, String>();


    // *** For ToolBar ***
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // ** FOR SPINNER/DROPDOWN ON MAP **

        spinner = (Spinner) findViewById(R.id.markerDD);
        adapter = ArrayAdapter.createFromResource(this, R.array.markerType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id, Marker marker) {

                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_LONG).show();
               /* Object item = spinner.getSelectedItem();*/

             /*   String m = markerMap.get(marker.getId());
                String s = parent.getSelectedItem().toString();

                if (s.equals("All")) {
          if(m.equals("mCA")){
              marker.setVisible(true); //Captain Americas
          } else if(m.equals("mOR")) {
              marker.setVisible(true); //O'reillys
          } else if(m.equals("mOR")) {
              marker.setVisible(true); //Palace
          } else if(m.equals("mTGIF")){
                    marker.setVisible(true); //TGIF
                     } else if(m.equals("mSI")) {
                    marker.setVisible(true); //Sinnotss
                } else if(m.equals("mDG")) {
                    marker.setVisible(true); //Diceys
                } else if(m.equals("mD2")) {
                        marker.setVisible(true); // dTwo
                    } else if(m.equals("mEG")) {
                        marker.setVisible(true); //Everleigh
                    } else if(m.equals("mCFJ")) {
                        marker.setVisible(true); // Coppers
                    } else if(m.equals("mTB")) {
                        marker.setVisible(true); //Trinity Bar
                    } else if(m.equals("mLA")) {
                        marker.setVisible(true); //Lagona
                    } else if(m.equals("mTemB")) {
                        marker.setVisible(true);//Temple Bar
                    } else if(m.equals("mTC")) {
                        marker.setVisible(true); //Counter
                    } else if(m.equals("mAB")) {
                        marker.setVisible(true); //Aussie bbq
                    } else if(m.equals("mIC")) {
                        marker.setVisible(true); // Italian corner
                    }

        } else if(s.equals("Clubs")){
                    if(m.equals("mCA")){
                        marker.setVisible(false); //Captain Americas
                    } else if(m.equals("mOR")) {
                        marker.setVisible(false); //O'reillys
                    } else if(m.equals("mOR")) {
                        marker.setVisible(true); //Palace
                    } else if(m.equals("mTGIF")){
                        marker.setVisible(false); //TGIF
                    } else if(m.equals("mSI")) {
                        marker.setVisible(false); //Sinnotss
                    } else if(m.equals("mDG")) {
                        marker.setVisible(true); //Diceys
                    } else if(m.equals("mD2")) {
                        marker.setVisible(true); // dTwo
                    } else if(m.equals("mEG")) {
                        marker.setVisible(true); //Everleigh
                    } else if(m.equals("mCFJ")) {
                        marker.setVisible(true); // Coppers
                    } else if(m.equals("mTB")) {
                        marker.setVisible(false); //Trinity Bar
                    } else if(m.equals("mLA")) {
                        marker.setVisible(false); //Lagona
                    } else if(m.equals("mTemB")) {
                        marker.setVisible(false);//Temple Bar
                    } else if(m.equals("mTC")) {
                        marker.setVisible(false); //Counter
                    } else if(m.equals("mAB")) {
                        marker.setVisible(false); //Aussie bbq
                    } else if(m.equals("mIC")) {
                        marker.setVisible(false); // Italian corner
                    }
        } else if(s.equals("Bars")){
                    if(m.equals("mCA")){
                        marker.setVisible(false); //Captain Americas
                    } else if(m.equals("mOR")) {
                        marker.setVisible(true); //O'reillys
                    } else if(m.equals("mOR")) {
                        marker.setVisible(false); //Palace
                    } else if(m.equals("mTGIF")){
                        marker.setVisible(false); //TGIF
                    } else if(m.equals("mSI")) {
                        marker.setVisible(true); //Sinnotss
                    } else if(m.equals("mDG")) {
                        marker.setVisible(false); //Diceys
                    } else if(m.equals("mD2")) {
                        marker.setVisible(false); // dTwo
                    } else if(m.equals("mEG")) {
                        marker.setVisible(false); //Everleigh
                    } else if(m.equals("mCFJ")) {
                        marker.setVisible(false); // Coppers
                    } else if(m.equals("mTB")) {
                        marker.setVisible(true); //Trinity Bar
                    } else if(m.equals("mLA")) {
                        marker.setVisible(true); //Lagona
                    } else if(m.equals("mTemB")) {
                        marker.setVisible(true);//Temple Bar
                    } else if(m.equals("mTC")) {
                        marker.setVisible(false); //Counter
                    } else if(m.equals("mAB")) {
                        marker.setVisible(false); //Aussie bbq
                    } else if(m.equals("mIC")) {
                        marker.setVisible(false); // Italian corner
                    }
        } else if(s.equals("Restaurants")){
                    if(m.equals("mCA")){
                        marker.setVisible(true); //Captain Americas
                    } else if(m.equals("mOR")) {
                        marker.setVisible(false); //O'reillys
                    } else if(m.equals("mOR")) {
                        marker.setVisible(false); //Palace
                    } else if(m.equals("mTGIF")){
                        marker.setVisible(true); //TGIF
                    } else if(m.equals("mSI")) {
                        marker.setVisible(false); //Sinnotss
                    } else if(m.equals("mDG")) {
                        marker.setVisible(false); //Diceys
                    } else if(m.equals("mD2")) {
                        marker.setVisible(false); // dTwo
                    } else if(m.equals("mEG")) {
                        marker.setVisible(false); //Everleigh
                    } else if(m.equals("mCFJ")) {
                        marker.setVisible(false); // Coppers
                    } else if(m.equals("mTB")) {
                        marker.setVisible(false); //Trinity Bar
                    } else if(m.equals("mLA")) {
                        marker.setVisible(false); //Lagona
                    } else if(m.equals("mTemB")) {
                        marker.setVisible(false);//Temple Bar
                    } else if(m.equals("mTC")) {
                        marker.setVisible(true); //Counter
                    } else if(m.equals("mAB")) {
                        marker.setVisible(true); //Aussie bbq
                    } else if(m.equals("mIC")) {
                        marker.setVisible(true); // Italian corner
                    }
        } */

                }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

/*
            Spinner spinner = (Spinner) findViewById(R.id.markerDD);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.markerType, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
 */

    }
/*
    int count;
    spinner = (Spinner)findViewById(R.id.markerDD);

    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub
            spinner = parent.getItemAtPosition(position).toString();
            count = position; //this would give you the id of the selected item
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)


    }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        } */


    // *** For Toolbar ***
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_itp2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addVenueBtn) {
            Intent i = new Intent(this, AddDetailActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        if (id == R.id.homeBtn) {
            Intent i = new Intent(this, itp2.class);
            startActivity(i);
            finish();
            return true;
        }
        if (id == R.id.cocktailsBtn) {
            Intent i = new Intent(this, CocktailScreen.class);
            startActivity(i);
            finish();
            return true;
        }
        if (id == R.id.locationBtn) {
            Intent i = new Intent(this, MapsActivity.class);
            startActivity(i);
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    // *** CHANGE MAP TYPE ***
    public void changeType(View view) {
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            // mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            //} else if(mMap.getMapType() == GoogleMap.MAP_TYPE_TERRAIN){
            //  mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } else {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (mMap != null) {
                setUpMap();


                // *** Code for infowindow ***
                // Ref Youtube : https://www.youtube.com/watch?v=g7rvqxn8SLg
                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {

                        View v = getLayoutInflater().inflate(R.layout.windowlayout, null);
                        TextView tvName = (TextView) v.findViewById(R.id.tv_name);
                        TextView tvAddress = (TextView) v.findViewById(R.id.tv_address);
                        //  TextView tvHours = (TextView) v.findViewById(R.id.tv_maphours);

                        // LatLng ll = marker.getPosition();

                        tvName.setText(marker.getTitle());
                        tvAddress.setText(marker.getSnippet());
                        // tvHours.setText(marker.getSnippet());

                        return v;
                    }
                });

                // *** For a new activity to open when info window is clicked ***

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        // To access hashmap array to bring to different activities
                        String m = markerMap.get(marker.getId());

                        if (m.equals("mCA")) {
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            startActivity(i);
                        } else if (m.equals("mOR")) {
                            Intent i = new Intent(MapsActivity.this, AddDetailActivity.class);
                            startActivity(i);
                        } else if (m.equals("mTP")) {
                            Intent i = new Intent(MapsActivity.this, CocktailScreen.class);
                            startActivity(i);
                        } else if (m.equals("mTGIF")) {
                            Intent i = new Intent(MapsActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mSI")) {
                            Intent i = new Intent(MapsActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mDG")) {
                            Intent i = new Intent(MapsActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mD2")) {
                            Intent i = new Intent(MapsActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mEG")) {
                            Intent i = new Intent(MapsActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mCFJ")) {
                            Intent i = new Intent(MapsActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mTB")) {
                            Intent i = new Intent(MapsActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mLA")) {
                            Intent i = new Intent(MapsActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mTemB")) {
                            Intent i = new Intent(MapsActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mTC")) {
                            Intent i = new Intent(MapsActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mAB")) {
                            Intent i = new Intent(MapsActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mIC")) {
                            Intent i = new Intent(MapsActivity.this, itp2.class);
                            startActivity(i);
                        }

                        // Use code below if you want all infowindows to bring you to the same window
                        //  Intent intent = new Intent(MapsActivity.this, itp2.class);
                        // startActivity(intent);
                    }
                });
            }
        }

    }

    public void setUpMap() {


        String id = null;


        /* USE IF STATEMENTS TO SELECT MARKERS SHOWN ACCORDING TO WHAT IS SELECTED IN THE DROPDOWN MENU */

        // MARKER: CAPTAIN AMERICAS

        Marker mCA = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.3405075, -6.2628862))
                .title("Captain America's")
                .snippet("44 Grafton St, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));


        // Hashmap array entry
        id = mCA.getId();
        markerMap.put(id, "mCA");


        // MARKER:O'REILLYS
        Marker mOR = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.3469988, -6.2563745))
                .title("O'Reillys")
                .snippet("2 Poolbeg St, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

        // Hashmap array entry
        id = mOR.getId();
        markerMap.put(id, "mOR");


        // MARKER: THE PALACE
        Marker mTP = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.335940, -6.265624))
                .title("The Palace")
                .snippet("84-87 Camden Street Lower, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

        // Hashmap array entry
        id = mTP.getId();
        markerMap.put(id, "mTP");


        // MARKER: TGI FRIDAYS
        Marker mTGIF = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.3397012, -6.2634227))
                .title("Tgi Friday's")
                .snippet("St Stephen's Green, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));

        // Hashmap array entry
        id = mTGIF.getId();
        markerMap.put(id, "mTGIF");


        // MARKER:SINNOTTS
        Marker mSI = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.340136, -6.2637727))
                .title("Sinnotts")
                .snippet("South King Street, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

        // Hashmap array entry
        id = mSI.getId();
        markerMap.put(id, "mSI");


        // MARKER: DICEYS GARDEN
        Marker mDG = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.335719, -6.263619))
                .title("Dicey's Garden")
                .snippet("21-25 Harcourt St, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

        // Hashmap array entry
        id = mDG.getId();
        markerMap.put(id, "mDG");


        // MARKER: DTWO
        Marker mD2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.334425, -6.262743))
                .title("DTwo")
                .snippet("60 Harcourt St, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

        // Hashmap array entry
        id = mD2.getId();
        markerMap.put(id, "mD2");


        // MARKER: EVERLEIGH GARDEN'S
        Marker mEG = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.335181, -6.263543))
                .title("Everleigh Garden's")
                .snippet("33 Harcourt St, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

        // Hashmap array entry
        id = mEG.getId();
        markerMap.put(id, "mEG");


        //MARKER: COPPER FACE JACK'S
        Marker mCFJ = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.335377, -6.263599))
                .title("Copper Face Jack's")
                .snippet("29-30 Harcourt St, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

        // Hashmap array entry
        id = mCFJ.getId();
        markerMap.put(id, "mCFJ");


        // MARKER: TRINITY BAR
        Marker mTB = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.3404330, -6.263613))
                .title("Trinity Bar")
                .snippet("46-47 Dame St")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

        // Hashmap array entry
        id = mTB.getId();
        markerMap.put(id, "mTB");


        // MARKER: LAGOONA
        Marker mLA = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.349617, -6.243003))
                .title("Lagoona")
                .snippet("Unit 4, Custom House Square, Mayor St Lower, Dublin 1")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

        // Hashmap array entry
        id = mLA.getId();
        markerMap.put(id, "mLA");


        // MARKER: THE TEMPLE BAR
        Marker mTemB = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.345475, -6.264189))
                .title("The Temple Bar")
                .snippet("247-48 Temple Bar, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

        // Hashmap array entry
        id = mTemB.getId();
        markerMap.put(id, "mTemB");


        // MARKER: THE COUNTER
        Marker mTC = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.343368, -6.260031))
                .title("The Counter")
                .snippet("20 Suffolk St, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));

        // Hashmap array entry
        id = mTC.getId();
        markerMap.put(id, "mTC");


        // MARKER: AUSSIE BBQ
        Marker mAB = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.331180, -6.264750))
                .title("Aussie BBQ")
                .snippet("45 South Richmond St, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));

        // Hashmap array entry
        id = mAB.getId();
        markerMap.put(id, "mAB");

        // MARKER: THE ITALIAN CORNER RESTAURANT
        Marker mIC = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.345644, -6.264745))
                .title("The Italian Corner Restaurant")
                .snippet("23/24 Wellington Quay, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));

        // Hashmap array entry
        id = mIC.getId();
        markerMap.put(id, "mIC");

       // Object item = spinner.getSelectedItem();


        /*if (s == "All") {
           mCA.setVisible(true); //Captain Americas
           mOR.setVisible(true); //O'reillys
            mTP.setVisible(true); //Palace
            mTGIF.setVisible(true); //TGIF
            mSI.setVisible(true); //Sinnotss
            mDG.setVisible(true); //Diceys
            mD2.setVisible(true); // dTwo
            mEG.setVisible(true); //Everleigh
            mCFJ.setVisible(true); // Coppers
            mTB.setVisible(true); //Trinity Bar
            mLA.setVisible(true); //Lagona
            mTemB.setVisible(true);//Temple Bar
            mTC.setVisible(true); //Counter
            mAB.setVisible(true); //Aussie bbq
            mIC.setVisible(true); // Italian corner

        } else if(s =="Clubs"){
            mCA.setVisible(false); //Captain Americas
            mOR.setVisible(false); //O'reillys
            mTP.setVisible(true); //Palace
            mTGIF.setVisible(false); //TGIF
            mSI.setVisible(false); //Sinnotss
            mDG.setVisible(true); //Diceys
            mD2.setVisible(true); // dTwo
            mEG.setVisible(true); //Everleigh
            mCFJ.setVisible(true); // Coppers
            mTB.setVisible(false); //Trinity Bar
            mLA.setVisible(false); //Lagoona
            mTemB.setVisible(false);//Temple Bar
            mTC.setVisible(false); //Counter
            mAB.setVisible(false); //Aussie bbq
            mIC.setVisible(false); // Italian corner
        } else if(s =="Bars"){
            mCA.setVisible(false); //Captain Americas
            mOR.setVisible(true); //O'reillys
            mTP.setVisible(false); //Palace
            mTGIF.setVisible(false); //TGIF
            mSI.setVisible(true); //Sinnotss
            mDG.setVisible(false); //Diceys
            mD2.setVisible(false); // dTwo
            mEG.setVisible(false); //Everleigh
            mCFJ.setVisible(false); // Coppers
            mTB.setVisible(true); //Trinity Bar
            mLA.setVisible(true); //Lagoona
            mTemB.setVisible(true);//Temple Bar
            mTC.setVisible(false); //Counter
            mAB.setVisible(false); //Aussie bbq
            mIC.setVisible(false); // Italian corner
        } else if(s =="Restaurants"){
            mCA.setVisible(true); //Captain Americas
            mOR.setVisible(false); //O'reillys
            mTP.setVisible(false); //Palace
            mTGIF.setVisible(true); //TGIF
            mSI.setVisible(false); //Sinnotss
            mDG.setVisible(false); //Diceys
            mD2.setVisible(false); // dTwo
            mEG.setVisible(false); //Everleigh
            mCFJ.setVisible(false); // Coppers
            mTB.setVisible(false); //Trinity Bar
            mLA.setVisible(false); //Lagoona
            mTemB.setVisible(false);//Temple Bar
            mTC.setVisible(true); //Counter
            mAB.setVisible(true); //Aussie bbq
            mIC.setVisible(true); // Italian corner
        } */





            /*

            //** END OF ALL IF STATEMENT **

        } else if (spinner.getSelectedItem() == "Club") {

            // MARKER: THE PALACE
            Marker mTP = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.335940, -6.265624))
                    .title("The Palace")
                    .snippet("84-87 Camden Street Lower, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

            // Hashmap array entry
            id = mTP.getId();
            markerMap.put(id, "mTP");


            // MARKER: DICEYS GARDEN
            Marker mDG = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.335719, -6.263619))
                    .title("Dicey's Garden")
                    .snippet("21-25 Harcourt St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

            // Hashmap array entry
            id = mDG.getId();
            markerMap.put(id, "mDG");


            // MARKER: DTWO
            Marker mD2 = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.334425, -6.262743))
                    .title("DTwo")
                    .snippet("60 Harcourt St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

            // Hashmap array entry
            id = mD2.getId();
            markerMap.put(id, "mD2");


            // MARKER: EVERLEIGH GARDEN'S
            Marker mEG = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.335181, -6.263543))
                    .title("Everleigh Garden's")
                    .snippet("33 Harcourt St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

            // Hashmap array entry
            id = mEG.getId();
            markerMap.put(id, "mEG");


            //MARKER: COPPER FACE JACK'S
            Marker mCFJ = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.335377, -6.263599))
                    .title("Copper Face Jack's")
                    .snippet("29-30 Harcourt St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

            // Hashmap array entry
            id = mCFJ.getId();
            markerMap.put(id, "mCFJ");

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }


            // *** Loading the map up to the current location ***

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), 15));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))     // Sets the center of the map to location user
                        .zoom(15)                   // Sets the zoom
                                //  .bearing(30)                // Sets the orientation of the camera to east
                        .tilt(10)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }


            mMap.setMyLocationEnabled(true);
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3482961, -6.257899), 12.0f));


            // *** FOR CONTROLS ON THE MAP ITSELF ***
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled(true);
            settings.setRotateGesturesEnabled(true);
            settings.setScrollGesturesEnabled(true);
            settings.setTiltGesturesEnabled(true);
            settings.setZoomControlsEnabled(true);
            settings.setZoomGesturesEnabled(true);
            settings.setMapToolbarEnabled(true);


            /*/
        //* END OF ELSE IF CLUB STATEMENT

       /* } else if (spinner.getSelectedItem() == "Bars/Pubs") {

            // MARKER:O'REILLYS
            Marker mOR = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.3469988, -6.2563745))
                    .title("O'Reillys")
                    .snippet("2 Poolbeg St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

            // Hashmap array entry
            id = mOR.getId();
            markerMap.put(id, "mOR");

            // MARKER:SINNOTTS
            Marker mSI = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.340136, -6.2637727))
                    .title("Sinnotts")
                    .snippet("South King Street, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

            // Hashmap array entry
            id = mSI.getId();
            markerMap.put(id, "mSI");

            // MARKER: TRINITY BAR
            Marker mTB = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.3404330, -6.263613))
                    .title("Trinity Bar")
                    .snippet("46-47 Dame St")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

            // Hashmap array entry
            id = mTB.getId();
            markerMap.put(id, "mTB");


            // MARKER: LAGOONA
            Marker mLA = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.349617, -6.243003))
                    .title("Lagoona")
                    .snippet("Unit 4, Custom House Square, Mayor St Lower, Dublin 1")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

            // Hashmap array entry
            id = mLA.getId();
            markerMap.put(id, "mLA");


            // MARKER: THE TEMPLE BAR
            Marker mTemB = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.345475, -6.264189))
                    .title("The Temple Bar")
                    .snippet("247-48 Temple Bar, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

            // Hashmap array entry
            id = mTemB.getId();
            markerMap.put(id, "mTemB");

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }


            // *** Loading the map up to the current location ***

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), 15));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))     // Sets the center of the map to location user
                        .zoom(15)                   // Sets the zoom
                                //  .bearing(30)                // Sets the orientation of the camera to east
                        .tilt(10)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }


            mMap.setMyLocationEnabled(true);
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3482961, -6.257899), 12.0f));


            // *** FOR CONTROLS ON THE MAP ITSELF ***
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled(true);
            settings.setRotateGesturesEnabled(true);
            settings.setScrollGesturesEnabled(true);
            settings.setTiltGesturesEnabled(true);
            settings.setZoomControlsEnabled(true);
            settings.setZoomGesturesEnabled(true);
            settings.setMapToolbarEnabled(true);


            /*
            //** END OF IF ELSE BAR/PUBS STATEMENT**



        } else if (spinner.getSelectedItem() == "Restaurant") {

            // MARKER: CAPTAIN AMERICAS

            Marker mCA = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.3405075, -6.2628862))
                    .title("Captain America's")
                    .snippet("44 Grafton St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));


            // Hashmap array entry
            id = mCA.getId();
            markerMap.put(id, "mCA");


            // MARKER: TGI FRIDAYS
            Marker mTGIF = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.3397012, -6.2634227))
                    .title("Tgi Friday's")
                    .snippet("St Stephen's Green, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));

            // Hashmap array entry
            id = mTGIF.getId();
            markerMap.put(id, "mTGIF");


            // MARKER: THE COUNTER
            Marker mTC = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.343368, -6.260031))
                    .title("The Counter")
                    .snippet("20 Suffolk St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));

            // Hashmap array entry
            id = mTC.getId();
            markerMap.put(id, "mTC");


            // MARKER: AUSSIE BBQ
            Marker mAB = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.331180, -6.264750))
                    .title("Aussie BBQ")
                    .snippet("45 South Richmond St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));

            // Hashmap array entry
            id = mAB.getId();
            markerMap.put(id, "mAB");

            // MARKER: THE ITALIAN CORNER RESTAURANT
            Marker mIC = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.345644, -6.264745))
                    .title("The Italian Corner Restaurant")
                    .snippet("23/24 Wellington Quay, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));

            // Hashmap array entry
            id = mIC.getId();
            markerMap.put(id, "mIC");

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }


            // *** Loading the map up to the current location ***

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), 15));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))     // Sets the center of the map to location user
                        .zoom(15)                   // Sets the zoom
                                //  .bearing(30)                // Sets the orientation of the camera to east
                        .tilt(10)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }


            mMap.setMyLocationEnabled(true);
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3482961, -6.257899), 12.0f));


            // *** FOR CONTROLS ON THE MAP ITSELF ***
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled(true);
            settings.setRotateGesturesEnabled(true);
            settings.setScrollGesturesEnabled(true);
            settings.setTiltGesturesEnabled(true);
            settings.setZoomControlsEnabled(true);
            settings.setZoomGesturesEnabled(true);
            settings.setMapToolbarEnabled(true);

            /*
            //** OF ELSE RESTAURANT STATEMENT **


        }*/
        //}

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        // *** Loading the map up to the current location ***

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 15));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))     // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                            //  .bearing(30)                // Sets the orientation of the camera to east
                    .tilt(10)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }


        mMap.setMyLocationEnabled(true);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3482961, -6.257899), 12.0f));


        // *** FOR CONTROLS ON THE MAP ITSELF ***
        UiSettings settings = mMap.getUiSettings();
        settings.setCompassEnabled(true);
        settings.setRotateGesturesEnabled(true);
        settings.setScrollGesturesEnabled(true);
        settings.setTiltGesturesEnabled(true);
        settings.setZoomControlsEnabled(true);
        settings.setZoomGesturesEnabled(true);
        settings.setMapToolbarEnabled(true);
    }


    }





