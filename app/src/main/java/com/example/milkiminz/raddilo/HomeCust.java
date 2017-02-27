package com.example.milkiminz.raddilo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static com.example.milkiminz.raddilo.R.id.fab;

public class HomeCust extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Switch s1,s2,s3,s4,s5;
    EditText aw;
    RequestQueue requestQueue;
    ProgressDialog pDialog;
    String[] arr;
    Spinner sp;
    String spstring,s1string,s2string,s3string,s4string,s5string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cust);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        s1=(Switch) findViewById(R.id.s1);
        s2=(Switch) findViewById(R.id.s2);
        s3=(Switch) findViewById(R.id.s3);
        s4=(Switch) findViewById(R.id.s4);
        s5=(Switch) findViewById(R.id.s5);
        aw=(EditText)findViewById(R.id.aw);
        requestQueue = Volley.newRequestQueue(HomeCust.this);

        new AttemptLogin().execute();


        sp=(Spinner) findViewById(R.id.spinnershop);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_cust, menu);
        return true;
    }
    public void sellnow(View view){
        if(s1.isChecked()){
            s1string="on";

        }
        else {
            s1string="off";
        }
        if(s2.isChecked()){
            s2string="on";

        }
        else {
            s2string="off";
        }
        if(s3.isChecked()){
            s3string="on";

        }
        else {
            s3string="off";
        } if(s4.isChecked()){
            s4string="on";

        }
        else {
            s4string="off";
        } if(s5.isChecked()){
            s5string="on";

        }
        else {
            s5string="off";
        }



       StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://139.59.47.63/bookraddiorder.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(HomeCust.this, response, Toast.LENGTH_LONG).show();
                if(response.equals("success")) {
                    Toast.makeText(HomeCust.this, "Ordered Successfully", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(HomeCust.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Creating parameters
                Map<String,String> params = new Hashtable<>();
                params.put("email",loadData());
                params.put("switch1",s1string);
                params.put("switch2",s2string);
                params.put("switch3",s3string);
                params.put("switch4",s4string);
                params.put("switch5",s5string);
                params.put("aw",aw.getText().toString());
                params.put("shopname",spstring);
                return params;
            }
        };
        requestQueue.add(stringRequest);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_feeback) {
            startActivity(new Intent(HomeCust.this,Feedback.class));
            return true;
        }
        else if (id==R.id.action_logout){
            File dir = getFilesDir();
            File file = new File(dir, "email.txt");
            file.delete();
            Intent p=new Intent(this,Logincust.class);
            startActivity(p);
            finish();

        }
        else if (id==R.id.action_aboutdevelopers){
            startActivity(new Intent(HomeCust.this,AboutDevelopers.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chome) {
            startActivity(new Intent(HomeCust.this,HomeCust.class));
            finish();

        } else if (id == R.id.nav_cprofile) {
            startActivity(new Intent(HomeCust.this,ProfileCust.class));
            finish();

        }else if(id==R.id.nav_cbook){
            startActivity(new Intent(HomeCust.this,Bookings.class));
            finish();

        }
        else if (id == R.id.nav_aboutus) {
            startActivity(new Intent(HomeCust.this,AboutUs.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    class AttemptLogin extends AsyncTask<String, String, String> {


        String success;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(HomeCust.this);
            pDialog.setMessage("Logging in....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {
            JSONObject params = new JSONObject();
            String load_url = "http://139.59.47.63/fetchshop.php";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, load_url,params, new Response.Listener<JSONObject>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(JSONObject response) {
                     try{
                         JSONArray jarr=response.getJSONArray("shopname");
                         arr=new String[jarr.length()+1];
                         arr[0]="--Select--";
                         for(int i=0;i<jarr.length();i++)
                         {
                             JSONObject job=jarr.getJSONObject(i);
                             arr[i+1]=job.getString("sname");
                         }
                         ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeCust.this, android.R.layout.simple_spinner_item, arr);

                         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                         sp.setAdapter(adapter);


                         sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                             @Override
                             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                 spstring=sp.getSelectedItem().toString();
                             }

                             @Override
                             public void onNothingSelected(AdapterView<?> parent) {

                             }


                         });
                     }catch (Exception e){

                     }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(HomeCust.this,"Internet is slow. Please try again with good internet speed.",Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);

            return null;
        }

        protected void onPostExecute(String message) {
            pDialog.dismiss();



        }
    }
}
