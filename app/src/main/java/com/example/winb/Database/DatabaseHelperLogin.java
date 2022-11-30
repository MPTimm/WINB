package com.example.winb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.lights.LightsManager;

import androidx.annotation.Nullable;

import com.example.winb.Model.Constants;
import com.example.winb.Model.User;

import java.util.List;

public class DatabaseHelperLogin extends SQLiteOpenHelper {

    private Context ctx;

    public DatabaseHelperLogin(@Nullable Context context) {
        super(context, Constants.DB_NAME_LOGIN, null, 1);
        this.ctx = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseHelperLogin.CreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DatabaseHelperLogin.DeleteQuery);
        onCreate(sqLiteDatabase);
    }

    //Create querys to implement "create table" and "delete"

    public static String CreateQuery = "CREATE TABLE " + Constants.TABLE_NAME_USER + " ( "
            + Constants.KEY_ID
            + "INTEGER PRIMARY KEY,"
            + Constants.KEY_USER_NAME
            + " TEXT,"
            + Constants.KEY_PASSWORD
            + " TEXT)";

    public static String DeleteQuery = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME_USER;

    public void Register (Context context, User user){

        SQLiteDatabase db = new DatabaseHelperLogin(context).getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(Constants.KEY_USER_NAME, user.getUsername());
        Values.put(Constants.KEY_PASSWORD, user.getPassword());
        db.insert(Constants.TABLE_NAME_USER, null, Values);
        db.close();
    }

    public User Authentication(Context context, User user){

        SQLiteDatabase db = new DatabaseHelperLogin(context).getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME_USER, new String[]
                {
                        Constants.KEY_ID, Constants.KEY_USER_NAME + "=?", new String[] { user.getUsername(), null, null, null}
                });
        if(cursor != null && cursor.moveToFirst() && cursor.getCount() > 0)
        {
            User user1 = new User(cursor.getString(2));
            return user1;
        }
        return null;
    }

    public List<User> getUser(Context context){

        List<User> users = new List<User>();
        SQLiteDatabase db = new DatabaseHelperLogin(context).getReadableDatabase();
        String[] columns = new String[] {Constants.KEY_ID,Constants.KEY_USER_NAME,Constants.KEY_PASSWORD};
        Cursor cursor = db.query(Constants.TABLE_NAME_USER, columns, null, null, null, null, null,
        {
            while (cursor.moveToNext())
            {
                    users.add(new User
                {
                        ID = cursor.getString(cursor.getColumnIndexOrThrow(Constants.KEY_ID)),
                        Username = cursor.getString(cursor.getColumnIndexOrThrow(Constants.KEY_USER_NAME)),
                        Password = cursor.getString(cursor.getColumnIndexOrThrow(Constants.KEY_PASSWORD)),

                });
            }
            db.close();
            return users;
        }
    }


}
