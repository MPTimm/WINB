package com.example.winb.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.winb.Model.Constants;
import com.example.winb.Model.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context ctx;

    public DatabaseHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERY_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_GROCERY_ITEM + " TEXT,"
                + Constants.KEY_QTY + " TEXT,"
                + Constants.KEY_DATE + " LONG);";

        db.execSQL(CREATE_GROCERY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public void addGrocery(Product grocery) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constants.KEY_QTY, grocery.getQty());
        values.put(Constants.KEY_DATE, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, values);

        Log.d("Saved!", "Saved to db" + values);
    }


    //get Item count
    public int getGroceryCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    //update record
    public int updateGrocery(Product grocery) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constants.KEY_QTY, grocery.getQty());
        values.put(Constants.KEY_DATE, java.lang.System.currentTimeMillis());

        return db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=?", new String[]{
                String.valueOf(grocery.getId())
        });
    }

    //delete grocery
    public void deleteGrocery(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "= ?", new String[]{String.valueOf(id)});
        db.close();
    }

    //    get all grocery
    @SuppressLint("Range")
    public List<Product> getAllGrocery() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Product> groceryList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                Constants.KEY_ID, Constants.KEY_GROCERY_ITEM, Constants.KEY_QTY, Constants.KEY_DATE
        }, null, null, null, null, Constants.KEY_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                product.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
                product.setQty(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY)));

                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE))));

                product.setDateAdded(formatDate);

                groceryList.add(product);
            } while (cursor.moveToNext());
        }
        return groceryList;
    }
}
