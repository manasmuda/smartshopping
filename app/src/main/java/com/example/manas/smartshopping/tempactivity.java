package com.example.manas.smartshopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class tempactivity extends AppCompatActivity {
    private FirebaseApp fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempactivity);
        startSearch("s",true,savedInstanceState,true);
    }
}
