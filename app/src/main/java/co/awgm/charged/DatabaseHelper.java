package co.awgm.charged;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Andrew on 30/10/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    final static String M = "DATABASE_HELPER";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "chargedPlaces.db";
    private static final String TABLE_PLACES = "places";

    private static final String KEY_ID = "id";
    private static final String KEY_LOCATION_CODE = "locationCode";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    private static final String KEY_NAME = "name";
    private static final String KEY_SIGNAGE = "signage";
    private static final String KEY_INFO = "info";
    private static final String KEY_ICON_FILENAME = "iconFileName";
    private static final String KEY_CONTAINER_ID = "containerId";
    private static final String KEY_CONTAINER_NAME = "containerName";
    private static final String KEY_CATEGORY_ID = "categoryId";
    private static final String KEY_CATEGORY_HANDLE = "categoryHandle";
    private static final String KEY_KEYWORDS = "keywords";






    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d(M, "Default Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(M, "On Create()");
        String CREATE_PLACES_TABLE = "CREATE TABLE " + TABLE_PLACES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_LOCATION_CODE + " TEXT,"
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
                + KEY_KEYWORDS + " TEXT"
                + ")";
        db.execSQL( CREATE_PLACES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(M, "On Upgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);

        onCreate(db);
    }

    void addPlace(ChargedPlace place){
        Log.d(M, "Add Place");
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(KEY_LOCATION_CODE, place.getLocationCode());
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
        values.put(KEY_KEYWORDS, place.getKeywords());

        db.insert(TABLE_PLACES, null, values);
        db.close();
    }

    public ChargedPlace getPlace(int id){
        Log.d(M, "GetPlace");

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACES, new String[]{
                KEY_ID,
                KEY_LOCATION_CODE,
                KEY_LAT,
                KEY_LNG,
                KEY_NAME,
                KEY_SIGNAGE,
                KEY_INFO,
                KEY_ICON_FILENAME,
                KEY_CONTAINER_ID,
                KEY_CONTAINER_NAME,
                KEY_CATEGORY_ID,
                KEY_CATEGORY_HANDLE,
                KEY_KEYWORDS}, KEY_ID + "=?", new String[]{String.valueOf(id)},null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        ChargedPlace place = new ChargedPlace(
                //Integer.parseInt(cursor.getString(0)),
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
                cursor.getString(11),
                cursor.getString(12));

        return place;
    }

    public ArrayList<ChargedPlace> getChargedPlaces(){
        Log.d(M, "getChargedPlaces");

        ArrayList<ChargedPlace> placesList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PLACES;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                ChargedPlace place = new ChargedPlace();
                        //place.setID(Integer.parseInt(cursor.getString(0)));
                        place.setLocationCode(cursor.getString(1));
                        place.setLat(cursor.getString(2));
                        place.setLng(cursor.getString(3));
                        place.setName(cursor.getString(4));
                        place.setSignage(cursor.getString(5));
                        place.setInfo(cursor.getString(6));
                        place.setIconFileName(cursor.getString(7));
                        place.setContainerId(cursor.getString(8));
                        place.setContainerName(cursor.getString(9));
                        place.setCategoryId(cursor.getString(10));
                        place.setCategoryHandle(cursor.getString(11));
                        place.setKeywords(cursor.getString(12));

                placesList.add(place);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return placesList;
    }
    public int updatePlace(ChargedPlace place){
        //Log.d(M, "UpdatePlace");
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LOCATION_CODE, place.getLocationCode());
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
        values.put(KEY_KEYWORDS, place.getKeywords());
        int result = db.update(TABLE_PLACES, values, KEY_ID + "=?",new String[]{String.valueOf(place.getID())});
        return result;


    }
    public void deletePlace (ChargedPlace place){
        Log.d(M, "DeletePlace");
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PLACES, KEY_ID + "=?", new String[]{String.valueOf(place.getID())});
        db.close();
    }

    public int getPlacesCount(){
        Log.d(M, "GetPlacesCount");
        String countQuery = "SELECT * FROM " + TABLE_PLACES;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int c = cursor.getCount();
        cursor.close();

        return c;
    }

    //public List<ChargedPlace> getAllPlaces() {
    //    Log.d(M, "GetAllPlaces");
    //    List<ChargedPlace> placesList = new ArrayList<>();
    //
    //    String selectQuery = "SELECT * FROM " + TABLE_PLACES;
    //
    //    SQLiteDatabase db = this.getWritableDatabase();
    //
    //    Cursor cursor = db.rawQuery(selectQuery, null);
    //
    //    if (cursor.moveToFirst()) {
    //        do {
    //            ChargedPlace place = new ChargedPlace();
    //            place.setLocationCode(cursor.getString(0));
    //            place.setLat(cursor.getString(2));
    //            place.setLng(cursor.getString(3));
    //            place.setName(cursor.getString(1));
    //            place.setSignage(cursor.getString(4));
    //            place.setInfo(cursor.getString(5));
    //            place.setIconFileName(cursor.getString(6));
    //            place.setContainerName(cursor.getString(7));
    //            place.setCategoryId(cursor.getString(8));
    //            place.setCategoryHandle(cursor.getString(9));
    //            place.setKeywords(cursor.getString(10));
    //
    //            placesList.add(place);
    //        } while (cursor.moveToNext());
    //    }
    //
    //    return placesList;
    //
    //}

    public void loadMarkersFromFile(Context context) {
        Log.d(M, "loadMarkersFromFile");

//        JsonFileReader JsonFileReader = new JsonFileReader();
//
//        ArrayList<ChargedPlace> arrayList = JsonFileReader.ReadJsonFile(context);
//
//        for (int i = 0; i < arrayList.size(); i++){
//            addPlace(arrayList.get(i));
//        }

        addTestPlaces();
    }





    private void addTestPlaces() {

        Log.d(M, "...Adding Places...");


        addPlace(
                new ChargedPlace(
                /*locationCode*/"12202003B",
                /*lat*/"-32.066105",
                /*lng*/"115.837124",
                /*name*/"Engineering",
                /*signage*/"220.2.003B",
                /*info*/ "Room 003B, Level 2, Engineering and Energy Building, Murdoch University (Murdoch Campus), South Street, Murdoch",
                /*icon_file_name*/"office.png",
                /*container_id*/"1220",
                /*container_name*/"220 - Engineering and Energy",
                /*category_id*/"26",
                /*category_handle*/"academic-offices",
                /*keywords*/"EEB2.003B"));


        addPlace(
                new ChargedPlace(
                /*locationCode*/"12202003C",
                /*lat*/"-32.066105",
                /*lng*/"115.837149",
                /*name*/"Engineering",
                /*signage*/"220.2.003C",
                /*info*/ "Room 003C, Level 2, Engineering and Energy Building, Murdoch University (Murdoch Campus), South Street, Murdoch",
                /*icon_file_name*/"office.png",
                /*container_id*/"1220",
                /*container_name*/"220 - Engineering and Energy",
                /*category_id*/"26",
                /*category_handle*/"academic-offices",
                /*keywords*/"EEB2.003C"));

        addPlace(
                new ChargedPlace(
                /*locationCode*/"12202003D",
                /*lat*/"-32.066105",
                /*lng*/"115.837124",
                /*name*/"Engineering",
                /*signage*/"220.2.003D",
                /*info*/ "Room 003D, Level 2, Engineering and Energy Building, Murdoch University (Murdoch Campus), South Street, Murdoch\n",
                /*icon_file_name*/"office.png",
                /*container_id*/"1220",
                /*container_name*/"220 - Engineering and Energy",
                /*category_id*/"26",
                /*category_handle*/"academic-offices",
                /*keywords*/"EEB2.003D"));

        addPlace(
                new ChargedPlace(
                /*locationCode*/"12202003E",
                /*lat*/"-32.066105",
                /*lng*/"115.837124",
                /*name*/"Engineering",
                /*signage*/"220.2.003D",
                /*info*/ "Room 003D, Level 2, Engineering and Energy Building, Murdoch University (Murdoch Campus), South Street, Murdoch",
                /*icon_file_name*/"office.png",
                /*container_id*/"1220",
                /*container_name*/"220 - Engineering and Energy",
                /*category_id*/"26",
                /*category_handle*/"academic-offices",
                /*keywords*/"12202003E"));

        addPlace(
                new ChargedPlace(
                /*locationCode*/"12202003H",
                /*lat*/"-32.066106",
                /*lng*/"115.837225",
                /*name*/"Engineering",
                /*signage*/"220.2.003E",
                /*info*/ "Room 003E, Level 2, Engineering and Energy Building, Murdoch University (Murdoch Campus), South Street, Murdoch",
                /*icon_file_name*/"office.png",
                /*container_id*/"1220",
                /*container_name*/"220 - Engineering and Energy",
                /*category_id*/"26",
                /*category_handle*/"academic-offices",
                /*keywords*/"EEB2.003E"));






    }




    //https://www.youtube.com/watch?v=K6cYSNXb9ew&ab_channel=TihomirRAdeff
}
