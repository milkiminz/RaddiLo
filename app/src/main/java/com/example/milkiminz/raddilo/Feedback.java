package com.example.milkiminz.raddilo;

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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    EditText gfb;
    String fb;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        requestQueue = Volley.newRequestQueue(this);
        gfb=(EditText) findViewById(R.id.feedback);
    }
    public void sendfeedback(View view) {
        fb = gfb.getText().toString();
        if (!fb.equals("")){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.feedback_url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals(getResources().getString(R.string.success))) {
                    Toast.makeText(Feedback.this, getResources().getString(R.string.successfully_send), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Feedback.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Creating parameters
                Map<String,String> params = new Hashtable<>();

                params.put("feedback", "By"+loadData()+":"+fb);


                return params;
            }
        };


        requestQueue.add(stringRequest);
        }else {
            Toast.makeText(this, getResources().getString(R.string.enter_query), Toast.LENGTH_SHORT).show();
        }
    }

    protected String loadData() {
        String FILENAME = "email.txt";
        String out="";
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
}

