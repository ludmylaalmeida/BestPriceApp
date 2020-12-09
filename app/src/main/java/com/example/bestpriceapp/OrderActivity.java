package com.example.bestpriceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bestpriceapp.Model.Cart;
import com.example.bestpriceapp.Model.Products;
import com.example.bestpriceapp.Prevalent.Prevalent;
import com.example.bestpriceapp.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MaterialButton findBestPrice;
    private TextView totalQuantity;

    public double orderTotalPrice = 0;
    public int orderTotalQuantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_order);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation_order);


        recyclerView = (RecyclerView) findViewById(R.id.cartRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        findBestPrice = (MaterialButton) findViewById(R.id.findBestPriceBtn);
        findBestPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OrderActivity.this, FindBestPriceActivity.class);
                intent.putExtra("Total Price", String.valueOf(orderTotalPrice).format("%.2f", orderTotalPrice));
                intent.putExtra("Quantity", String.valueOf(orderTotalQuantity));
                startActivity(intent);
                finish();
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.orders_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_nav:
                                Intent intent1 = new Intent(OrderActivity.this, HomeActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.groceries_nav:
                                Intent intent2 = new Intent(OrderActivity.this, GroceriesActivity.class);
                                startActivity(intent2);
                                break;
                            case R.id.orders_nav:
                                break;
                            case R.id.account_nav:
                                Intent intent3 = new Intent(OrderActivity.this, AccountActivity.class);
                                startActivity(intent3);
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("User View")
                .child(Prevalent.currentOnlineUser.getPhone()).child("Products"), Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
                cartViewHolder.txtProductName.setText(cart.getName());
                cartViewHolder.txtProductQuantity.setText(cart.getQuantity());

                double oneProductTotalPrice = (Double.valueOf(cart.getPrice())) * (Integer.valueOf(cart.getQuantity()));
                orderTotalPrice += oneProductTotalPrice;

                orderTotalQuantity += (Integer.valueOf(cart.getQuantity()));

                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]{
                                "Edit",
                                "Remove"
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent intent = new Intent(OrderActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("name", cart.getName());
                                    startActivity(intent);
                                }
                                // click on remove button
                                if (which == 1) {

                                    cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Products")
                                            .child(cart.getName()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(OrderActivity.this, "Item removed", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                }
                            }
                        });

                        builder.show();

                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}