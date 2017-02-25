package com.example.milkiminz.raddilo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.example.milkiminz.raddilo.R.styleable.View;

public class Choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);


    }
    public void recycler(View view){
        startActivity(new Intent(Choose.this,Registrationshop.class));
    }
    public void  customer(View view){
        startActivity(new Intent(Choose.this,Registrationcust.class));
    }
}
