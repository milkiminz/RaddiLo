package com.example.milkiminz.raddilo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class Logincust extends AppCompatActivity  {

    String loginUrl = "http://139.59.47.63/logincust.php";

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

    }

    public void Login_now(View view){
        if (isNetworkAvailable()) {
            new AttemptLogin().execute();
        }else{
            Toast.makeText(Logincust.this,"NO INTERNET", Toast.LENGTH_LONG).show();
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
            pDialog = new ProgressDialog(Logincust.this);
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

                               if (s.equals("success")) {


                                    Toast.makeText(Logincust.this, "success", Toast.LENGTH_LONG).show();
                                    finish();
                                } else if (s.equals("failed")) {


                                    Toast.makeText(Logincust.this, " Failed", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(Logincust.this, "Check Internet ", Toast.LENGTH_LONG).show();

                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        pDialog.dismiss();


                        Toast.makeText(Logincust.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();


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
                requestQueue = Volley.newRequestQueue(Logincust.this);

                //Adding request to the queue
                requestQueue.add(stringRequest);

                return null;
            }

        protected void onPostExecute(String message) {
            pDialog.dismiss();


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


}




