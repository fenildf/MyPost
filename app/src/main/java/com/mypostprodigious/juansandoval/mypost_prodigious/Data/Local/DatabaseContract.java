package com.mypostprodigious.juansandoval.mypost_prodigious.Data.Local;


import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

public class DatabaseContract {

    public static final String CONTENT_AUTHORITY = "";
    public static final String CONTENT_SCHEME = "";
    public static final Uri BASE_CONTENT_URL = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);

    public static final String PATH_POST = "";

    public DatabaseContract() {
    }

    public static abstract class Post implements BaseColumns {

        @NonNull
        public static final String CONTENT_URI_STRING = "";
        public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);

        public static final String CONTENT_USER_TYPE = "" + CONTENT_AUTHORITY + "/" + PATH_POST;
        public static final String CONTENT_URER_ITEM_TYPE = "" + CONTENT_AUTHORITY + "/" + PATH_POST;

        public static final String TABLE_NAME = "";

        public static final String COLUMN_ID = "";
        public static final String COLUMN_USER_ID = "";
        public static final String COLUMN_TITLE = "";
        public static final String COLUMN_BODY = "";

        public static String getPostCreateQuery() {
            return "CREATE TABLE" + TABLE_NAME + "(" +
                    COLUMN_ID + "LONG NOT NULL PRIMARY KEY, " +
                    COLUMN_USER_ID + "LONG, " +
                    COLUMN_TITLE + "TEXT NOT NULL, " +
                    COLUMN_BODY + "TEXT NOT NULL, " + ")";
        }

        public static String getUserDeleteQuery() {
            return "DROP TABLE IF EXIST " + TABLE_NAME;
        }

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}

