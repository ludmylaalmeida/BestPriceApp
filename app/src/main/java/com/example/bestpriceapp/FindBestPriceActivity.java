package com.example.bestpriceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class FindBestPriceActivity extends AppCompatActivity {

    private TextView totalPrice, totalQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_best_price);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        totalPrice = (TextView) findViewById(R.id.subtotal);
        totalPrice.setText("Subtotal: $" + getIntent().getStringExtra("Total Price"));

        totalQuantity = (TextView) findViewById(R.id.totalItemsText);
        totalQuantity.setText(getIntent().getStringExtra("Quantity") + " items");

        // Bottom Navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation_bestPrice);

        bottomNavigationView.setSelectedItemId(R.id.orders_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_nav:
                                Intent intent1 = new Intent(FindBestPriceActivity.this, HomeActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.groceries_nav:
                                Intent intent3 = new Intent(FindBestPriceActivity.this, GroceriesActivity.class);
                                startActivity(intent3);
                                break;
                            case R.id.orders_nav:
                                break;
                            case R.id.account_nav:
                                Intent intent2 = new Intent(FindBestPriceActivity.this, AccountActivity.class);
                                startActivity(intent2);
                                break;
                        }
                        return true;
                    }
                });



    }
}