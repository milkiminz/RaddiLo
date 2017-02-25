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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://139.59.47.63/feedback.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(Feedback.this, "Successfully Send", Toast.LENGTH_LONG).show();
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

                params.put("feedback", fb);


                return params;
            }
        };


        requestQueue.add(stringRequest);
    }
}

