package com.example.bestpriceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bestpriceapp.Model.Users;
import com.example.bestpriceapp.Prevalent.Prevalent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.paperdb.Paper;

public class AccountActivity extends AppCompatActivity {

    private Button LogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_account);

        Users user = new Users();

        TextView userName = (TextView) findViewById(R.id.userNameDisplay);
        userName.setText(Prevalent.currentOnlineUser.getFirstName() + " " + Prevalent.currentOnlineUser.getLastName() );

        TextView userEmail= (TextView) findViewById(R.id.userEmailDisplay);
        userEmail.setText(Prevalent.currentOnlineUser.getEmail());

        TextView userPhoneNumber= (TextView) findViewById(R.id.userPhoneNumberDisplay);
        userPhoneNumber.setText(Prevalent.currentOnlineUser.getPhone());

        LogoutButton = (Button) findViewById(R.id.logoutButton);

        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.book().destroy();

                // main activity is the login or guest checkout page...
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation_account);

        bottomNavigationView.setSelectedItemId(R.id.account_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_nav:
                                Intent intent1 = new Intent(AccountActivity.this, HomeActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.groceries_nav:
                                Intent intent2 = new Intent(AccountActivity.this, GroceriesActivity.class);
                                startActivity(intent2);
                                break;
                            case R.id.orders_nav:
                                Intent intent3 = new Intent(AccountActivity.this, OrderActivity.class);
                                startActivity(intent3);
                                break;
                            case R.id.account_nav:
                                break;
                        }
                        return true;
                    }
                });
    }
}