package com.example.appshell;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Scanner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
    }
    //QR SCANNER once it scans it determines if allergy is present and jumps to either safe/unsafe which then jumps backs to Search
}

