package com.example.milkiminz.raddilo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;


public class LoginCust extends AppCompatActivity  {

    String loginUrl;

    EditText email;
    EditText password;
    ProgressDialog pDialog;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logincust);
       email = (EditText) findViewById(R.id.cemail);
       password = (EditText) findViewById(R.id.cpassword);
        loginUrl = getResources().getString(R.string.logincust);



    }

    public void Login_now(View view){
        if(!email.getText().toString().equals("")||!password.getText().toString().equals("")){
        if (isNetworkAvailable()) {
            new AttemptLogin().execute();
        }else{
            Toast.makeText(LoginCust.this,getResources().getString(R.string.slowinternet), Toast.LENGTH_LONG).show();//for slow interner
        }
    }else{
            Toast.makeText(this, getResources().getString(R.string.enteremailpassword), Toast.LENGTH_SHORT).show();//for wrong email or password
        }
    }







    class AttemptLogin extends AsyncTask<String, String, String> {

        String em=email.getText().toString();
        String pass=password.getText().toString();
        String success;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginCust.this);
            pDialog.setMessage("Logging in....");
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

                               if (s.equals(getResources().getString(R.string.success))) {

                                    saveData2(em);
                                    Toast.makeText(LoginCust.this, getResources().getString(R.string.success), Toast.LENGTH_LONG).show();
                                   startActivity(new Intent(LoginCust.this,HomeCust.class));
                                    finish();
                                } else if (s.equals(getResources().getString(R.string.failed))) {


                                    Toast.makeText(LoginCust.this,getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(LoginCust.this, getResources().getString(R.string.slowinternet), Toast.LENGTH_LONG).show();

                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        pDialog.dismiss();


                        Toast.makeText(LoginCust.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();


                        //get response body and parse with appropriate encoding

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        //Creating parameters
                        Map<String, String> params = new Hashtable<>();

                        //Adding parameters
                        params.put("cpassword", pass);
                        params.put("cemail", em);

                        //returning parameters
                        return params;
                    }
                };


                //Creating a Request Queue
                requestQueue = Volley.newRequestQueue(LoginCust.this);

                //Adding request to the queue
                requestQueue.add(stringRequest);

                return null;
            }

        protected void onPostExecute(String message) {
            pDialog.dismiss();


        }
    }

    protected void saveData2(String email){


        DBHelper db=new DBHelper(getApplicationContext());
        db.insertContact(email);
    }
    protected String loadData() {

        DBHelper db=new DBHelper(getApplicationContext());
        Cursor c=db.getData();
        c.moveToFirst();
        return c.getString(1);
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


}




