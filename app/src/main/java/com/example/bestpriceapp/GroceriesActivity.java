package com.example.bestpriceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.bestpriceapp.Model.Products;
import com.example.bestpriceapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class GroceriesActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private RecyclerView recyclerViewSearch;
    RecyclerView.LayoutManager layoutManagerSearch;

    private EditText searchInput;
    private MaterialButton searchBtn;
    private String searchInputText;

    private DatabaseReference productsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_groceries);

        // add back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // connect to database
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Search Bar
        searchInput = (EditText) findViewById(R.id.searchInput);
        searchBtn = (MaterialButton) findViewById(R.id.searchBtn);

        recyclerViewSearch = findViewById(R.id.recycler_search_menu);
        recyclerViewSearch .setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewSearch .setLayoutManager(layoutManager);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInputText = searchInput.getText().toString();

                onSearchBtnClick();
            }
        });

        // Floating cart button
        floatingActionButton = (FloatingActionButton) findViewById(R.id.shoppingCartGroceries);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroceriesActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

        // Bottom Navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation_groceries);

        bottomNavigationView.setSelectedItemId(R.id.groceries_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_nav:
                                Intent intent1 = new Intent(GroceriesActivity.this, HomeActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.groceries_nav:
                                break;
                            case R.id.orders_nav:
                                Intent intent3 = new Intent(GroceriesActivity.this, OrderActivity.class);
                                startActivity(intent3);
                                break;
                            case R.id.account_nav:
                                Intent intent2 = new Intent(GroceriesActivity.this, AccountActivity.class);
                                startActivity(intent2);
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    protected void onStart(){
        super.onStart();

        // options for the recycler to show all the items
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(productsRef, Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull Products products) {
                holder.productName.setText(products.getName());

                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GroceriesActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("name", products.getName());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onSearchBtnClick() {

        // get options name for search
        FirebaseRecyclerOptions<Products> optionsForSearch = new FirebaseRecyclerOptions.Builder<Products>().setQuery(productsRef.orderByChild("name").startAt(searchInputText).endAt(searchInputText), Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapterSearch = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(optionsForSearch) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull Products products) {
                holder.productName.setText(products.getName());

                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GroceriesActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("name", products.getName());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };

        recyclerViewSearch.setAdapter(adapterSearch);
        adapterSearch.startListening();

    }


}