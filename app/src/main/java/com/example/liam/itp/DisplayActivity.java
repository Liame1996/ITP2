package com.example.liam.itp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * DisplayActivity.java
 * @reference https://www.youtube.com/user/BowToKingBen
 * @reference http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
 * 13/02/2016
 * @author Evan Masterson, x14426302
 */
public class DisplayActivity extends AppCompatActivity {

    private TextView nameV, typeV, addressV, emailV, phoneV, tvHour, tvMinute, tvSecond, openClose, openingClosing;
    private LinearLayout countdownLayout;
    private Handler handler;
    private Runnable runnable;

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
        openClose = (TextView)findViewById(R.id.openClose);
        openingClosing = (TextView)findViewById(R.id.openingClosing);


        if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Diceys")){
            new getDiceysDetails().execute();
            DiceysCountDownStart();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Palace")){
            new getPalaceDetails().execute();
            PalaceCountDownStart();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Dtwo")){
            new getDtwoDetails().execute();
            DtwoCountDownStart();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Everleigh")){
            new getEverleighDetails().execute();
            EverleighCountDownStart();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Coppers")){
            new getCoppersDetails().execute();
            CoppersCountDownStart();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Sinnotts")){
            new getSinnottsDetails().execute();
            SinnottsCountDownStart();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Oreillys")){
            new getOreillysDetails().execute();
            OreillysCountDownStart();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Trinity")){
            new getTrinityDetails().execute();
            TrinityCountDownStart();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Lagoona")){
            new getLagoonaDetails().execute();
            LagoonaCountDownStart();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Temple")){
            new getTempleDetails().execute();
            TempleCountDownStart();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Captains")){
            new getCaptainsDetails().execute();
            CaptainsCountDownStart();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Tgif")){
            new getTgifDetails().execute();
            TgifCountDownStart();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Counter")){
            new getCounterDetails().execute();
            CounterCountDownStart();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Aussiebbq")){
            new getAussiebbqDetails().execute();
            AussiebbqCountDownStart();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Italian")){
            new getItalianDetails().execute();
            ItalianCountDownStart();
        }


        initUI();
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
        tvHour = (TextView) findViewById(R.id.txtTimerHour);
        tvMinute = (TextView) findViewById(R.id.txtTimerMinute);
        tvSecond = (TextView) findViewById(R.id.txtTimerSecond);
    }

    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void DiceysCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
                        Date startTime = dateFormat.parse("16:00:00");
                        Date endTime = dateFormat.parse("02:30:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void PalaceCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
                        Date startTime = dateFormat.parse("22:00:00");
                        Date endTime = dateFormat.parse("02:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void DtwoCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
                        Date startTime = dateFormat.parse("23:00:00");
                        Date endTime = dateFormat.parse("02:30:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void EverleighCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
                        Date startTime = dateFormat.parse("22:30:00");
                        Date endTime = dateFormat.parse("03:30:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY){
                        Date startTime = dateFormat.parse("23:00:00");
                        Date endTime = dateFormat.parse("03:30:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
                        Date startTime = dateFormat.parse("23:00:00");
                        Date endTime = dateFormat.parse("05:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
                        Date startTime = dateFormat.parse("23:00:00");
                        Date endTime = dateFormat.parse("24:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void CoppersCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
                        Date startTime = dateFormat.parse("23:30:00");
                        Date endTime = dateFormat.parse("03:30:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void SinnottsCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY) {
                        Date startTime = dateFormat.parse("08:00:00");
                        Date endTime = dateFormat.parse("23:30:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY){
                        Date startTime = dateFormat.parse("08:00:00");
                        Date endTime = dateFormat.parse("02:30:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
                        Date startTime = dateFormat.parse("10:30:00");
                        Date endTime = dateFormat.parse("24:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
                        Date startTime = dateFormat.parse("12:00:00");
                        Date endTime = dateFormat.parse("23:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void OreillysCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY) {
                        Date startTime = dateFormat.parse("16:00:00");
                        Date endTime = dateFormat.parse("23:30:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY){
                        Date startTime = dateFormat.parse("16:00:00");
                        Date endTime = dateFormat.parse("03:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
                        Date startTime = dateFormat.parse("17:00:00");
                        Date endTime = dateFormat.parse("03:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
                        Date startTime = dateFormat.parse("17:00:00");
                        Date endTime = dateFormat.parse("23:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void TrinityCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
                        Date startTime = dateFormat.parse("08:00:00");
                        Date endTime = dateFormat.parse("24:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
                        Date startTime = dateFormat.parse("08:00:00");
                        Date endTime = dateFormat.parse("02:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void LagoonaCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY) {
                        Date startTime = dateFormat.parse("10:00:00");
                        Date endTime = dateFormat.parse("23:30:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
                        Date startTime = dateFormat.parse("12:00:00");
                        Date endTime = dateFormat.parse("24:30:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                    else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
                        Date startTime = dateFormat.parse("12:00:00");
                        Date endTime = dateFormat.parse("23:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void TempleCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY) {
                        Date startTime = dateFormat.parse("10:30:00");
                        Date endTime = dateFormat.parse("01:30:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
                        Date startTime = dateFormat.parse("10:30:00");
                        Date endTime = dateFormat.parse("02:30:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
                        Date startTime = dateFormat.parse("11:30:00");
                        Date endTime = dateFormat.parse("01:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }
    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void CaptainsCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
                        Date startTime = dateFormat.parse("12:00:00");
                        Date endTime = dateFormat.parse("02:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }
    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void TgifCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
                        Date startTime = dateFormat.parse("12:00:00");
                        Date endTime = dateFormat.parse("22:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
                        Date startTime = dateFormat.parse("12:00:00");
                        Date endTime = dateFormat.parse("23:30:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }
    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void CounterCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY) {
                        Date startTime = dateFormat.parse("12:00:00");
                        Date endTime = dateFormat.parse("22:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
                        Date startTime = dateFormat.parse("12:00:00");
                        Date endTime = dateFormat.parse("23:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
                        Date startTime = dateFormat.parse("12:00:00");
                        Date endTime = dateFormat.parse("21:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }
    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void AussiebbqCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
                        Date startTime = dateFormat.parse("12:00:00");
                        Date endTime = dateFormat.parse("24:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
                        Date startTime = dateFormat.parse("12:00:00");
                        Date endTime = dateFormat.parse("02:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }
    /**
     * http://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
     */
    public void ItalianCountDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                try{
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ||
                            cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
                        Date startTime = dateFormat.parse("08:00:00");
                        Date endTime = dateFormat.parse("22:00:00");
                        String currentTimeString = dateFormat.format(cal.getTime());
                        Date currentTimeDate = dateFormat.parse(currentTimeString);
                        if (currentTimeDate.after(startTime)) {
                            long diff = endTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently OPEN");
                                openingClosing.setText("Closing in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }else if(!currentTimeDate.after(startTime) || currentTimeDate.after(endTime)){
                            long diff = startTime.getTime() - currentTimeDate.getTime();
                            if (diff < 0) {
                                Date dateMax = dateFormat.parse("24:00:00");
                                Date dateMin = dateFormat.parse("00:00:00");
                                diff = (dateMax.getTime() - currentTimeDate.getTime()) + (endTime.getTime() - dateMin.getTime());
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            } else {
                                long hours = diff / (60 * 60 * 1000);
                                diff -= hours * (60 * 60 * 1000);
                                long minutes = diff / (60 * 1000);
                                diff -= minutes * (60 * 1000);
                                long seconds = diff / 1000;
                                openClose.setText("Currently CLOSED");
                                openingClosing.setText("Opening in:");
                                tvHour.setText("" + String.format("%02d", hours));
                                tvMinute.setText("" + String.format("%02d", minutes));
                                tvSecond.setText("" + String.format("%02d", seconds));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.335719, -6.263619), 17.0f));

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

    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.335940, -6.265624), 17.0f));
        }
    }

    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.335181, -6.263543), 17.0f));
        }

    }

    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.334425, -6.262743), 17.0f));
        }
    }

    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.335377, -6.263599), 17.0f));
        }
    }


    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.340136, -6.2637727), 17.0f));
        }
    }

    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3469988, -6.2563745), 17.0f));
        }
    }

    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3404330, -6.263613), 17.0f));
        }
    }

    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.349617, -6.243003), 17.0f));
        }
    }

    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.345475, -6.264189), 17.0f));
        }
    }

    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3405075, -6.2628862), 17.0f));
        }
    }

    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3397012, -6.2634227), 17.0f));
        }
    }

    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.343368, -6.260031), 17.0f));
        }
    }

    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.331180, -6.264750), 17.0f));
        }
    }

    /**
     * @reference https://www.youtube.com/user/BowToKingBen
     */
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.345644, -6.264745), 17.0f));
        }
    }
}


