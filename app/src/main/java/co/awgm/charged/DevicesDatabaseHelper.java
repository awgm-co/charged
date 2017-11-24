package co.awgm.charged;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserFactory;

import java.util.ArrayList;

/**
 * Created by Andrew on 30/10/2017.
 */

public class DevicesDatabaseHelper extends SQLiteOpenHelper {

    XmlPullParserFactory pullParserFactory;

    final static String M = "DEVICES_DATABASE_HELPER";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "chargedDevices.db";
    private static final String TABLE_DEVICES = "devices";

    private static final String KEY_ID = "id";
    private static final String KEY_NICKNAME = "nickName";
    private static final String KEY_CHARGE_TYPE = "chargeType";
    private static final String KEY_IMAGE_TYPE = "imageType";
    private static final String KEY_MAKE = "make";
    private static final String KEY_MODEL = "model";
    private static final String KEY_CHARGE_TIME = "chargeTime";
    private static final String KEY_TYPE = "type";

    public DevicesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d(M, "Default Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(M, "On Create()");
        String CREATE_DEVICES_TABLE = "CREATE TABLE " + TABLE_DEVICES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NICKNAME + " TEXT,"
                + KEY_CHARGE_TYPE + " TEXT,"
                + KEY_IMAGE_TYPE + " TEXT,"
                + KEY_MAKE + " TEXT,"
                + KEY_MODEL + " TEXT,"
                + KEY_CHARGE_TIME + " TEXT,"
                + KEY_TYPE + " TEXT"
                + ")";
        db.execSQL(CREATE_DEVICES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(M, "On Upgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICES);

        onCreate(db);
    }

    void addDevice(ChargedDevice device){
        //Log.d(M, "addDevice");
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(KEY_NICKNAME, device.getNickName());
        values.put(KEY_CHARGE_TYPE, device.getChargeTime());
        values.put(KEY_IMAGE_TYPE, device.getImageType());
        values.put(KEY_MODEL, device.getModel());
        values.put(KEY_MAKE, device.getMake());
        values.put(KEY_CHARGE_TIME, device.getChargeTime());
        values.put(KEY_TYPE, device.getType());

        db.insert(TABLE_DEVICES, null, values);
        db.close();
    }

    public ChargedDevice getDevice(int id){
        Log.d(M, "GetDevice");

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_DEVICES, new String[]{
                KEY_ID,
                KEY_NICKNAME,
                KEY_CHARGE_TYPE,
                KEY_IMAGE_TYPE,
                KEY_MAKE,
                KEY_MODEL,
                KEY_CHARGE_TIME,
                KEY_TYPE}, KEY_ID + "=?", new String[]{String.valueOf(id)},null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        ChargedDevice device = new ChargedDevice(
                //Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6));

        return device;
    }

    public ArrayList<ChargedDevice> getChargedDevices(){
        Log.d(M, "getChargedDevices");

        ArrayList<ChargedDevice> devicesList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_DEVICES;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                ChargedDevice device = new ChargedDevice();
                        device.setID(Integer.parseInt(cursor.getString(0)));
                        device.setNickName(cursor.getString(1));
                        device.setChargeType(cursor.getString(2));
                        device.setChargeTime(cursor.getString(3));
                        device.setMake(cursor.getString(4));
                        device.setModel(cursor.getString(5));
                        device.setImageType(cursor.getString(6));

                devicesList.add(device);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return devicesList;
    }
    public int updateDevice(ChargedDevice device){
        //Log.d(M, "UpdateDevice");
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NICKNAME, device.getNickName());
        values.put(KEY_CHARGE_TYPE, device.getChargeType());
        values.put(KEY_IMAGE_TYPE, device.getChargeTime());
        values.put(KEY_MAKE, device.getMake());
        values.put(KEY_MODEL, device.getModel());
        values.put(KEY_CHARGE_TIME, device.getChargeTime());
        values.put(KEY_TYPE, device.getType());

        int result = db.update(TABLE_DEVICES, values, KEY_ID + "=?",new String[]{String.valueOf(device.getID())});
        return result;


    }
    public void deleteDevice (ChargedDevice device){
        Log.d(M, "DeleteDevice");
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_DEVICES, KEY_ID + "=?", new String[]{String.valueOf(device.getID())});
        db.close();
    }

    public int getDevicesCount(){
        Log.d(M, "GetDevicesCount");
        String countQuery = "SELECT * FROM " + TABLE_DEVICES;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int c = cursor.getCount();
        cursor.close();

        return c;
    }

    public ArrayList<ChargedDevice> getAllDevices() {
        Log.d(M, "GetAllDevices");
        ArrayList<ChargedDevice> devicesList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_DEVICES;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                ChargedDevice device = new ChargedDevice();
            /*ID*/
                //String zero = cursor.getString(0);
                //Log.d(M,"0 " + zero);

            /*Nick Name*/
                //String one = cursor.getString(1);
                //Log.d(M,"1 " + one);
                device.setNickName(cursor.getString(1));

            /*Charge Type*/
                //String two = cursor.getString(2);
                //Log.d(M,"2 " + two);
                device.setChargeType(cursor.getString(2));

            /*Charge Time*/
                //String two = cursor.getString(2);
                //Log.d(M,"2 " + two);
                device.setChargeTime(cursor.getString(2));

            /*Make*/
                //String four = cursor.getString(4);
                //Log.d(M,"4 " + four);
                device.setMake(cursor.getString(4));

            /*Model*/
                //String five = cursor.getString(5);
                //Log.d(M,"5 " + five);
                device.setModel(cursor.getString(5));

            /*Image Type*/
                //String three = cursor.getString(3);
                //Log.d(M,"3 " + three);
                device.setImageType(cursor.getString(3));

                devicesList.add(device);

            } while (cursor.moveToNext());
        }

        return devicesList;

    }

    public void LoadTestDevices (){
        Log.d(M, "...Adding Test Devices...");

        addDevice(
                new ChargedDevice(
                "X7",
                "240v",
                "4",
                "Aorus",
                "X7V2",
                "laptop"));

        addDevice(
                new ChargedDevice(
                "Uni",
                "240v",
                "4",
                "Razer",
                "Blade Stealth",
                "laptop"));

        addDevice(
                new ChargedDevice(
                "6P",
                "5v",
                "3",
                "Huawei",
                "Nexus 6P",
                "phone"));

        addDevice(
                new ChargedDevice(
                "S3",
                "5v",
                "4",
                "Samsung",
                "Galaxy S3",
                "phone"));






    }
}
