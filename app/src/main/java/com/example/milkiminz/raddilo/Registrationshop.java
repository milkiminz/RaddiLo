package com.example.milkiminz.raddilo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class Registrationshop extends AppCompatActivity {


    String loginUrl = "http://139.59.47.63/registershop.php";

    EditText name;
    EditText email;
    EditText password;
    EditText confirmpassword;
    EditText phone;
    EditText address;
    ProgressDialog pDialog;
    RequestQueue requestQueue;
    String nm,pass,ph,add,em;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationshop);
        name = (EditText) findViewById(R.id.sname);
        email = (EditText) findViewById(R.id.semail);
        password = (EditText) findViewById(R.id.spassword);
        confirmpassword = (EditText) findViewById(R.id.sconfirmpassword);
        phone = (EditText) findViewById(R.id.sphone);
        address = (EditText) findViewById(R.id.saddress);


    }


    public void Check(View view) {
        nm=name.getText().toString();
        em=email.getText().toString();
        pass=password.getText().toString();
        ph=phone.getText().toString();
        add=address.getText().toString();

        if(isNetworkAvailable()) {
            if (password.getText().toString().equals(confirmpassword.getText().toString())) {
                new AttemptRegister().execute();
            } else {
                Toast.makeText(Registrationshop.this, "Password did not match", Toast.LENGTH_SHORT).show();
            }
        }else
            Toast.makeText(this, "NO INTERNET", Toast.LENGTH_SHORT).show();

    }
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    protected void saveData2(String em){
        String FILENAME1 = "email.txt";
        String verifyme=em;

        try {
            FileOutputStream fos1 = getApplication().openFileOutput(FILENAME1, Context.MODE_PRIVATE);
            fos1.write(verifyme.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class AttemptRegister extends AsyncTask<String, String, String> {



        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(Registrationshop.this);
            pDialog.setMessage("Registering....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {





            StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pDialog.dismiss();

                            if (s.equals("success")) {

                                saveData2(em);
                                Toast.makeText(Registrationshop.this, "successfully Registered", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Registrationshop.this,otpshop.class));
                                finish();
                            } else if (s.equals("failed")) {


                                Toast.makeText(Registrationshop.this, "Registration Failed", Toast.LENGTH_LONG).show();

                            }
                            else if(s.equals("already")){
                                Toast.makeText(Registrationshop.this, "Already Registered", Toast.LENGTH_LONG).show();

                            }



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    pDialog.dismiss();


                    Toast.makeText(Registrationshop.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();


                    //get response body and parse with appropriate encoding

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    //Creating parameters
                    Map<String, String> params = new Hashtable<>();

                    //Adding parameters
                    params.put("sname",nm);
                    params.put("spassword", pass);
                    params.put("semail", em);
                    params.put("sphone",ph);
                    params.put("saddress",add);

                    //returning parameters
                    return params;
                }
            };


            //Creating a Request Queue
            requestQueue = Volley.newRequestQueue(Registrationshop.this);

            //Adding request to the queue
            requestQueue.add(stringRequest);

            return null;
        }

        protected void onPostExecute(String message) {
            pDialog.dismiss();


        }
    }


}
