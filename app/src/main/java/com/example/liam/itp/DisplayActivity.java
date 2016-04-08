package com.example.liam.itp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liam.itp.DBHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class DisplayActivity extends AppCompatActivity {

    private TextView nameV, typeV, addressV, emailV, phoneV, tvDay, tvHour, tvMinute, tvSecond, countTV;
    private Button prevButton, nextButton, addButton;
    private LinearLayout countdownLayout;
    private Handler handler;
    private Runnable runnable;
    private ImageButton homeBtn, addVenueBtn, locationBtn, cocktailsBtn;

    private ResultSet details = null;

    private GoogleMap mMap;
    HashMap<String, String> markerMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameV = (TextView)findViewById(R.id.nameID);
        typeV = (TextView)findViewById(R.id.typeID);
        addressV = (TextView)findViewById(R.id.addressID);
        emailV = (TextView)findViewById(R.id.emailID);
        phoneV = (TextView)findViewById(R.id.phoneID);
        countTV = (TextView)findViewById(R.id.countTV);

        countTV.setText("00:00:00");
        //final countDown timerDiceys = new countDown(180000, 1000);
        //final countDown timerPalace = new countDown(18000, 1000);

        //timerDiceys.start();
        //timerPalace.start();



//        prevButton = (Button)findViewById(R.id.prevBtn);
//        nextButton = (Button)findViewById(R.id.nextBtn);
//        addButton = (Button)findViewById(R.id.addBtn);



        if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Diceys")){
            new getDiceysDetails().execute();
            diceysTimer();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Palace")){
            new getPalaceDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Dtwo")){
            new getDtwoDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Everleigh")){
            new getEverleighDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Coppers")){
            new getCoppersDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Sinnotts")){
            new getSinnottsDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Oreillys")){
            new getOreillysDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Trinity")){
            new getTrinityDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Lagoona")){
            new getLagoonaDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Temple")){
            new getTempleDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Captains")){
            new getCaptainsDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Tgif")){
            new getTgifDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Counter")){
            new getCounterDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Aussiebbq")){
            new getAussiebbqDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Italian")){
            new getItalianDetails().execute();
        }


        initUI();
        countDownStart();



//        prevButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    if (details != null && details.previous()) {
//                        display(details.getString("C_NAME"), details.getString("CATEG"), details.getString("ADDRESS"), details.getString("EMAIL"), details.getString("PHONE") + "");
//                    }
//                } catch (SQLException s) {
//                    Log.e("", s.getMessage());
//                }
//            }
//        });
//
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    if (details != null && details.next()) {
//                        display(details.getString("C_NAME"), details.getString("CATEG"),details.getString("ADDRESS"),details.getString("EMAIL"),details.getString("PHONE") +"");
//                    }
//                }
//                catch(SQLException s){
//                    Log.e("", s.getMessage());
//                }
//            }
//        });
//
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(DisplayActivity.this, AddDetailActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });


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

    private void ShowMessage(String msg){
        Toast.makeText(DisplayActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    public void display(String name, String type, String address, String email, String phone){
        nameV.setText(name);
        typeV.setText(type);
        addressV.setText(address);
        emailV.setText(email);
        phoneV.setText(phone);
    }

    @SuppressLint("SimpleDateFormat")
    private void initUI() {
        countdownLayout = (LinearLayout) findViewById(R.id.innerCountdownLayout);
        tvDay = (TextView) findViewById(R.id.txtTimerDay);
        tvHour = (TextView) findViewById(R.id.txtTimerHour);
        tvMinute = (TextView) findViewById(R.id.txtTimerMinute);
        tvSecond = (TextView) findViewById(R.id.txtTimerSecond);
    }

    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "ss-mm-hh-dd-MM-yyyy");
                    // Here Set your Event Date
                    Date futureDate = dateFormat.parse("00-00-03-12-03-2016");
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        tvDay.setText("" + String.format("%02d", days));
                        tvHour.setText("" + String.format("%02d", hours));
                        tvMinute.setText("" + String.format("%02d", minutes));
                        tvSecond.setText("" + String.format("%02d", seconds));
                    } else {
                        countdownLayout.setVisibility(View.VISIBLE);
                        handler.removeCallbacks(runnable);
                        // handler.removeMessages(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    public class countDown extends CountDownTimer{
        public countDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            countTV.setText(hms);

        }

        @Override
        public void onFinish() {
            countTV.setText("00:00:00");
        }
    }

    public void diceysTimer(){
        Calendar cal = Calendar.getInstance();

        final countDown timerDiceys = new countDown(37800000, 1000);
        if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY && cal.get(Calendar.HOUR_OF_DAY)>=16) {
            timerDiceys.start();
        }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY && cal.get(Calendar.HOUR_OF_DAY)>=16) {
            timerDiceys.start();
        }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY && cal.get(Calendar.HOUR_OF_DAY)>=16) {
            timerDiceys.start();
        }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY && cal.get(Calendar.HOUR_OF_DAY)>=16) {
            timerDiceys.start();
        }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY && cal.get(Calendar.HOUR_OF_DAY)>=16) {
            timerDiceys.start();
        }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY && cal.get(Calendar.HOUR_OF_DAY)>=16) {
            timerDiceys.start();
        }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY && cal.get(Calendar.HOUR_OF_DAY)>=16) {
            timerDiceys.start();
        }
    }



        //DICEYS DETAILS
        public class getDiceysDetails extends AsyncTask<Void, Void, Void> {
            private ProgressDialog pDialog;
            private GoogleMap mMap;


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(DisplayActivity.this);
                pDialog.setCancelable(false);
                pDialog.setMessage("Getting details...");
                showDialog();
            }

            @Override
            protected Void doInBackground(Void... params) {
                DBHelper db = new DBHelper();
                details = db.getDiceysDetails();
                return null;
            }

            @Override
            protected void onPostExecute(Void r) {
               setUpMapIfNeeded();
                hideDialog();
                try {
                    if (details != null && details.next()) {
                        display(details.getString("C_NAME"), details.getString("CATEG"), details.getString("ADDRESS"), details.getString("EMAIL"), details.getString("PHONE") + "");
                    } else {
                        ShowMessage("No details found");
                    }
                } catch (SQLException s) {
                    Log.e("Erro", s.getMessage());
                }

            }

            private void setUpMapIfNeeded() {
                if (mMap == null) {
                    mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                    if (mMap != null) {
                        setUpMap();

                        // *** Code for infowindow *** -CMalone
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
                        // *** For a new activity to open when info window is clicked *** -CMalone
                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                // When the user clicks the InfoWindow it brings them to the MapsActivity
                                Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
            public void setUpMap() {

                String id = null;

                // MARKER: DICEYS GARDEN -CMalone
                Marker mDG = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(53.335719, -6.263619))
                        .title("Dicey's Garden")
                        .snippet("21-25 Harcourt St, Dublin 2")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

                // Hashmap array entry
                id = mDG.getId();
                markerMap.put(id, "mDG");

                // FOR CONTROLS ON THE MAP ITSELF -CMalone
                UiSettings settings = mMap.getUiSettings();
                settings.setCompassEnabled( true );
                settings.setRotateGesturesEnabled( true );
                settings.setScrollGesturesEnabled( true );
                settings.setTiltGesturesEnabled( true );
                settings.setZoomControlsEnabled( true);
                settings.setZoomGesturesEnabled( true );
                settings.setMapToolbarEnabled(true);

                // Set camera to load up on diceys -CMalone
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.335719, -6.263619), 15.0f));

            }
            private void showDialog() {
                if (!pDialog.isShowing()) {
                    pDialog.show();
                }
            }

            private void hideDialog() {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
            }
        }

    //PALACE DETAILS
    public class getPalaceDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(DisplayActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getPalaceDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r) {
            setUpMapIfNeeded();
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("C_NAME"), details.getString("CATEG"), details.getString("ADDRESS"), details.getString("EMAIL"), details.getString("PHONE") + "");
                } else {
                    ShowMessage("No details found");
                }
            } catch (SQLException s) {
                Log.e("Erro", s.getMessage());
            }
        }

        private void showDialog() {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hideDialog() {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }

        // ******** FOR MAP ************ - CMalone
        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                if (mMap != null) {
                    setUpMap();

                    // *** Code for infowindow *** -CMalone
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
                    // *** For a new activity to open when info window is clicked ***-CMalone
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            // When the user clicks the InfoWindow it brings them to the MapsActivity -CMalone
                            Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }

        public void setUpMap() {

            String id = null;
            // MARKER: The Palace -CMalone
            Marker mTP = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.335940, -6.265624))
                    .title("The Palace")
                    .snippet("84-87 Camden Street Lower, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

            // Hashmap array entry -CMalone
            id = mTP.getId();
            markerMap.put(id, "mTP");

            // FOR CONTROLS ON THE MAP ITSELF -CMalone
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled( true );
            settings.setRotateGesturesEnabled( true );
            settings.setScrollGesturesEnabled( true );
            settings.setTiltGesturesEnabled( true );
            settings.setZoomControlsEnabled( true);
            settings.setZoomGesturesEnabled( true );
            settings.setMapToolbarEnabled(true);

            // Set camera to load up on the palace -CMalone
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.335940, -6.265624), 15.0f));
        }
    }

    //EVERLEIGH DETAILS
    public class getEverleighDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(DisplayActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getEverleighDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r) {
            setUpMapIfNeeded();
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("C_NAME"), details.getString("CATEG"), details.getString("ADDRESS"), details.getString("EMAIL"), details.getString("PHONE") + "");
                } else {
                    ShowMessage("No details found");
                }
            } catch (SQLException s) {
                Log.e("Erro", s.getMessage());
            }
        }

        private void showDialog() {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hideDialog() {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }

        // ******** FOR MAP ************ - CMalone
        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                if (mMap != null) {
                    setUpMap();

                    // *** Code for infowindow *** -CMalone
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
                    // *** For a new activity to open when info window is clicked *** -CMalone
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            // When the user clicks the InfoWindow it brings them to the MapsActivity -CMalone
                            Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }

        public void setUpMap() {

            String id = null;
            // MARKER: EVERLEIGH GARDEN'S -CMalone
            Marker mEG = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.335181, -6.263543))
                    .title("Everleigh Garden's")
                    .snippet("33 Harcourt St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

            // Hashmap array entry -CMalone
            id = mEG.getId();
            markerMap.put(id, "mEG");

            // FOR CONTROLS ON THE MAP ITSELF -CMalone
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled( true );
            settings.setRotateGesturesEnabled( true );
            settings.setScrollGesturesEnabled( true );
            settings.setTiltGesturesEnabled( true );
            settings.setZoomControlsEnabled( true);
            settings.setZoomGesturesEnabled( true );
            settings.setMapToolbarEnabled(true);

            // Set camera to load up on Everleigh Gardens  -CMalone
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.335181, -6.263543), 15.0f));
        }

    }

    //DTWO DETAILS
    public class getDtwoDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(DisplayActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getDtwoDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r) {
            setUpMapIfNeeded();
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("C_NAME"), details.getString("CATEG"), details.getString("ADDRESS"), details.getString("EMAIL"), details.getString("PHONE") + "");
                } else {
                    ShowMessage("No details found");
                }
            } catch (SQLException s) {
                Log.e("Erro", s.getMessage());
            }
        }

        private void showDialog() {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hideDialog() {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }


        // ******** FOR MAP ************ - CMalone

        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                if (mMap != null) {
                    setUpMap();

                    // *** Code for infowindow *** -CMalone
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
                    // *** For a new activity to open when info window is clicked *** -CMalone
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            // When the user clicks the InfoWindow it brings them to thre MapsActivity -CMalone
                            Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
        public void setUpMap() {

            String id = null;
            // MARKER: DTWO -CMalone
            Marker mD2 = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.334425, -6.262743))
                    .title("DTwo")
                    .snippet("60 Harcourt St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

            // Hashmap array entry -CMalone
            id = mD2.getId();
            markerMap.put(id, "mD2");

            // FOR CONTROLS ON THE MAP ITSELF -CMalone
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled( true );
            settings.setRotateGesturesEnabled( true );
            settings.setScrollGesturesEnabled( true );
            settings.setTiltGesturesEnabled( true );
            settings.setZoomControlsEnabled( true);
            settings.setZoomGesturesEnabled( true );
            settings.setMapToolbarEnabled(true);

            // Set camera to load up on the D2 -CMalone
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.334425, -6.262743), 15.0f));
        }
    }

    //COPPERS DETAILS
    public class getCoppersDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(DisplayActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getCoppersDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r) {
            setUpMapIfNeeded();
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("C_NAME"), details.getString("CATEG"), details.getString("ADDRESS"), details.getString("EMAIL"), details.getString("PHONE") + "");
                } else {
                    ShowMessage("No details found");
                }
            } catch (SQLException s) {
                Log.e("Erro", s.getMessage());
            }
        }

        private void showDialog() {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hideDialog() {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }

        // ******** FOR MAP ************ - CMalone

        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                if (mMap != null) {
                    setUpMap();

                    // *** Code for infowindow *** -CMalone
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
                    // *** For a new activity to open when info window is clicked *** -CMalone
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            // When the user clicks the InfoWindow it brings them to the MapsActivity -CMalone
                            Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }

        public void setUpMap() {

            String id = null;
            //MARKER: COPPER FACE JACK'S -CMalone
            Marker mCFJ =  mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.335377, -6.263599))
                    .title("Copper Face Jack's")
                    .snippet("29-30 Harcourt St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_cocktail)));

            // Hashmap array entry -CMalone
            id = mCFJ.getId();
            markerMap.put(id, "mCFJ");

            // FOR CONTROLS ON THE MAP ITSELF -CMalone
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled( true );
            settings.setRotateGesturesEnabled( true );
            settings.setScrollGesturesEnabled( true );
            settings.setTiltGesturesEnabled( true );
            settings.setZoomControlsEnabled( true);
            settings.setZoomGesturesEnabled( true );
            settings.setMapToolbarEnabled(true);

            // Set camera to load up on Copper Face Jacks -CMalone
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.335377, -6.263599), 15.0f));
        }
    }



    //SINNOTTS DETAILS
    private class getSinnottsDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            pDialog = new ProgressDialog(DisplayActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getSinnottsDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            setUpMapIfNeeded();
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("B_NAME"), details.getString("CATEG"),details.getString("ADDRESS"),details.getString("EMAIL"),details.getString("PHONE") +"");
                }
                else{
                    ShowMessage("No details found");
                }
            }
            catch(SQLException s){
                Log.e("Erro", s.getMessage());
            }
        }

        private void showDialog() {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hideDialog(){
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
        }

        // ******** FOR MAP ************ - CMalone

        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                if (mMap != null) {
                    setUpMap();

                    // *** Code for infowindow *** -CMalone
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
                    // *** For a new activity to open when info window is clicked *** -CMalone
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            // When the user clicks the InfoWindow it brings them to the MapsActivity
                            Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
        public void setUpMap() {

            String id = null;
            // MARKER:SINNOTTS -CMalone
            Marker mSI = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.340136, -6.2637727))
                    .title("Sinnotts")
                    .snippet("South King Street, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

            // Hashmap array entry -CMalone
            id = mSI.getId();
            markerMap.put(id, "mSI");

            // FOR CONTROLS ON THE MAP ITSELF -CMalone
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled( true );
            settings.setRotateGesturesEnabled( true );
            settings.setScrollGesturesEnabled( true );
            settings.setTiltGesturesEnabled( true );
            settings.setZoomControlsEnabled( true);
            settings.setZoomGesturesEnabled( true );
            settings.setMapToolbarEnabled(true);

            // Set camera to load up on Sinnotts -CMalone
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.340136, -6.2637727), 15.0f));
        }
    }

    //OREILLYS DETAILS
    private class getOreillysDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            pDialog = new ProgressDialog(DisplayActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getOreillysDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            setUpMapIfNeeded();
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("B_NAME"), details.getString("CATEG"),details.getString("ADDRESS"),details.getString("EMAIL"),details.getString("PHONE") +"");
                }
                else{
                    ShowMessage("No details found");
                }
            }
            catch(SQLException s){
                Log.e("Erro", s.getMessage());
            }
        }

        private void showDialog() {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hideDialog(){
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
        }

        // ******** FOR MAP ************ - CMalone

        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                if (mMap != null) {
                    setUpMap();

                    // *** Code for infowindow *** -CMalone
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

                    // *** For a new activity to open when info window is clicked *** -CMalone
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            // When the user clicks the InfoWindow it brings them to the MapsActivity -CMalone
                            Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }

        public void setUpMap() {

            String id = null;
            // MARKER:O'REILLYS -CMalone
            Marker mOR = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.3469988, -6.2563745))
                    .title("O'Reillys")
                    .snippet("2 Poolbeg St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

            // Hashmap array entry -CMalone
            id = mOR.getId();
            markerMap.put(id, "mOR");

            // FOR CONTROLS ON THE MAP ITSELF -CMalone
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled( true );
            settings.setRotateGesturesEnabled( true );
            settings.setScrollGesturesEnabled( true );
            settings.setTiltGesturesEnabled( true );
            settings.setZoomControlsEnabled( true);
            settings.setZoomGesturesEnabled( true );
            settings.setMapToolbarEnabled(true);

            // Set camera to load up on O'Reillys -CMalone
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3469988, -6.2563745), 15.0f));
        }
    }

    //TRINITY BAR DETAILS
    private class getTrinityDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            pDialog = new ProgressDialog(DisplayActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getTrinityDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            setUpMapIfNeeded();
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("B_NAME"), details.getString("CATEG"),details.getString("ADDRESS"),details.getString("EMAIL"),details.getString("PHONE") +"");
                }
                else{
                    ShowMessage("No details found");
                }
            }
            catch(SQLException s){
                Log.e("Erro", s.getMessage());
            }
        }

        private void showDialog() {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hideDialog(){
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
        }

        // ******** FOR MAP ************ - CMalone

        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                if (mMap != null) {
                    setUpMap();

                    // *** Code for infowindow *** -CMalone
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

                    // *** For a new activity to open when info window is clicked *** -CMalone
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            // When the user clicks the InfoWindow it brings them to MapsActivity -CMalone
                            Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
        public void setUpMap() {

            String id = null;
            // MARKER: TRINITY BAR -CMalone
            Marker mTB = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.3404330, -6.263613))
                    .title("Trinity Bar")
                    .snippet("46-47 Dame St")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

            // Hashmap array entry -CMalone
            id = mTB.getId();
            markerMap.put(id, "mTB");

            // FOR CONTROLS ON THE MAP ITSELF -CMalone
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled( true );
            settings.setRotateGesturesEnabled( true );
            settings.setScrollGesturesEnabled( true );
            settings.setTiltGesturesEnabled( true );
            settings.setZoomControlsEnabled( true);
            settings.setZoomGesturesEnabled( true );
            settings.setMapToolbarEnabled(true);

            // Set camera to load up on Trinity Bar -CMalone
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3404330, -6.263613), 15.0f));
        }
    }

    //LAGOONA DETAILS
    private class getLagoonaDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            pDialog = new ProgressDialog(DisplayActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getLagoonaDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            setUpMapIfNeeded();
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("B_NAME"), details.getString("CATEG"),details.getString("ADDRESS"),details.getString("EMAIL"),details.getString("PHONE") +"");
                }
                else{
                    ShowMessage("No details found");
                }
            }
            catch(SQLException s){
                Log.e("Erro", s.getMessage());
            }
        }

        private void showDialog() {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hideDialog(){
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
        }
        // ******** FOR MAP ************ - CMalone

        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                if (mMap != null) {
                    setUpMap();

                    // *** Code for infowindow *** -CMalone
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

                    // *** For a new activity to open when info window is clicked *** -CMalone
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            // When the user clicks the InfoWindow it brings them to the MapsActivity -CMalone
                            Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
        public void setUpMap() {

            String id = null;
            // MARKER: LAGOONA -CMalone
            Marker mLA = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.349617, -6.243003))
                    .title("Lagoona")
                    .snippet("Unit 4, Custom House Square, Mayor St Lower, Dublin 1")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

            // Hashmap array entry -CMalone
            id = mLA.getId();
            markerMap.put(id, "mLA");

            // FOR CONTROLS ON THE MAP ITSELF -CMalone
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled( true );
            settings.setRotateGesturesEnabled( true );
            settings.setScrollGesturesEnabled( true );
            settings.setTiltGesturesEnabled( true );
            settings.setZoomControlsEnabled( true);
            settings.setZoomGesturesEnabled( true );
            settings.setMapToolbarEnabled(true);

            // Set camera to load up on Lagoona -CMalone
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.349617, -6.243003), 15.0f));
        }
    }

    //TEMPLE BAR DETAILS
    private class getTempleDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            pDialog = new ProgressDialog(DisplayActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getTempleDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            setUpMapIfNeeded();
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("B_NAME"), details.getString("CATEG"),details.getString("ADDRESS"),details.getString("EMAIL"),details.getString("PHONE") +"");
                }
                else{
                    ShowMessage("No details found");
                }
            }
            catch(SQLException s){
                Log.e("Erro", s.getMessage());
            }
        }

        private void showDialog() {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hideDialog(){
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
        }

        // ******** FOR MAP ************ - CMalone

        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                if (mMap != null) {
                    setUpMap();

                    // *** Code for infowindow *** -CMalone
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

                    // *** For a new activity to open when info window is clicked *** -CMalone
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            // When the user clicks the InfoWindow it brings the user to the MapsActivity -CMalone
                            Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
        public void setUpMap() {

            String id = null;
            // MARKER: THE TEMPLE BAR -CMalone
            Marker mTemB =mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.345475, -6.264189))
                    .title("The Temple Bar")
                    .snippet("247-48 Temple Bar, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pint)));

            // Hashmap array entry -CMalone
            id = mTemB.getId();
            markerMap.put(id, "mTemB");

            // FOR CONTROLS ON THE MAP ITSELF -CMalone
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled( true );
            settings.setRotateGesturesEnabled( true );
            settings.setScrollGesturesEnabled( true );
            settings.setTiltGesturesEnabled( true );
            settings.setZoomControlsEnabled( true);
            settings.setZoomGesturesEnabled( true );
            settings.setMapToolbarEnabled(true);

            // Set camera to load up on the Temple Bar -CMalone
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.345475, -6.264189), 15.0f));
        }
    }

    //CAPTAINS DETAILS
    private class getCaptainsDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            pDialog = new ProgressDialog(DisplayActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getCaptainsDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            setUpMapIfNeeded();
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("R_NAME"), details.getString("CATEG"),details.getString("ADDRESS"),details.getString("EMAIL"),details.getString("PHONE") +"");
                }
                else{
                    ShowMessage("No details found");
                }
            }
            catch(SQLException s){
                Log.e("Erro", s.getMessage());
            }
        }

        private void showDialog() {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hideDialog(){
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
        }

        // ******** FOR MAP ************ - CMalone

        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                if (mMap != null) {
                    setUpMap();

                    // *** Code for infowindow *** -CMalone
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

                    // *** For a new activity to open when info window is clicked *** -CMalone
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            // When the user clicks the InfoWindow it brings them to the MapsActivity -CMalone
                            Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
        public void setUpMap() {

            String id = null;
            // MARKER: CAPTAIN AMERICAS -CMalone

            Marker mCA = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.3405075, -6.2628862))
                    .title("Captain America's")
                    .snippet("44 Grafton St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));

            // Hashmap array entry -CMalone
            id = mCA.getId();
            markerMap.put(id, "mCA");

            // FOR CONTROLS ON THE MAP ITSELF -CMalone
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled( true );
            settings.setRotateGesturesEnabled( true );
            settings.setScrollGesturesEnabled( true );
            settings.setTiltGesturesEnabled( true );
            settings.setZoomControlsEnabled( true);
            settings.setZoomGesturesEnabled( true );
            settings.setMapToolbarEnabled(true);

            // Set camera to load up on the Captain Americas -CMalone
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3405075, -6.2628862), 15.0f));
        }
    }

    //TGIF DETAILS
    private class getTgifDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            pDialog = new ProgressDialog(DisplayActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getTgifDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            setUpMapIfNeeded();
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("R_NAME"), details.getString("CATEG"),details.getString("ADDRESS"),details.getString("EMAIL"),details.getString("PHONE") +"");
                }
                else{
                    ShowMessage("No details found");
                }
            }
            catch(SQLException s){
                Log.e("Erro", s.getMessage());
            }
        }

        private void showDialog() {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hideDialog(){
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
        }

        // ******** FOR MAP ************ - CMalone

        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                if (mMap != null) {
                    setUpMap();

                    // *** Code for infowindow *** -CMalone
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

                    // *** For a new activity to open when info window is clicked *** -CMalone
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            // When the user clicks the InfoWindow it brings them to the MapsActivity -CMalone
                            Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
        public void setUpMap() {

            String id = null;
           // MARKER: TGI FRIDAYS -CMalone
            Marker mTGIF = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.3397012, -6.2634227))
                    .title("Tgi Friday's")
                    .snippet("St Stephen's Green, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));

            // Hashmap array entry -CMalone
            id = mTGIF.getId();
            markerMap.put(id, "mTGIF");

            // FOR CONTROLS ON THE MAP ITSELF -CMalone
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled( true );
            settings.setRotateGesturesEnabled( true );
            settings.setScrollGesturesEnabled( true );
            settings.setTiltGesturesEnabled( true );
            settings.setZoomControlsEnabled( true);
            settings.setZoomGesturesEnabled( true );
            settings.setMapToolbarEnabled(true);

            // Set camera to load up on TGIF -CMalone
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3397012, -6.2634227), 15.0f));
        }
    }

    //COUNTER DETAILS
    private class getCounterDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            pDialog = new ProgressDialog(DisplayActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getCounterDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            setUpMapIfNeeded();
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("R_NAME"), details.getString("CATEG"),details.getString("ADDRESS"),details.getString("EMAIL"),details.getString("PHONE") +"");
                }
                else{
                    ShowMessage("No details found");
                }
            }
            catch(SQLException s){
                Log.e("Erro", s.getMessage());
            }
        }

        private void showDialog() {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hideDialog(){
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
        }

        // ******** FOR MAP ************ - CMalone

        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                if (mMap != null) {
                    setUpMap();

                    // *** Code for infowindow *** -CMalone
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
                    // *** For a new activity to open when info window is clicked *** -CMalone

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            // When the InfoWindow is clicked it brings the user to the MapsActivity -CMalone
                            Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
        public void setUpMap() {

            String id = null;
            // MARKER: THE COUNTER -CMalone
            Marker mTC =mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.343368, -6.260031))
                    .title("The Counter")
                    .snippet("20 Suffolk St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));

            // Hashmap array entry -CMalone
            id = mTC.getId();
            markerMap.put(id, "mTC");

            // FOR CONTROLS ON THE MAP ITSELF -CMalone
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled( true );
            settings.setRotateGesturesEnabled( true );
            settings.setScrollGesturesEnabled( true );
            settings.setTiltGesturesEnabled( true );
            settings.setZoomControlsEnabled( true);
            settings.setZoomGesturesEnabled( true );
            settings.setMapToolbarEnabled(true);

            // Set camera to load up on the Counter -CMalone
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.343368, -6.260031), 15.0f));
        }
    }

    //AUSSIEBBQ DETAILS
    private class getAussiebbqDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            pDialog = new ProgressDialog(DisplayActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getAussiebbgDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            setUpMapIfNeeded();
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("R_NAME"), details.getString("CATEG"),details.getString("ADDRESS"),details.getString("EMAIL"),details.getString("PHONE") +"");
                }
                else{
                    ShowMessage("No details found");
                }
            }
            catch(SQLException s){
                Log.e("Erro", s.getMessage());
            }
        }

        private void showDialog() {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hideDialog(){
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
        }

        // ******** FOR MAP ************ - CMalone

        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                if (mMap != null) {
                    setUpMap();

                    // *** Code for infowindow *** -CMalone
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

                    // *** For a new activity to open when info window is clicked *** -CMalone
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            // When the InfoWindow is clicked bring user to the MapsActivity -CMalone
                            Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
        public void setUpMap() {

            String id = null;
            // MARKER: AUSSIE BBQ -CMalone
            Marker mAB = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.331180, -6.264750))
                    .title("Aussie BBQ")
                    .snippet("45 South Richmond St, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));

            // Hashmap array entry -CMalone
            id = mAB.getId();
            markerMap.put(id, "mAB");

            // FOR CONTROLS ON THE MAP ITSELF -CMalone
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled( true );
            settings.setRotateGesturesEnabled( true );
            settings.setScrollGesturesEnabled( true );
            settings.setTiltGesturesEnabled( true );
            settings.setZoomControlsEnabled( true);
            settings.setZoomGesturesEnabled( true );
            settings.setMapToolbarEnabled(true);

            // Set camera to load up on the Aussie BBQ
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.331180, -6.264750), 15.0f));
        }
    }

    //ITALIAN DETAILS
    private class getItalianDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            pDialog = new ProgressDialog(DisplayActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getItalianDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            setUpMapIfNeeded();
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("R_NAME"), details.getString("CATEG"),details.getString("ADDRESS"),details.getString("EMAIL"),details.getString("PHONE") +"");
                }
                else{
                    ShowMessage("No details found");
                }
            }
            catch(SQLException s){
                Log.e("Erro", s.getMessage());
            }
        }

        private void showDialog() {
            if (!pDialog.isShowing()) {
                pDialog.show();
            }
        }

        private void hideDialog(){
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
        }

        // ******** FOR MAP ************ - CMalone

        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                if (mMap != null) {
                    setUpMap();

                    // *** Code for infowindow *** -CMalone
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

                    // *** For a new activity to open when info window is clicked *** -CMalone

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            // When InfoWindow is clicked it brings the user to the MapsActivity - CMalone
                            Intent intent = new Intent(DisplayActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }

        public void setUpMap() {

            String id = null;
            // MARKER: THE ITALIAN CORNER RESTAURANT -CMalone
            Marker mIC = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(53.345644, -6.264745))
                    .title("The Italian Corner Restaurant")
                    .snippet("23/24 Wellington Quay, Dublin 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cutlery)));

            // Hashmap array entry -CMalone
            id = mIC.getId();
            markerMap.put(id, "mIC");

            // FOR CONTROLS ON THE MAP ITSELF -CMalone
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled( true );
            settings.setRotateGesturesEnabled( true );
            settings.setScrollGesturesEnabled( true );
            settings.setTiltGesturesEnabled( true );
            settings.setZoomControlsEnabled( true);
            settings.setZoomGesturesEnabled( true );
            settings.setMapToolbarEnabled(true);

            // Set camera to load up on the Italian Corner -CMalone
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.345644, -6.264745), 15.0f));
        }
    }

//    //WHISKEY DETAILS
//    private class getWhiskeyDetails extends AsyncTask<Void, Void, Void> {
//        private ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute(){
//            pDialog = new ProgressDialog(DisplayActivity.this);
//            pDialog.setCancelable(false);
//            pDialog.setMessage("Getting details...");
//            showDialog();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            DBHelper db = new DBHelper();
//            details = db.getBarDetails();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void r){
//            hideDialog();
//            try {
//                if (details != null && details.next()) {
//                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
//                }
//                else{
//                    ShowMessage("No details found");
//                }
//            }
//            catch(SQLException s){
//                Log.e("Erro", s.getMessage());
//            }
//        }
//
//        private void showDialog() {
//            if (!pDialog.isShowing()) {
//                pDialog.show();
//            }
//        }
//
//        private void hideDialog(){
//            if(pDialog.isShowing()){
//                pDialog.dismiss();
//            }
//        }
//    }
//
//    //VODKA DETAILS
//    private class getVodkaDetails extends AsyncTask<Void, Void, Void> {
//        private ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute(){
//            pDialog = new ProgressDialog(DisplayActivity.this);
//            pDialog.setCancelable(false);
//            pDialog.setMessage("Getting details...");
//            showDialog();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            DBHelper db = new DBHelper();
//            details = db.getBarDetails();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void r){
//            hideDialog();
//            try {
//                if (details != null && details.next()) {
//                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
//                }
//                else{
//                    ShowMessage("No details found");
//                }
//            }
//            catch(SQLException s){
//                Log.e("Erro", s.getMessage());
//            }
//        }
//
//        private void showDialog() {
//            if (!pDialog.isShowing()) {
//                pDialog.show();
//            }
//        }
//
//        private void hideDialog(){
//            if(pDialog.isShowing()){
//                pDialog.dismiss();
//            }
//        }
//    }
//
//    //BRANDY DETAILS
//    private class getBrandyDetails extends AsyncTask<Void, Void, Void> {
//        private ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute(){
//            pDialog = new ProgressDialog(DisplayActivity.this);
//            pDialog.setCancelable(false);
//            pDialog.setMessage("Getting details...");
//            showDialog();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            DBHelper db = new DBHelper();
//            details = db.getBarDetails();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void r){
//            hideDialog();
//            try {
//                if (details != null && details.next()) {
//                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
//                }
//                else{
//                    ShowMessage("No details found");
//                }
//            }
//            catch(SQLException s){
//                Log.e("Erro", s.getMessage());
//            }
//        }
//
//        private void showDialog() {
//            if (!pDialog.isShowing()) {
//                pDialog.show();
//            }
//        }
//
//        private void hideDialog(){
//            if(pDialog.isShowing()){
//                pDialog.dismiss();
//            }
//        }
//    }
//
//    //RUM DETAILS
//    private class getRumDetails extends AsyncTask<Void, Void, Void> {
//        private ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute(){
//            pDialog = new ProgressDialog(DisplayActivity.this);
//            pDialog.setCancelable(false);
//            pDialog.setMessage("Getting details...");
//            showDialog();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            DBHelper db = new DBHelper();
//            details = db.getBarDetails();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void r){
//            hideDialog();
//            try {
//                if (details != null && details.next()) {
//                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
//                }
//                else{
//                    ShowMessage("No details found");
//                }
//            }
//            catch(SQLException s){
//                Log.e("Erro", s.getMessage());
//            }
//        }
//
//        private void showDialog() {
//            if (!pDialog.isShowing()) {
//                pDialog.show();
//            }
//        }
//
//        private void hideDialog(){
//            if(pDialog.isShowing()){
//                pDialog.dismiss();
//            }
//        }
//    }
//
//    //TEQUILA DETAILS
//    private class getTequilaDetails extends AsyncTask<Void, Void, Void> {
//        private ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute(){
//            pDialog = new ProgressDialog(DisplayActivity.this);
//            pDialog.setCancelable(false);
//            pDialog.setMessage("Getting details...");
//            showDialog();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            DBHelper db = new DBHelper();
//            details = db.getBarDetails();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void r){
//            hideDialog();
//            try {
//                if (details != null && details.next()) {
//                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
//                }
//                else{
//                    ShowMessage("No details found");
//                }
//            }
//            catch(SQLException s){
//                Log.e("Erro", s.getMessage());
//            }
//        }
//
//        private void showDialog() {
//            if (!pDialog.isShowing()) {
//                pDialog.show();
//            }
//        }
//
//        private void hideDialog(){
//            if(pDialog.isShowing()){
//                pDialog.dismiss();
//            }
//        }
//    }
//
//    //GIN DETAILS
//    private class getGinDetails extends AsyncTask<Void, Void, Void> {
//        private ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute(){
//            pDialog = new ProgressDialog(DisplayActivity.this);
//            pDialog.setCancelable(false);
//            pDialog.setMessage("Getting details...");
//            showDialog();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            DBHelper db = new DBHelper();
//            details = db.getBarDetails();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void r){
//            hideDialog();
//            try {
//                if (details != null && details.next()) {
//                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
//                }
//                else{
//                    ShowMessage("No details found");
//                }
//            }
//            catch(SQLException s){
//                Log.e("Erro", s.getMessage());
//            }
//        }
//
//        private void showDialog() {
//            if (!pDialog.isShowing()) {
//                pDialog.show();
//            }
//        }
//
//        private void hideDialog(){
//            if(pDialog.isShowing()){
//                pDialog.dismiss();
//            }
//        }
//    }
//
//    //BEER DETAILS
//    private class getBeerDetails extends AsyncTask<Void, Void, Void> {
//        private ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute(){
//            pDialog = new ProgressDialog(DisplayActivity.this);
//            pDialog.setCancelable(false);
//            pDialog.setMessage("Getting details...");
//            showDialog();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            DBHelper db = new DBHelper();
//            details = db.getBarDetails();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void r){
//            hideDialog();
//            try {
//                if (details != null && details.next()) {
//                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
//                }
//                else{
//                    ShowMessage("No details found");
//                }
//            }
//            catch(SQLException s){
//                Log.e("Erro", s.getMessage());
//            }
//        }
//
//        private void showDialog() {
//            if (!pDialog.isShowing()) {
//                pDialog.show();
//            }
//        }
//
//        private void hideDialog(){
//            if(pDialog.isShowing()){
//                pDialog.dismiss();
//            }
//        }
//    }
//
//    //CIDER DETAILS
//    private class getCiderDetails extends AsyncTask<Void, Void, Void> {
//        private ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute(){
//            pDialog = new ProgressDialog(DisplayActivity.this);
//            pDialog.setCancelable(false);
//            pDialog.setMessage("Getting details...");
//            showDialog();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            DBHelper db = new DBHelper();
//            details = db.getBarDetails();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void r){
//            hideDialog();
//            try {
//                if (details != null && details.next()) {
//                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
//                }
//                else{
//                    ShowMessage("No details found");
//                }
//            }
//            catch(SQLException s){
//                Log.e("Erro", s.getMessage());
//            }
//        }
//
//        private void showDialog() {
//            if (!pDialog.isShowing()) {
//                pDialog.show();
//            }
//        }
//
//        private void hideDialog(){
//            if(pDialog.isShowing()){
//                pDialog.dismiss();
//            }
//        }
//    }



/*

    // ******** FOR MAP ************ - CM
    //private GoogleMap mMap;


    // CODE TAKEN FROM TECH ACADEMY TUTORIAL



 @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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
                            Intent i = new Intent(DisplayActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mOR")) {
                            Intent i = new Intent(DisplayActivity.this, AddDetailActivity.class);
                            startActivity(i);
                        } else if (m.equals("mTP")) {
                            Intent i = new Intent(DisplayActivity.this, CocktailScreen.class);
                            startActivity(i);
                        } else if (m.equals("mTGIF")) {
                            Intent i = new Intent(DisplayActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mSI")) {
                            Intent i = new Intent(DisplayActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mDG")) {
                            Intent i = new Intent(DisplayActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mD2")) {
                            Intent i = new Intent(DisplayActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mEG")) {
                            Intent i = new Intent(DisplayActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mCFJ")) {
                            Intent i = new Intent(DisplayActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mTB")) {
                            Intent i = new Intent(DisplayActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mLA")) {
                            Intent i = new Intent(DisplayActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mTemB")) {
                            Intent i = new Intent(DisplayActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mTC")) {
                            Intent i = new Intent(DisplayActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mAB")) {
                            Intent i = new Intent(DisplayActivity.this, itp2.class);
                            startActivity(i);
                        } else if (m.equals("mIC")) {
                            Intent i = new Intent(DisplayActivity.this, itp2.class);
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


*//* USE IF STATEMENTS TO SELECT MARKERS SHOWN ACCORDING TO WHAT IS SELECTED IN THE DROPDOWN MENU *//*


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





        // FOR CONTROLS ON THE MAP ITSELF
        UiSettings settings = mMap.getUiSettings();
        settings.setCompassEnabled( true );
        settings.setRotateGesturesEnabled( true );
        settings.setScrollGesturesEnabled( true );
        settings.setTiltGesturesEnabled( true );
        settings.setZoomControlsEnabled( true);
        settings.setZoomGesturesEnabled( true );
        settings.setMapToolbarEnabled(true);

       // mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3482961, -6.257899), 15.0f));



    }*/




}


