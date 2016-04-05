package com.example.liam.itp;

//author: @caoimhemalone x14447022
// Ref: https://www.youtube.com/watch?v=NHXa96-r8TY YouTube: TechAcademy


import android.Manifest;
import android.content.Context;
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
import android.widget.EditText;


// Code imported for zoom
import com.example.liam.itp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MapsActivity extends AppCompatActivity{
    //implements GoogleMap.InfoWindowAdapter{
    //implements OnMapReadyCallback {

    private GoogleMap mMap;


    // CODE TAKEN FROM TECH ACADEMY TUTORIAL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

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

    /* public void onSearch(View view) throws IOException {
        EditText location_tF = (EditText)findViewById(R.id.TFaddress);
        // gets text from search bar and turns it into a String named location
        String location = location_tF.getText().toString();
        // Define outside of try
        List<Address> addressList = null; //null initialises addressList

        if(location != null || !location.equals(""))
        {

            //Geocoder turns a street address or other description into a long lat coordinate
            Geocoder geocoder = new Geocoder(this);

            // addressList stores a list of addresses
            addressList = geocoder.getFromLocationName(location , 1);


            Address address = addressList.get(0);
            // Address stores the longitude and latitiude
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Here's what you searched for!"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


        }
// So that keyboard disappears after search
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    } */




   /*  public void onZoom(View view){
        if(view.getId() == R.id.zoomIn){
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if(view.getId()==R.id.zoomOut){
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }

    public void changeType (View view){
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            // mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            //} else if(mMap.getMapType() == GoogleMap.MAP_TYPE_TERRAIN){
            //  mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }  else {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    } */

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (mMap != null) {
                setUpMap();
            }
        }

    }

    public void setUpMap() {

        /* USE IF STATEMENTS TO SELECT MARKERS SHOWN ACCORDING TO WHAT IS SELECTED IN THE DROPDOWN MENU */

        // Marker 1



        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.3405075, -6.2628862))
                .title("Captain America's")
                .snippet("44 Grafton St, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));
        //  mMap.setInfoWindowAdapter(this);


        //Marker 2
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.3469988, -6.2563745))
                .title("O'Reillys")
                .snippet("2 Poolbeg St, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));
        //  mMap.setInfoWindowAdapter(this);


        //Marker 3
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.335940, -6.265624))
                .title("The Palace")
                .snippet("84-87 Camden Street Lower, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));
        // mMap.setInfoWindowAdapter(this);

        // Marker 4

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.3397012, -6.2634227))
                .title("Tgi Friday's")
                .snippet("St Stephen's Green, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));
        //  mMap.setInfoWindowAdapter(this);

        //Marker 5
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.340136, -6.2637727))
                .title("Sinnotts")
                .snippet("South King Street, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));
        //  mMap.setInfoWindowAdapter(this);

        //Marker 6
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.335719, -6.263619))
                .title("Dicey's Garden")
                .snippet("21-25 Harcourt St, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));
        // mMap.setInfoWindowAdapter(this);



        // Getting more information for infowindow


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



        // Loading the map up to the current location
/*
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Criteria criteria = new Criteria();

                    Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                    if (location != null)
                    {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(location.getLatitude(), location.getLongitude()), 13));

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(30)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    }



        private void setUpMapIfNeeded() {
            // Do a null check to confirm that we have not already instantiated the map.
            if (mMap == null) {
                // Try to obtain the map from the SupportMapFragment.
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                        .getMap();
                // Check if we were successful in obtaining the map.
                if (mMap != null) {
                    setUpMap();
                }
            }
        } */

        mMap.setMyLocationEnabled(true);
       //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3482961, -6.257899), 12.0f));



    }


    // ****** Add on click from this video -> https://www.youtube.com/watch?v=xglNnK6xXYY to make window clickable to load to other page

/*
    // For info window implementaion
     @Override
    public View getInfoWindow(Marker marker) {
        //return null automatically given
        // Inflate view
        // Will return everything in info_window.xml
         View view = getLayoutInflater().inflate(R.layout.info_window, null, false);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    } */
}
