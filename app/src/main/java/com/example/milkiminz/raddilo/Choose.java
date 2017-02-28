package com.example.milkiminz.raddilo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);


    }
    public void recycler(View view){
        startActivity(new Intent(Choose.this,RegistrationShop.class));
    }
    public void  customer(View view){
        startActivity(new Intent(Choose.this,RegistrationCust.class));
    }
}
