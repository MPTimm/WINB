package com.example.winb.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.winb.Adapter.RecyclerViewAdapter;
import com.example.winb.Database.DatabaseHelper;
import com.example.winb.Model.Product;
import com.example.winb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Product> groceryList;
    private List<Product> listItems;
    private DatabaseHelper db;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private EditText groceryItem, quantity;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopUpDialog();
            }
        });


        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceryList = new ArrayList<>();
        listItems = new ArrayList<>();

        groceryList = db.getAllGrocery();
        for (Product g : groceryList) {
            Product grocery = new Product();
            grocery.setName(g.getName());
            grocery.setQty("Quantity :" + g.getQty());
            grocery.setId(g.getId());
            grocery.setDateAdded("Added on : " + g.getDateAdded());

            listItems.add(grocery);
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, listItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void createPopUpDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.pop_up, null);
        groceryItem = view.findViewById(R.id.grocery_item);
        quantity = view.findViewById(R.id.quantity);
        saveButton = view.findViewById(R.id.saveButton);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!groceryItem.getText().toString().isEmpty() && !quantity.getText().toString().isEmpty()) {
                    saveGroceryToDB(v);
                }
            }
        });

    }

    private void saveGroceryToDB(View v) {
        Product grocery = new Product();
        String newGrocery = groceryItem.getText().toString();
        String newGroceryQty = quantity.getText().toString();

        Toast.makeText(getApplicationContext(), "Name" + newGrocery + "Quantity" + newGroceryQty, Toast.LENGTH_LONG).show();

        grocery.setName(newGrocery);
        grocery.setQty(newGroceryQty);

        db.addGrocery(grocery);
        Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                startActivity(new Intent(ListActivity.this, ListActivity.class));
            }
        }, 1000);

    }

    public void byPassActivity() {
        if (db.getGroceryCount() > 0) {
            startActivity(new Intent(ListActivity.this, ListActivity.class));
            finish();
        }
    }
}
