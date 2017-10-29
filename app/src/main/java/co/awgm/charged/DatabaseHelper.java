package co.awgm.charged;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 30/10/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "chargedPlaces";
    private static final String TABLE_PLACES = "places";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "Lng";
    private static final String KEY_NAME = "name";
    private static final String KEY_SIGNAGE = "signage";
    private static final String KEY_INFO = "info";
    private static final String KEY_ICON_FILENAME = "iconFileName";
    private static final String KEY_CONTAINER_ID = "containerId";
    private static final String KEY_CONTAINER_NAME = "containerName";
    private static final String KEY_CATEGORY_ID = "categoryId";
    private static final String KEY_CATEGORY_HANDLE = "categoryHandle";
    private static final String KEY_LOCATION_CODE = "locationCode";
    private static final String KEY_KEYWORDS = "keywords";






    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLACES_TABLE = "CREATE TABLE" + TABLE_PLACES + "("
                + KEY_LOCATION_CODE + " TEXT PRIMARY KEY,"
                + KEY_LAT + " TEXT,"
                + KEY_LNG + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_SIGNAGE + " TEXT,"
                + KEY_INFO + " TEXT,"
                + KEY_ICON_FILENAME + " TEXT,"
                + KEY_CONTAINER_ID + " TEXT,"
                + KEY_CONTAINER_NAME + " TEXT,"
                + KEY_CATEGORY_ID + " TEXT,"
                + KEY_CATEGORY_HANDLE + " TEXT,"
                + KEY_LOCATION_CODE + " TEXT,"
                + KEY_KEYWORDS + " TEXT,"
                + ")";
        db.execSQL( CREATE_PLACES_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);

        onCreate(db);
    }

    void addPlace(ChargedPlace place){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LAT, place.getLat());
        values.put(KEY_LNG, place.getLng());
        values.put(KEY_NAME, place.getName());
        values.put(KEY_SIGNAGE, place.getSignage());
        values.put(KEY_INFO, place.getInfo());
        values.put(KEY_ICON_FILENAME, place.getIconFileName());
        values.put(KEY_CONTAINER_ID, place.getContainerId());
        values.put(KEY_CONTAINER_NAME, place.getContainerName());
        values.put(KEY_CATEGORY_ID, place.getCategoryId());
        values.put(KEY_CATEGORY_HANDLE, place.getCategoryHandle());
        values.put(KEY_LOCATION_CODE, place.getLocationCode());
        values.put(KEY_KEYWORDS, place.getKeywords());

        db.insert(TABLE_PLACES, null, values);
        db.close();
    }

    ChargedPlace getPlace(String locationId){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACES, new String[]{
                KEY_LOCATION_CODE,
                KEY_NAME,
                KEY_LAT,
                KEY_LNG,
                KEY_SIGNAGE,
                KEY_INFO,
                KEY_ICON_FILENAME,
                KEY_CONTAINER_ID,
                KEY_CONTAINER_NAME,
                KEY_CATEGORY_ID,
                KEY_CATEGORY_HANDLE,
                KEY_KEYWORDS}, KEY_LOCATION_CODE + "=?", new String[]{locationId},null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        ChargedPlace place = new ChargedPlace(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9),
                cursor.getString(10),
                cursor.getString(11));

        return place;
    }

    public List<ChargedPlace> getChargedPlaces(){

        List<ChargedPlace> placesList = new ArrayList<>();

        String selectQuery = "SELECT *FROM" + TABLE_PLACES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                ChargedPlace place = new ChargedPlace();
                        place.setLocationCode(cursor.getString(0));
                        place.setName(cursor.getString(1));
                        place.setLat(cursor.getString(2));
                        place.setLng(cursor.getString(3));
                        place.setSignage(cursor.getString(4));
                        place.setInfo(cursor.getString(5));
                        place.setIconFileName(cursor.getString(6));
                        place.setContainerId(cursor.getString(7));
                        place.setContainerName(cursor.getString(8));
                        place.setCategoryId(cursor.getString(9));
                        place.setCategoryHandle(cursor.getString(10));
                        place.setKeywords(cursor.getString(11));

                placesList.add(place);
            } while (cursor.moveToNext());
        }

        return placesList;
    }


    //https://www.youtube.com/watch?v=K6cYSNXb9ew&ab_channel=TihomirRAdeff
    //
    // 20:00/29:34
}
