package com.example.winb.View;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.winb.R;

public class FillActivity extends AppCompatActivity {

    private TextView itemName, quantity, dateAdded;
    private int productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        itemName = findViewById(R.id.productName);
        quantity = findViewById(R.id.productQty);
        dateAdded = findViewById(R.id.productDateAdded);

        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){
            itemName.setText(bundle.getString("name"));
            quantity.setText(bundle.getString("quantity"));
            dateAdded.setText(bundle.getString("date"));

            productId = bundle.getInt("id");
        }
    }
}
