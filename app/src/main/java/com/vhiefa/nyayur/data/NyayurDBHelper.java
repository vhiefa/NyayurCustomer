package com.vhiefa.nyayur.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import com.vhiefa.nyayur.data.NyayurContract.ProdukEntry;
//import com.vhiefa.nyayur.data.NyayurContract.TukangEntry;
import com.vhiefa.nyayur.data.NyayurContract.KetersediaanEntry;

/**
 * Created by afifatul on 2016-12-03.
 */

public class NyayurDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "nyayur.db";

    public NyayurDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_KETERSEDIAAN_TABLE = "CREATE TABLE " + KetersediaanEntry.TABLE_NAME + " (" +
                KetersediaanEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KetersediaanEntry.COLUMN_ID_KETERSEDIAAN + " TEXT NOT NULL, " +
                KetersediaanEntry.COLUMN_TUKANG_ID + " TEXT NOT NULL, " +
                KetersediaanEntry.COLUMN_ITEM_CODE + " TEXT NOT NULL," +
                KetersediaanEntry.COLUMN_HARGA + " TEXT NOT NULL, "+
                KetersediaanEntry.COLUMN_TANGGAL + " TEXT NOT NULL, " +
                KetersediaanEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                KetersediaanEntry.COLUMN_SUBCATEGORY + " TEXT NOT NULL," +
                KetersediaanEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "+
                KetersediaanEntry.COLUMN_ICON_CODE + " TEXT NOT NULL, " +


                 "UNIQUE (" + KetersediaanEntry.COLUMN_ID_KETERSEDIAAN + ") ON CONFLICT REPLACE);";

                // To assure the application have just one Tukang
                // per item, it's created a UNIQUE constraint with REPLACE strategy
              //  " UNIQUE (" + KetersediaanEntry.COLUMN_TUKANG_ID + ", " +
              //  KetersediaanEntry.COLUMN_ITEM_CODE + ") ON CONFLICT REPLACE);";

 /*       final String SQL_CREATE_PRODUK_TABLE = "CREATE TABLE " + ProdukEntry.TABLE_NAME + " (" +
                ProdukEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ProdukEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                ProdukEntry.COLUMN_SUBCATEGORY + " TEXT NOT NULL, " +
                ProdukEntry.COLUMN_ITEM_CODE + " TEXT NOT NULL," +
                ProdukEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL," +
                ProdukEntry.COLUMN_ICON_CODE + " TEXT NOT NULL," +

                "UNIQUE (" + ProdukEntry.COLUMN_ITEM_CODE + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_TUKANG_TABLE = "CREATE TABLE " + TukangEntry.TABLE_NAME + " (" +
                TukangEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TukangEntry.COLUMN_TUKANG_UNIQUE_ID+ " TEXT NOT NULL, " +
                TukangEntry.COLUMN_NAMA + " TEXT NOT NULL, " +
                TukangEntry.COLUMN_NOPE + " TEXT NOT NULL, " +
                TukangEntry.COLUMN_ALAMAT+ " TEXT NOT NULL, " +

                "UNIQUE (" + TukangEntry.COLUMN_TUKANG_UNIQUE_ID + ") ON CONFLICT REPLACE);";*/

     //   sqLiteDatabase.execSQL(SQL_CREATE_TUKANG_TABLE);
     //   sqLiteDatabase.execSQL(SQL_CREATE_PRODUK_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_KETERSEDIAAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.

      //  sqLiteDatabase.execSQL(" DROP TABLE IF EXIST " + ProdukEntry.TABLE_NAME);
       // sqLiteDatabase.execSQL(" DROP TABLE IF EXIST " + TukangEntry.TABLE_NAME);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXIST " + KetersediaanEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

