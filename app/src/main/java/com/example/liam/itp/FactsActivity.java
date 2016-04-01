package com.example.liam.itp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

public class FactsActivity extends AppCompatActivity {
    private TextView TitleV, IngredientsV, DescriptionV, FactV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TitleV = (TextView)findViewById(R.id.titleID);
        IngredientsV = (TextView)findViewById(R.id.ingredientsID);
        DescriptionV = (TextView)findViewById(R.id.descriptionID);
        FactV = (TextView)findViewById(R.id.factID);



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

    public void display(String name, String type, String address, String email, String phone){
        TitleV.setText(name);
        IngredientsV.setText(type);
        DescriptionV.setText(address);
        FactV.setText(email);
    }
}