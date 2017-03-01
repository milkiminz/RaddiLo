package com.example.milkiminz.raddilo;

import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Hashtable;
import java.util.Map;

public class RegistrationCust extends AppCompatActivity {


    String loginUrl;

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
        setContentView(R.layout.activity_registrationcust);
        name = (EditText) findViewById(R.id.cname);
        email = (EditText) findViewById(R.id.cemail);
        password = (EditText) findViewById(R.id.cpassword);
        confirmpassword = (EditText) findViewById(R.id.cconfirmpassword);
        phone = (EditText) findViewById(R.id.cphone);
        address = (EditText) findViewById(R.id.caddress);
        loginUrl = getResources().getString(R.string.registercust);

    }


    public void Check(View view) { //when button is clicked
        nm=name.getText().toString();
        em=email.getText().toString();
        pass=password.getText().toString();
        ph=phone.getText().toString();
        add=address.getText().toString();
        if(!name.getText().toString().equals("")||!email.getText().toString().equals("")||!password.getText().toString().equals("")||!phone.getText().toString().equals("")||!address.getText().toString().equals("")) {
            if (isNetworkAvailable()) {
                if (password.getText().toString().equals(confirmpassword.getText().toString())) {
                    new AttemptRegister().execute();
                } else {
                    Toast.makeText(RegistrationCust.this, getResources().getString(R.string.differentpassword), Toast.LENGTH_SHORT).show();
                }
            } else
                Toast.makeText(this, getResources().getString(R.string.slowinternet), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getResources().getString(R.string.fillfields), Toast.LENGTH_SHORT).show();  //if fields are empty
        }
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

    public void login(View view){
        startActivity(new Intent(RegistrationCust.this,LoginCust.class));
    }
    class AttemptRegister extends AsyncTask<String, String, String> {

        String em=email.getText().toString();
        String pass=password.getText().toString();


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegistrationCust.this);
            pDialog.setMessage("Registering....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {



            //StringRequest function


            StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pDialog.dismiss();

                            if (s.equals(getResources().getString(R.string.success))) {


                                Toast.makeText(RegistrationCust.this, getResources().getString(R.string.success)+" Registered", Toast.LENGTH_LONG).show();
                                finish();
                            } else if (s.equals(getResources().getString(R.string.failed))) {


                                Toast.makeText(RegistrationCust.this, getResources().getString(R.string.rf), Toast.LENGTH_LONG).show();

                            }
                            else if(s.equals(getResources().getString(R.string.already))){
                                Toast.makeText(RegistrationCust.this, getResources().getString(R.string.ar), Toast.LENGTH_LONG).show();

                            }



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    pDialog.dismiss();


                    Toast.makeText(RegistrationCust.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();


                    //get response body and parse with appropriate encoding

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    //Creating parameters
                    Map<String, String> params = new Hashtable<>();

                    //Adding parameters
                    params.put("cname",nm);
                    params.put("cpassword", pass);
                    params.put("cemail", em);
                    params.put("cphone",ph);
                    params.put("caddress",add);

                    //returning parameters
                    return params;
                }
            };


            //Creating a Request Queue
            requestQueue = Volley.newRequestQueue(RegistrationCust.this);

            //Adding request to the queue
            requestQueue.add(stringRequest);

            return null;
        }

        protected void onPostExecute(String message) {
            pDialog.dismiss();


        }
    }


}
