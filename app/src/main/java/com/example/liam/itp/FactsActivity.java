package com.example.liam.itp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * FactsActivity.java
 * @reference https://www.youtube.com/user/BowToKingBen
 * 06/03/2016
 * @author John Cahill, x14378581
 */
public class FactsActivity extends AppCompatActivity {
    private TextView TitleV, IngredientsV, DescriptionV, FactV;
    private Button prevButton, nextButton;

    private ResultSet details = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_facts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TitleV = (TextView)findViewById(R.id.titleID);
        IngredientsV = (TextView)findViewById(R.id.ingredientsID);
        DescriptionV = (TextView)findViewById(R.id.descriptionID);
        FactV = (TextView)findViewById(R.id.factID);

        prevButton = (Button)findViewById(R.id.prevButton);
        nextButton = (Button)findViewById(R.id.nextButton);

        prevButton.setVisibility(View.INVISIBLE);




        if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Whiskey")){
            new getWhiskeyDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Vodka")){
            new getVodkaDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Gin")){
            new getGinDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Rum")){
            new getRumDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Tequila")){
            new getTequilaDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Brandy")){
            new getBrandyDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Beer")){
            new getBeerDetails().execute();
        }else if(getIntent().getStringExtra("Extra").equalsIgnoreCase("Cider")) {
            new getCiderDetails().execute();
        }


        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    prevButton.setVisibility(View.INVISIBLE);
                    nextButton.setVisibility(View.VISIBLE);

                    if (details != null && details.previous()) {
                        display(details.getString("Title"), details.getString("Ingredients"), details.getString("Description"), details.getString("Fact") + "");
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
                    nextButton.setVisibility(View.INVISIBLE);
                    prevButton.setVisibility(View.VISIBLE);

                    if (details != null && details.next()) {
                        display(details.getString("Title"), details.getString("Ingredients"),details.getString("Description"),details.getString("Fact")+"");
                    }
                }
                catch(SQLException s){
                    Log.e("", s.getMessage());
                    }
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
        Toast.makeText(FactsActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    public void display(String name, String ingred, String desc, String fact){
        TitleV.setText(name);
        IngredientsV.setText(ingred);
        DescriptionV.setText(desc);
        FactV.setText(fact);
    }


    //WHISKEY DETAILS
    private class getWhiskeyDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            pDialog = new ProgressDialog(FactsActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getWhiskeyDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
                } else{
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
            pDialog = new ProgressDialog(FactsActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getVodkaDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
                } else{
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
            pDialog = new ProgressDialog(FactsActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getBrandyDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
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
            pDialog = new ProgressDialog(FactsActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getRumDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
                } else{
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
            pDialog = new ProgressDialog(FactsActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getTequilaDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
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
            pDialog = new ProgressDialog(FactsActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getGinDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
                } else{
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
            pDialog = new ProgressDialog(FactsActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getBeerDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
                } else{
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
            pDialog = new ProgressDialog(FactsActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Getting details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper db = new DBHelper();
            details = db.getCiderDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            try {
                if (details != null && details.next()) {
                    display(details.getString("Title"), details.getString("Ingredients"),details.getString("Description"),details.getString("Fact") +"");
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