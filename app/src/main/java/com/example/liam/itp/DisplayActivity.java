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
import java.util.Date;

public class DisplayActivity extends AppCompatActivity {

    private TextView nameV, typeV, addressV, emailV, phoneV, tvDay, tvHour, tvMinute, tvSecond;
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

        prevButton = (Button)findViewById(R.id.prevBtn);
        nextButton = (Button)findViewById(R.id.nextBtn);
        addButton = (Button)findViewById(R.id.addBtn);

        new getClubDetails().execute();
        initUI();
        countDownStart();



        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (details != null && details.previous()) {
                        display(details.getString("C_NAME"), details.getString("CATEG"), details.getString("ADDRESS"), details.getString("EMAIL"), details.getString("PHONE") + "");
                    }
                } catch (SQLException s) {
                    Log.e("", s.getMessage());
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (details != null && details.next()) {
                        display(details.getString("C_NAME"), details.getString("CATEG"),details.getString("ADDRESS"),details.getString("EMAIL"),details.getString("PHONE") +"");
                    }
                }
                catch(SQLException s){
                    Log.e("", s.getMessage());
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisplayActivity.this, AddDetailActivity.class);
                startActivity(i);
                finish();
            }
        });


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



        //CLUB DETAILS
        private class getClubDetails extends AsyncTask<Void, Void, Void> {
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
                details = db.getClubDetails();
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



    //BAR DETAILS
    private class getBarDetails extends AsyncTask<Void, Void, Void> {
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
            details = db.getClubDetails();
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

    //RESTAURANT DETAILS
    private class getRestaurantDetails extends AsyncTask<Void, Void, Void> {
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
            details = db.getClubDetails();
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

    //WHISKEY DETAILS
    private class getWhiskeyDetails extends AsyncTask<Void, Void, Void> {
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
            details = db.getClubDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
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

    //VODKA DETAILS
    private class getVodkaDetails extends AsyncTask<Void, Void, Void> {
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
            details = db.getClubDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
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

    //BRANDY DETAILS
    private class getBrandyDetails extends AsyncTask<Void, Void, Void> {
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
            details = db.getClubDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
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

    //RUM DETAILS
    private class getRumDetails extends AsyncTask<Void, Void, Void> {
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
            details = db.getClubDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
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

    //TEQUILA DETAILS
    private class getTequilaDetails extends AsyncTask<Void, Void, Void> {
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
            details = db.getClubDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
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

    //GIN DETAILS
    private class getGinDetails extends AsyncTask<Void, Void, Void> {
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
            details = db.getClubDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
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

    //BEER DETAILS
    private class getBeerDetails extends AsyncTask<Void, Void, Void> {
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
            details = db.getClubDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
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

    //CIDER DETAILS
    private class getCiderDetails extends AsyncTask<Void, Void, Void> {
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
            details = db.getClubDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Image"),details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
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



}
