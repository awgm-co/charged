package co.awgm.charged;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Andrew on 30/10/2017.
 */

public class PlacesDatabaseHelper extends SQLiteOpenHelper {

    XmlPullParserFactory pullParserFactory;

    final static String M = "PLACES_DATABASE_HELPER";

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
    private Context context;





    public PlacesDatabaseHelper(Context _context) {
        super(_context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(M, "Default Constructor");

        context = _context;
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
        //Log.d(M, "Add Place");
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
        values.put(KEY_CATEGORY_HANDLE, place.getCategoryName());
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
                        place.setID(Integer.parseInt(cursor.getString(0)));
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
                        place.setCategoryName(cursor.getString(11));
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
        values.put(KEY_CATEGORY_HANDLE, place.getCategoryName());
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

    public ArrayList<ChargedPlace> getAllPlaces() {
        Log.d(M, "GetAllPlaces");
        ArrayList<ChargedPlace> placesList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PLACES;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                ChargedPlace place = new ChargedPlace();
            /*ID*/
                //String zero = cursor.getString(0);
                //Log.d(M,"0 " + zero);

            /*Location Code*/
                //String one = cursor.getString(1);
                //Log.d(M,"1 " + one);
                place.setLocationCode(cursor.getString(1));

            /*Latitude*/
                //String two = cursor.getString(2);
                //Log.d(M,"2 " + two);
                place.setLat(cursor.getString(2));

            /*Longitude*/
                //String three = cursor.getString(3);
                //Log.d(M,"3 " + three);
                place.setLng(cursor.getString(3));

            /*Name*/
                //String four = cursor.getString(4);
                //Log.d(M,"4 " + four);
                place.setName(cursor.getString(4));

            /*Signage*/
                //Log.d(M,"5 " + five);
                //place.setSignage(cursor.getString(5));
                String five = cursor.getString(5);

            /*Info*/
                //String six = cursor.getString(6);
                //Log.d(M,"6 " + six);
                place.setInfo(cursor.getString(6));

            /*Icon File Name*/
                //String seven = cursor.getString(7);
                //Log.d(M,"7 " + seven);
                place.setIconFileName(cursor.getString(7));

            /*Container ID*/
                //String eight = cursor.getString(8);
                //Log.d(M,"8 " + eight);
                place.setContainerId(cursor.getString(8));

            /*Container Name*/
                //String nine = cursor.getString(9);
                //Log.d(M,"9 " + nine);
                place.setContainerName(cursor.getString(9));

            /*CategoryID*/
                //String ten = cursor.getString(10);
                //Log.d(M,"10 " + ten);
                place.setCategoryId(cursor.getString(10));

            /*CategoryName*/
                //String eleven = cursor.getString(11);
                //Log.d(M,"11 " + eleven);
                place.setCategoryName(cursor.getString(11));

            /*Keywords*/
                //String twelve = cursor.getString(12);
                //Log.d(M,"12 " + twelve);
                place.setKeywords(cursor.getString(12));

                placesList.add(place);

            } while (cursor.moveToNext());
        }

        return placesList;

    }

    public void loadMarkersFromFile() {
        Log.d(M, "LOADING MARKERS FROM FILE...");

        AssetManager assetManager = context.getAssets();
        XmlPullParserFactory xmlFactoryObject;
        ArrayList<ChargedPlace> places = null;

        try {
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlFactoryObject.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            InputStream inputStream = assetManager.open("charged_map_markers.xml");
            parser.setInput(inputStream,null);


            places = parseXML(parser);

        } catch (XmlPullParserException e)
            {
                e.printStackTrace();
        } catch (IOException e)
            {
                e.printStackTrace();
        }

        for (ChargedPlace p: places) {
            addPlace(p);
            //Log.d(M, p.getLocationCode());
        }
    }


    private ArrayList<ChargedPlace> parseXML(XmlPullParser parser)
            throws XmlPullParserException,IOException
    {
        Log.d(M, "parseXML()");

        ArrayList<ChargedPlace> places = null;
        int eventType = parser.getEventType();
        ChargedPlace place = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            //Log.d(M, "eventType != XmlPullParser.END_DOCUMENT");
            String name = null;

            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    //Log.d(M, "XmlPullParser.START_DOCUMENT");
                    places = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    //Log.d(M, name);
                    if (name.equals("place")) {
                        //Log.d(M, "New Place");
                        place = new ChargedPlace();
                    }
                    if (place != null) {

                        if (name.equals("category_id")) {
                            place.setCategoryId(parser.nextText());
                            //Log.d(M, name + ": " + place.getCategoryId());
                        }
                        if (name.equals("category_name")) {
                            place.setCategoryName(parser.nextText());
                            //Log.d(M, name + ": " + place.getCategoryName());
                        }
                        if (name.equals("container_id")) {
                            place.setContainerId(parser.nextText());
                            //Log.d(M, name + ": " + place.getContainerId());
                        }
                        if (name.equals("container_name")) {
                            place.setContainerName(parser.nextText());
                            //Log.d(M, name + ": " + place.getContainerName());
                        }
                        if (name.equals("icon_file_name")) {
                            place.setIconFileName(parser.nextText());
                            //Log.d(M, name + ": " + place.getIconFileName());
                        }
                        if (name.equals("info")) {
                            place.setInfo(parser.nextText());
                            //Log.d(M, name + ": " + place.getInfo());
                        }
                        if (name.equals("keywords")) {
                            place.setKeywords(parser.nextText());
                            //Log.d(M, name + ": " + place.getKeywords());
                        }
                        if (name.equals("lat")) {
                            place.setLat(parser.nextText());
                            //Log.d(M, name + ": " + place.getLat());
                        }
                        if (name.equals("lng")) {
                            place.setLng(parser.nextText());
                            //Log.d(M, name + ": " + place.getLng());
                        }
                        if (name.equals("location_code")) {
                            place.setLocationCode(parser.nextText());
                            //Log.d(M, name + ": " + place.getLocationCode());
                        }
                        if (name.equals("name")) {
                            place.setName(parser.nextText());
                            //Log.d(M, name + ": " + place.getName());
                        }
                        if (name.equals("signage")) {
                            place.setSignage(parser.nextText());
                            //Log.d(M, name + ": " + place.getSignage());
                        }
                    }
                    break;

                case XmlPullParser.END_TAG:
                    //Log.d(M, "END_TAG");
                    name = parser.getName();
                    if (name.equalsIgnoreCase("place") && place != null) {
                        if (places != null) {
                            places.add(place);
                        } else{
                            Log.d(M,"place was null");
                        }
                    }
            }
            eventType = parser.next();
        }

        return places;
    }



    public void LoadTestMarkers (){
        Log.d(M, "LoadTestMarkers");
        addTestPlaces();
    }
    private void addTestPlaces() {

        Log.d(M, "...Adding Test Places...");


        addPlace(
                new ChargedPlace(
                "12202003B",
                "-32.066105",
                "115.837124",
                "Engineering",
                "220.2.003B",
                "Room 003B, Level 2, Engineering and Energy Building, Murdoch University (Murdoch Campus), South Street, Murdoch",
                "office.png",
                "1220",
                "220 - Engineering and Energy",
                "26",
                "academic-offices",
                "EEB2.003B"));


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
