package com.example.milkiminz.raddilo;

import android.app.ProgressDialog;
import android.content.Context;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;

public class otpcust extends AppCompatActivity {

    String Url;
    ProgressDialog pDialog;
    EditText cotp;
    String ss;
    RequestQueue requestQueue;

    String out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpcust);
        cotp=(EditText) findViewById(R.id.cotp);
        Url = getResources().getString(R.string.verifycust);
    }


    public void sub(View view)
    {
if (!cotp.getText().toString().equals("")){
        ss=cotp.getText().toString();
        new verify().execute();


    }else {
    Toast.makeText(this, getResources().getString(R.string.enterotp), Toast.LENGTH_SHORT).show();
}
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
    protected String loadData() {
        String FILENAME = "email.txt";

        try {
            out="";
            FileInputStream fis1 = getApplication().openFileInput(FILENAME);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));
            String sLine1 = null;

            while (((sLine1 = br1.readLine()) != null)) {
                out += sLine1;
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return out;
    }

    class verify extends AsyncTask<String, String, String> {



        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(otpcust.this);
            pDialog.setMessage("Checking...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {





            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pDialog.dismiss();

                            if (s.equals(getResources().getString(R.string.success))) {


                                Toast.makeText(otpcust.this, getResources().getString(R.string.success)+" Verified", Toast.LENGTH_LONG).show();

                            } else if (s.equals(getResources().getString(R.string.failed))) {


                                Toast.makeText(otpcust.this, getResources().getString(R.string.vf), Toast.LENGTH_LONG).show();

                            }



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    pDialog.dismiss();


                    Toast.makeText(otpcust.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();


                    //get response body and parse with appropriate encoding

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    //Creating parameters
                    Map<String, String> params = new Hashtable<>();

                    //Adding parameters
                    params.put("cemail",loadData());

                    params.put("cotp",ss);


                    //returning parameters
                    return params;
                }
            };


            //Creating a Request Queue
            requestQueue = Volley.newRequestQueue(otpcust.this);

            //Adding request to the queue
            requestQueue.add(stringRequest);

            return null;
        }

        protected void onPostExecute(String message) {
            pDialog.dismiss();


        }
    }



}
