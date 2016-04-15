package com.example.liam.itp;

/**
 * @author: caoimhemalone x14447022
 * @reference https://www.youtube.com/watch?v=NHXa96-r8TY YouTube: TechAcademy
 * @reference https://www.youtube.com/watch?v=92f4c2vHrPg YouTube: PRABEESH R K
 * Used to create map
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.UiSettings;
import java.util.HashMap;



public class MapsActivity extends AppCompatActivity{
    private GoogleMap mMap;
        Spinner spinner;
        ArrayAdapter<CharSequence> adapter;

        DisplayActivity dact = new DisplayActivity();

    HashMap<String, String> markerMap = new HashMap<String, String>();

    // *** For ToolBar ***
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


            /**
             * @reference https://www.youtube.com/watch?v=28jA5-mO8K8&index=8&list=LL9QnUxf2Pctj2wyWa4GABCw YouTube: PRABEESH R K
             */

            // ** FOR SPINNER/DROPDOWN ON MAP **

            spinner = (Spinner)findViewById(R.id.markerDD);
            adapter = ArrayAdapter.createFromResource(this,R.array.markerType, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+" selected", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
    }

    // *** For Toolbar ***
        @Override
     public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_itp2, menu);
        return true;
    }
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
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
            Intent i = new Intent(this, Home.class);
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
        protected void onResume () {
        super.onResume();
        setUpMapIfNeeded();
    }

    // *** CHANGE MAP TYPE ***
    /**
     * @reference https://www.youtube.com/watch?v=J3R4b-KauuI YouTube: Tech Academy
     */

    public void changeType (View view){
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }  else {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (mMap != null) {
                setUpMap();


                // *** Code for infowindow ***
                /**
                 * @reference https://www.youtube.com/watch?v=g7rvqxn8SLg YouTube: zat mit
                 */

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
                        tvName.setText(marker.getTitle());
                        tvAddress.setText(marker.getSnippet());
                        return v;
                    }
                });

                // *** For a new activity to open when info window is clicked ***

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        // To access hashmap array to bring to different activities
                        String m = markerMap.get(marker.getId());

                        if(m.equals("mCA")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Captains");
                            startActivity(i);
                        } else if(m.equals("mOR")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Oeillys");
                            startActivity(i);
                        } else if(m.equals("mTP")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Palace");
                            startActivity(i);
                        } else if(m.equals("mTGIF")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Tgif");
                            startActivity(i);
                        } else if(m.equals("mSI")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Sinnotts");
                            startActivity(i);
                        } else if(m.equals("mDG")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Diceys");
                            startActivity(i);
                        } else if(m.equals("mD2")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Dtwo");
                            startActivity(i);
                        } else if(m.equals("mEG")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Everleigh");
                            startActivity(i);
                        } else if(m.equals("mCFJ")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Coppers");
                            startActivity(i);
                        } else if(m.equals("mTB")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Trinity");
                            startActivity(i);
                        } else if(m.equals("mLA")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Lagoona");
                            startActivity(i);
                        } else if(m.equals("mTemB")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Temple");
                            startActivity(i);
                        } else if(m.equals("mTC")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Counter");
                            startActivity(i);
                        } else if(m.equals("mAB")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Aussiebbq");
                            startActivity(i);
                        } else if(m.equals("mIC")){
                            Intent i = new Intent(MapsActivity.this, DisplayActivity.class);
                            i.putExtra("Extra", "Italian");
                            startActivity(i);
                        }
                    }
                });
            }
        }
    }

    public void setUpMap() {


        String id = null;

        /**
         * @reference https://www.youtube.com/watch?v=brOT34K57bo YouTube: zat mit
         */
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
      Marker mCFJ =  mMap.addMarker(new MarkerOptions()
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
        Marker mTemB =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.345475, -6.264189))
                .title("The Temple Bar")
                .snippet("247-48 Temple Bar, Dublin 2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

        // Hashmap array entry
        id = mTemB.getId();
        markerMap.put(id, "mTemB");


        // MARKER: THE COUNTER
        Marker mTC =mMap.addMarker(new MarkerOptions()
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
        /**
         * @reference http://javapapers.com/android/android-show-current-location-on-map-using-google-maps-api/
         */

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

        // *** FOR CONTROLS ON THE MAP ITSELF ***
        /**
         * @reference https://developers.google.com/maps/documentation/android-api/controls#ui_controls
         */
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




