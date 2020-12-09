package com.example.bestpriceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bestpriceapp.Prevalent.Prevalent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    private static Button createYourGroceryListBtn;
    private MaterialCardView card;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_home);

        TextView userName = (TextView) findViewById(R.id.welcomeUser);
        userName.setText("Hi " + Prevalent.currentOnlineUser.getFirstName() + ",");

        createYourGroceryListBtn = (Button) findViewById(R.id.createGrocerieListBtn);

        // Floating cart button
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.shoppingCartHome);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation_home);

        bottomNavigationView.setSelectedItemId(R.id.home_nav);


        // send user after button click to groceries page
        createYourGroceryListBtn.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         Intent createListIntent = new Intent(HomeActivity.this, GroceriesActivity.class);
                                                         startActivity(createListIntent);
                                                     }
                                                 }
        );

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_nav:
                                break;
                            case R.id.groceries_nav:

                                Intent intent1 = new Intent(HomeActivity.this, GroceriesActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.orders_nav:
                                Intent intent3 = new Intent(HomeActivity.this, OrderActivity.class);
                                startActivity(intent3);
                                break;
                            case R.id.account_nav:

                                Intent intent2 = new Intent(HomeActivity.this, AccountActivity.class);
                                startActivity(intent2);
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

//        FirebaseRecyclerOptions<>
    }


}