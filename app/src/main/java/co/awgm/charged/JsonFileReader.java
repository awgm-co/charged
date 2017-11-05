package co.awgm.charged;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Andrew on 6/11/2017.
 */

public class JsonFileReader extends AppCompatActivity {

    final static String M = "FILE_READER";


    public ArrayList<ChargedPlace> ReadJsonFile(Context context){

        ArrayList<ChargedPlace> jsonList = new ArrayList<ChargedPlace>();

        Log.d(M, "LOADING MARKERS FROM FILE...");


    String json = null;
        try {
            InputStream is  = context.getResources().getAssets().open("charged_map_markers.json");

            jsonList = readJsonStream(is);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    return jsonList;
}

    public ArrayList<ChargedPlace> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readPlacesArray(reader);
        } finally {
            reader.close();
        }
    }

    public ArrayList<ChargedPlace> readPlacesArray(JsonReader reader) throws IOException {
        ArrayList<ChargedPlace> places = new ArrayList<ChargedPlace>();

        reader.beginArray();
        while (reader.hasNext()) {
            places.add(readPlace(reader));
        }
        reader.endArray();
        return places;
    }

    public ChargedPlace readPlace(JsonReader reader) throws IOException {
        String locationCode = null;    //0
        String name = null;            //1
        String lat = null;             //2
        String lng = null;             //3
        String signage = null;         //4
        String info = null;            //5
        String iconFileName = null;    //6
        String containerId = null;     //7
        String containerName = null;   //8
        String categoryId = null;      //9
        String categoryHandle = null;  //12
        String keywords = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String JsonPlace;
            JsonPlace = reader.nextName();
            if (JsonPlace.equals("lat")) {
                lat = reader.nextString();
            } else if (JsonPlace.equals("lng")) {
                lng = reader.nextString();
            } else if (JsonPlace.equals("name")) {
                name = reader.nextString();
            } else if (JsonPlace.equals("signage")) {
                signage = reader.nextString();
            } else if (JsonPlace.equals("info")) {
                info = reader.nextString();
            } else if (JsonPlace.equals("icon_file_name")) {
                iconFileName = reader.nextString();
            } else if (JsonPlace.equals("container_name")) {
                containerName = reader.nextString();
            } else if (JsonPlace.equals("container_id")) {
                containerId = reader.nextString();
            } else if (JsonPlace.equals("category_id")) {
                categoryId = reader.nextString();
            } else if (JsonPlace.equals("category_handle")) {
                categoryHandle = reader.nextString();
            } else if (JsonPlace.equals("location_code")) {
                locationCode = reader.nextString();
            } else if (JsonPlace.equals("keywords")) {
                keywords = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new ChargedPlace(locationCode, lat,lng,name,signage,info,iconFileName,containerId,containerName,categoryId,categoryHandle,keywords);
    }



}
