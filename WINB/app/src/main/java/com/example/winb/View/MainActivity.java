package com.example.winb.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.winb.Database.DatabaseHelper;
import com.example.winb.Model.Product;
import com.example.winb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText item, pQuantity;
    private Button saveButton;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        byPassActivity();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopUpDialog();
            }
        });
    }


    private void createPopUpDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.pop_up, null);
        item = view.findViewById(R.id.grocery_item);
        pQuantity = view.findViewById(R.id.quantity);
        saveButton = view.findViewById(R.id.saveButton);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!item.getText().toString().isEmpty() && !pQuantity.getText().toString().isEmpty()) {
                    saveProductToDB(v);
                }
            }
        });

    }

    private void saveProductToDB(View v) {
        Product itemLocal = new Product();
        String newGrocery = item.getText().toString();
        String newGroceryQty = pQuantity.getText().toString();

        Toast.makeText(getApplicationContext(), "Name" + newGrocery + "Quantity" + newGroceryQty, Toast.LENGTH_LONG).show();

        itemLocal.setName(newGrocery);
        itemLocal.setQty(newGroceryQty);

        db.addGrocery(itemLocal);
        Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        }, 1000);

    }

    public void byPassActivity() {
        if (db.getGroceryCount() > 0) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }
}