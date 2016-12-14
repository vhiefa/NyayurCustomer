package com.vhiefa.nyayur.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

//import com.vhiefa.nyayur.data.NyayurContract.ProdukEntry;
//import com.vhiefa.nyayur.data.NyayurContract.TukangEntry;
import com.vhiefa.nyayur.data.NyayurContract.KetersediaanEntry;

/**
 * Created by afifatul on 2016-12-03.
 */

public class NyayurProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();


    private NyayurDBHelper mOpenHelper;

    static final int KETERSEDIAAN = 100;
    static final int KETERSEDIAAN_WITH_TUKANG = 101;
    static final int KETERSEDIAAN_WITH_TUKANG_AND_CATEGORY = 102;
    static final int KETERSEDIAAN_WITH_TUKANG_AND_CATEGORY_AND_SUBCATEGORY = 103;
    static final int KETERSEDIAAN_WITH_TUKANG_AND_CATEGORY_AND_SUBCATEGORY_AND_ID = 104;
   // static final int PRODUK = 200;
   // static final int TUKANG = 300;

    private static final SQLiteQueryBuilder sKetersediaanBuilder ;// sProdukBuilder, sTukangBuilder;

    static{
        sKetersediaanBuilder = new SQLiteQueryBuilder();
        sKetersediaanBuilder.setTables(
                KetersediaanEntry.TABLE_NAME
        );


      /*  sProdukBuilder = new SQLiteQueryBuilder();
        sTukangBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //produk INNER JOIN tukang ON produk.item_code = ketersediaan.item_code
        sKetersediaanByProdukBuilder.setTables(
                KetersediaanEntry.TABLE_NAME + " INNER JOIN " +
                        ProdukEntry.TABLE_NAME +
                        " ON " + KetersediaanEntry.TABLE_NAME +
                        "." + KetersediaanEntry.COLUMN_ITEM_CODE +
                        " = " + ProdukEntry.TABLE_NAME +
                        "." + ProdukEntry.COLUMN_ITEM_CODE);

        sProdukBuilder.setTables(
                ProdukEntry.TABLE_NAME
        );

        sTukangBuilder.setTables(
                TukangEntry.TABLE_NAME
        );*/

    }


    private static final String sTukangAndCategoryAndSubCategorySelection =
            KetersediaanEntry.TABLE_NAME+
                    "."+KetersediaanEntry.COLUMN_TUKANG_ID + " = ? AND " +
                    KetersediaanEntry.COLUMN_CATEGORY + " = ? AND " +
                    KetersediaanEntry.COLUMN_SUBCATEGORY + " = ? ";

    private static final String sKetersediaanByIdSelection =
            KetersediaanEntry.TABLE_NAME+
                    "."+ KetersediaanEntry.COLUMN_ID_KETERSEDIAAN + " = ? ";


    private Cursor getKetersediaanByTukangAndCategoryAndSubcategory(Uri uri, String[] projection, String sortOrder) {
        String tukang = KetersediaanEntry.getTukangFromUri(uri);
        String category = KetersediaanEntry.getCategoryFromUri(uri);
        String subcategory = KetersediaanEntry.getSubCategoryFromUri(uri);

        String[] selectionArgs;
        String selection;

        selectionArgs = new String[]{tukang, category, subcategory};
        selection = sTukangAndCategoryAndSubCategorySelection;

        return sKetersediaanBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getKetersediaanById (Uri uri, String[] projection, String sortOrder) {
        String id = KetersediaanEntry.getIdFromUri(uri);

        String[] selectionArgs;
        String selection;

        selectionArgs = new String[]{ id};
        selection = sKetersediaanByIdSelection;

        return sKetersediaanBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = NyayurContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, NyayurContract.PATH_KETERSEDIAAN, KETERSEDIAAN);
        matcher.addURI(authority, NyayurContract.PATH_KETERSEDIAAN+ "/*", KETERSEDIAAN_WITH_TUKANG);
        matcher.addURI(authority, NyayurContract.PATH_KETERSEDIAAN + "/*/*", KETERSEDIAAN_WITH_TUKANG_AND_CATEGORY);
        matcher.addURI(authority, NyayurContract.PATH_KETERSEDIAAN + "/*/*/*", KETERSEDIAAN_WITH_TUKANG_AND_CATEGORY_AND_SUBCATEGORY);
        matcher.addURI(authority, NyayurContract.PATH_KETERSEDIAAN + "/*/*/*/*", KETERSEDIAAN_WITH_TUKANG_AND_CATEGORY_AND_SUBCATEGORY_AND_ID);

        return matcher;
    }


    /*
        Students: We've coded this for you.  We just create a new WeatherDbHelper for later use
        here.
     */
    @Override
    public boolean onCreate() {
        mOpenHelper = new NyayurDBHelper(getContext());
        return true;
    }

    /*
        Students: Here's where you'll code the getType function that uses the UriMatcher.  You can
        test this by uncommenting testGetType in TestProvider.

     */
    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case KETERSEDIAAN_WITH_TUKANG_AND_CATEGORY_AND_SUBCATEGORY_AND_ID:
                return KetersediaanEntry.CONTENT_ITEM_TYPE;
            case KETERSEDIAAN_WITH_TUKANG_AND_CATEGORY_AND_SUBCATEGORY:
                return KetersediaanEntry.CONTENT_TYPE;
            case KETERSEDIAAN_WITH_TUKANG_AND_CATEGORY:
                return KetersediaanEntry.CONTENT_TYPE;
            case KETERSEDIAAN_WITH_TUKANG:
                return KetersediaanEntry.CONTENT_TYPE;
            case KETERSEDIAAN:
                return KetersediaanEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case KETERSEDIAAN_WITH_TUKANG_AND_CATEGORY_AND_SUBCATEGORY_AND_ID:
            {
                retCursor = getKetersediaanById(uri, projection, sortOrder);
                break;
            }

            case KETERSEDIAAN_WITH_TUKANG_AND_CATEGORY_AND_SUBCATEGORY: {
                retCursor = getKetersediaanByTukangAndCategoryAndSubcategory(uri, projection, sortOrder);
                break;
            }
            // "ketersediaan"
            case KETERSEDIAAN: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KetersediaanEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /*
        Student: Add the ability to insert Locations to the implementation of this function.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case KETERSEDIAAN: {
                //normalizeDate(values);
                long _id = db.insert(KetersediaanEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = KetersediaanEntry.buildKetersediaanUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
      /*      case PRODUK: {
                long _id = db.insert(ProdukEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = ProdukEntry.buildProdukUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TUKANG: {
                long _id = db.insert(TukangEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = TukangEntry.buildTukangUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            } */
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case KETERSEDIAAN:
                rowsDeleted = db.delete(
                        KetersediaanEntry.TABLE_NAME, selection, selectionArgs);
                break;
    /*        case LOCATION:
                rowsDeleted = db.delete(
                        WeatherContract.LocationEntry.TABLE_NAME, selection, selectionArgs);
                break;*/
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }


    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case KETERSEDIAAN:
                rowsUpdated = db.update(KetersediaanEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
        /*    case LOCATION:
                rowsUpdated = db.update(WeatherContract.LocationEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;*/
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case KETERSEDIAAN:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                     //   normalizeDate(value);
                        long _id = db.insert(KetersediaanEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }


}
