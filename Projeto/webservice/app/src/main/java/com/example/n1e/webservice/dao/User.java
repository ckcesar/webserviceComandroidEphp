package com.example.n1e.webservice.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.n1e.webservice.modelo.UserPass;

import java.util.ArrayList;

/**
 * Created by n1e on 28/04/17.
 */

public class User extends SQLiteOpenHelper {

    private static final String BD = "PIXCUBEV1_DB";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "USER";
    private static final String[] COLUNAS = new String[] {"CODIGO", "ID", "EMAIL", "PASSWORD_HASH", "CUSTOMER_ID"};
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME +
            " ( CODIGO INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID TEXT NOT NULL, " +
            "EMAIL TEXT NOT NULL, " +
            "PASSWORD_HASH TEXT NOT NULL, " +
            "CUSTOMER_ID TEXT NOT NULL);";


    public User(Context ctx) {
        super(ctx, BD, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }


    public void add (UserPass a) {
        ContentValues cv = new ContentValues();
        cv.put("ID", a.getId());
        cv.put("EMAIL", a.getEmail());
        cv.put("PASSWORD_HASH", a.getPassword_hash());
        cv.put("CUSTOMER_ID", a.getCustomer_id());

        getWritableDatabase().insert(TABLE_NAME, null, cv);
    }


    public void update (UserPass a) {
        ContentValues cv = new ContentValues();
        cv.put("CODIGO", a.getCodigo());
        cv.put("ID", a.getId());
        cv.put("EMAIL", a.getEmail());
        cv.put("PASSWORD_HASH", a.getPassword_hash());
        cv.put("CUSTOMER_ID", a.getCustomer_id());

        getWritableDatabase().update(TABLE_NAME, cv, "ID=?", new String[] {String.valueOf(a.getId())});
    }

    public String getString(String var){
        Cursor c = getWritableDatabase().rawQuery("SELECT ID FROM USER WHERE ID="+var, null);
           if(c.moveToNext()){
               return"1";
           }else{
               return"2";
           }
    }

    public String Logar(String email, String senha){
        Cursor c = getWritableDatabase().rawQuery("SELECT ID,EMAIL,PASSWORD_HASH FROM USER WHERE EMAIL=\'"+email+"\'" +" AND PASSWORD_HASH=\'"+senha+"\'", null);
           if(c.moveToNext()){
               return"1";
           }else{
               return"2";
           }
    }


}
