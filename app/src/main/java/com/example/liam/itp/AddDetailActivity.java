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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * AddDetailActivity.java
 * @reference https://www.youtube.com/user/BowToKingBen
 * 13/02/2016.
 * @author Evan Masterson, x14426302
 */

public class AddDetailActivity extends AppCompatActivity {

    private EditText nameField, addressField, emailField, phoneField;
    private Spinner typeField;
    private Button addBtn;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * @reference https://www.youtube.com/watch?v=28jA5-mO8K8&index=8&list=LL9QnUxf2Pctj2wyWa4GABCw YouTube: PRABEESH R K
         */
        // ** FOR SPINNER/DROPDOWN ON MAP **

        typeField = (Spinner) findViewById(R.id.type);
        typeField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_LONG).show();
            }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

                    nameField = (EditText) findViewById(R.id.nameET);
                    typeField = (Spinner) findViewById(R.id.type);
                    addressField = (EditText) findViewById(R.id.addressET);
                    emailField = (EditText) findViewById(R.id.emailET);
                    phoneField = (EditText) findViewById(R.id.phoneET);

                    addBtn = (Button) findViewById(R.id.addBtn);

                    addBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (nameField.getText().toString().isEmpty() || addressField.getText().toString().isEmpty() || !emailField.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") || phoneField.getText().toString().isEmpty()) {
                                ShowMessage("Please fill in all details!");
                            } else {
                                new AddDetails().execute();
                                nameField.setText(null);
                                addressField.setText(null);
                                emailField.setText(null);
                                phoneField.setText(null);
                            }
                        }
                    });


                }

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

            private void ShowMessage(String msg) {
                Toast.makeText(AddDetailActivity.this, msg, Toast.LENGTH_LONG).show();
            }

            /**
             * @reference https://www.youtube.com/user/BowToKingBen
             */
            private class AddDetails extends AsyncTask<Void, Void, Void> {
                String name, type, address, email;
                int phone;
                private ProgressDialog pDialog;
                private boolean result;

                @Override
                protected void onPreExecute() {
                    name = nameField.getText().toString();
                    type = typeField.getSelectedItem().toString();
                    address = addressField.getText().toString();
                    email = emailField.getText().toString();
                    phone = Integer.parseInt(phoneField.getText().toString());
                    result = false;

                    pDialog = new ProgressDialog(AddDetailActivity.this);
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
                protected void onPostExecute(Void r) {
                    hideDialog();
                    if (result == false) {
                        //result added
                        ShowMessage("Details Added for Authentication!");
                    } else {
                        //details weren't added
                        ShowMessage("Details not added!");
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
        }
