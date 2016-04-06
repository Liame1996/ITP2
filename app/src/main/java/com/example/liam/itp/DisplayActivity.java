package com.example.liam.itp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DisplayActivity extends AppCompatActivity {

    private TextView nameV, typeV, addressV, emailV, phoneV, tvDay, tvHour, tvMinute, tvSecond, countTV;
    private Button prevButton, nextButton, addButton;
    private LinearLayout countdownLayout;
    private Handler handler;
    private Runnable runnable;
    private ImageButton homeBtn, addVenueBtn, locationBtn, cocktailsBtn;

    private ResultSet details = null;

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



}
