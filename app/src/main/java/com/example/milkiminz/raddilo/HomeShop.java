package com.example.milkiminz.raddilo;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HomeShop extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        ListView orderlist;
    RequestQueue requestQueue;
    String[] nm,address,mail,ph,qty,ppr,pls,mel,gls,oth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_shop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        orderlist=(ListView)findViewById(R.id.orderlist);
        requestQueue= Volley.newRequestQueue(HomeShop.this);

        JSONObject params = new JSONObject();
        try{
            params.put("email",loadData());

        }catch (JSONException e){

        }
        String load_url = getResources().getString(R.string.homeshopurl);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, load_url,params, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonArray=response.getJSONArray("orderdetails");
                    int l=jsonArray.length();
                    nm=new String[l];
                    address=new String[l];
                    mail=new String[l];
                    ph=new String[l];
                    qty=new String[l];
                    ppr=new String[l];
                    pls=new String[l];
                    gls=new String[l];
                    mel=new String[l];
                    oth=new String[l];
                    for (int i=0;i<l;i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        nm[i]=jsonObject.getString("cname");
                        address[i]=jsonObject.getString("cadd");
                        mail[i]=jsonObject.getString("cemail");
                        ph[i]=jsonObject.getString("cph");
                        qty[i]=jsonObject.getString("bweight");
                        ppr[i]=jsonObject.getString("bpaper");
                        pls[i]=jsonObject.getString("bplastic");
                        gls[i]=jsonObject.getString("bglass");
                        mel[i]=jsonObject.getString("bmetal");
                        oth[i]=jsonObject.getString("bother");
                    }
                    recycleorder rc=new recycleorder(HomeShop.this,nm,mail,ph,address,qty,ppr,gls,mel,oth,pls);
                    orderlist.setAdapter(rc);
                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeShop.this,error.toString(),Toast.LENGTH_SHORT).show();
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





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.home_shop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_feeback) {
            startActivity(new Intent(HomeShop.this,Feedback.class));

        }
        else if (id==R.id.action_aboutdevelopers){
            startActivity(new Intent(this,AboutDevelopers.class));


        }
        else if (id==R.id.action_logout){
            File dir = getFilesDir();
            File file = new File(dir, "email.txt");
            file.delete();
            Intent p=new Intent(this,Loginshop.class);
            startActivity(p);
            finish();

        }
        else if (id==R.id.action_aboutdevelopers){
            startActivity(new Intent(HomeShop.this,AboutDevelopers.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_shome) {
            startActivity(new Intent(HomeShop.this,HomeShop.class));
            finish();
        } else if (id == R.id.nav_sprofile) {
            startActivity(new Intent(HomeShop.this,ProfileShop.class));
            finish();
        } else if (id == R.id.nav_aboutus) {
            startActivity(new Intent(HomeShop.this,AboutUs.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
