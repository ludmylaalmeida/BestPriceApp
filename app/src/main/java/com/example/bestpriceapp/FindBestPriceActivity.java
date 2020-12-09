package com.example.bestpriceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FindBestPriceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_best_price);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

    }
}