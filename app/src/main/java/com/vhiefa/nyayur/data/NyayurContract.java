package com.vhiefa.nyayur.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by afifatul on 2016-12-03.
 */

public class NyayurContract {


        public static final String CONTENT_AUTHORITY = "com.vhiefa.nyayur";
        // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
        // the content provider.
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

   //     public static final String PATH_PRODUK = "produk";
   //     public static final String PATH_TUKANG = "tukang";
        public static final String PATH_KETERSEDIAAN = "ketersediaan";


    /* Inner class that defines the table contents of the ketersediaan table */
    public static final class KetersediaanEntry implements BaseColumns {

        private static final String LOG_TAG = KetersediaanEntry.class.getSimpleName();

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_KETERSEDIAAN).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_KETERSEDIAAN;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_KETERSEDIAAN;

        public static final String TABLE_NAME = "ketersediaan";

        //id_tersedia,id_tukangsayur, id_sayuran, harga, tanggal, kategori, subkategori, namaitem, icon
        public static final String COLUMN_ID_KETERSEDIAAN ="id_tersedia";
        public static final String COLUMN_TUKANG_ID = "id_tukang";
        public static final String COLUMN_ITEM_CODE = "id_sayuran";
        public static final String COLUMN_HARGA = "harga";
        public static final String COLUMN_TANGGAL= "tanggal";
        public static final String COLUMN_CATEGORY = "kategori";
        public static final String COLUMN_SUBCATEGORY = "subkategori";
        public static final String COLUMN_ITEM_NAME = "nama_item";
        public static final String COLUMN_ICON_CODE = "kode_icon";



        public static Uri buildKetersediaanUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

     /*   public static Uri buildKetersedianBasedTukangAndItemCode(String tukang, String item_code) {
            return CONTENT_URI.buildUpon().appendPath(tukang).appendPath(null).appendPath(null).appendQueryParameter(COLUMN_ITEM_CODE, item_code).build();
        } */

        public static Uri buildKetersedianBasedTukangAndCategoryAndSubCategory(String tukang, String category, String subcategory) {
            return CONTENT_URI.buildUpon().appendPath(tukang).appendPath(category).appendPath(subcategory).build();
        }

        public static String getTukangFromUri (Uri uri) {

            return uri.getPathSegments().get(1);
        }

        public static String getCategoryFromUri (Uri uri) {

            return uri.getPathSegments().get(2);
        }

        public static String getSubCategoryFromUri (Uri uri) {

            return uri.getPathSegments().get(3);
        }

   /*     public static String getIdFromUri (Uri uri) {

            return uri.getPathSegments().get(4);
        } */

        public static String getIdFromUri(Uri uri) {
            return uri.getQueryParameter(COLUMN_ID_KETERSEDIAAN);
        }

    }


        /* Inner class that defines the table contents of the produk table */
    /*    public static final class ProdukEntry implements BaseColumns {

            private static final String LOG_TAG = ProdukEntry.class.getSimpleName();

            public static final Uri CONTENT_URI =
                    BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUK).build();
            public static final String CONTENT_TYPE =
                    "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_PRODUK;
            public static final String CONTENT_ITEM_TYPE =
                    "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_PRODUK;

            public static final String TABLE_NAME = "produk";

            public static final String COLUMN_ITEM_NAME = "nama_item";
            public static final String COLUMN_CATEGORY = "kategori";
            public static final String COLUMN_SUBCATEGORY = "subkategori";
            public static final String COLUMN_ITEM_CODE = "kode_item";
            public static final String COLUMN_ICON_CODE = "kode_icon";



            public static Uri buildProdukUri(long id) {
                return ContentUris.withAppendedId(CONTENT_URI, id);
            }


            public static Uri buildProdukBasedItemCode(String item_code) {
                return CONTENT_URI.buildUpon().appendPath(null).appendPath(null).appendQueryParameter(COLUMN_ITEM_CODE, item_code).build();
            }


            public static String getCategoryFromUri (Uri uri) {
                return uri.getPathSegments().get(1);
            }

            public static String getSubCategoryFromUri (Uri uri) {
                return uri.getPathSegments().get(2);
            }


        }


    public static final class TukangEntry implements BaseColumns {

        private static final String LOG_TAG = TukangEntry.class.getSimpleName();

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TUKANG).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_TUKANG;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_TUKANG;

        public static final String TABLE_NAME = "tukang";

        public static final String COLUMN_TUKANG_UNIQUE_ID = "uid_tukang";
        public static final String COLUMN_NAMA = "nama";
        public static final String COLUMN_NOPE = "no_hp";
        public static final String COLUMN_ALAMAT = "alamat";


        public static Uri buildTukangUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);

        }

        public static String getTukangUniqueIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }*/
}
