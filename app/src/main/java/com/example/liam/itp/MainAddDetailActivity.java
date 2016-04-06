package com.example.liam.itp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.liam.itp.DBHelper;

public class MainAddDetailActivity extends AppCompatActivity {

    private EditText nameField, addressField, emailField, phoneField;
    private Spinner typeField;
    private Button addBtn, displayBtn;
    private ImageButton homeBtn, locationBtn, addVenueBtn, cocktailsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameField = (EditText)findViewById(R.id.nameET);
        typeField = (Spinner)findViewById(R.id.type);
        addressField = (EditText)findViewById(R.id.addressET);
        emailField = (EditText)findViewById(R.id.emailET);
        phoneField = (EditText)findViewById(R.id.phoneET);

        addBtn = (Button)findViewById(R.id.addBtn);
        homeBtn = (ImageButton) findViewById(R.id.homeBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameField.getText().toString().isEmpty() || addressField.getText().toString().isEmpty() || emailField.getText().toString().isEmpty() || phoneField.getText().toString().isEmpty()){
                    ShowMessage("Please fill in all details!");
                }
                else{
                    new AddDetails().execute();
                    nameField.setText(null);
                    addressField.setText(null);
                    emailField.setText(null);
                    phoneField.setText(null);

                }
            }
        });

        displayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainAddDetailActivity.this, DisplayActivity.class);
                startActivity(i);
                finish();
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainAddDetailActivity.this, DisplayActivity.class);
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
            Intent i = new Intent(this, MainAddDetailActivity.class);
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

        return super.onOptionsItemSelected(item);
    }


    private void ShowMessage(String msg){
        Toast.makeText(MainAddDetailActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    private class AddDetails extends AsyncTask<Void, Void, Void> {
        String name, type, address, email;
        int phone;
        private ProgressDialog pDialog;
        private boolean result;

        @Override
        protected void onPreExecute(){
            name = nameField.getText().toString();
            type = typeField.getSelectedItem().toString();
            address = addressField.getText().toString();
            email = emailField.getText().toString();
            phone = Integer.parseInt(phoneField.getText().toString());
            result = false;

            pDialog = new ProgressDialog(MainAddDetailActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Adding Details...");
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {

            DBHelper db = new DBHelper();
            result = db.addDetails(name, type, address, email, phone);
            return null;
        }

        @Override
        protected void onPostExecute(Void r){
            hideDialog();
            if(result == false){
                //result added
                ShowMessage("Details Added for Authentication!");
            }
            else{
                //details weren't added
                ShowMessage("Details not added!");
            }

        }

        private void showDialog(){
            if(!pDialog.isShowing()){
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
