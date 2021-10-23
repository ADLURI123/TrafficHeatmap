package com.abhinay.trafficheatmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "trafficdb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "location";
    private static final String NAME_COL = "locname";
    private static final String LATITUDE_COL = "latitude";
    private static final String LONGITUDE_COL = "longitude";
    private static final String INTENSITY_COL = "intensity";
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + NAME_COL + " TEXT, "
                + LATITUDE_COL + " REAL,"
                + LONGITUDE_COL + " REAL,"
                + INTENSITY_COL + " REAL)";
        db.execSQL(query);
    }

    public void addNewCourse(String location, Double latitude,Double longitude,Double intensity) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL,location);
        values.put(LATITUDE_COL,latitude);
        values.put(LONGITUDE_COL,longitude);
        values.put(INTENSITY_COL,intensity);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<location> readLocations()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<location> ArrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                ArrayList.add(new location(cursor.getString(0),
                        cursor.getDouble(1),
                        cursor.getDouble(2),
                        cursor.getDouble(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

