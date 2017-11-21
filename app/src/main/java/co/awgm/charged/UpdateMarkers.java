package co.awgm.charged;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 6/11/2017.
 */

public class UpdateMarkers extends AppCompatActivity {

    final static String M = "FILE_READER";
    private XmlPullParser Parser;


    public UpdateMarkers() {

        List<ChargedPlace> markerList = new ArrayList<ChargedPlace>();
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;

        Log.d(M, "LOADING MARKERS FROM FILE...");

        XmlPullParserFactory xmlFactoryObject = null;
        try {
            xmlFactoryObject = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        Parser = null;
        try {
            Parser = xmlFactoryObject.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


        try {
            inputStream = assetManager.open("charged_map_markers.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Parser.setInput(inputStream,null);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        int event = 0;
        try {
            event = Parser.getEventType();
        } catch (XmlPullParserException e1) {
            e1.printStackTrace();
        }
        while (event != XmlPullParser.END_DOCUMENT)  {
                String name = Parser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        Log.d(M, "START_TAG");
                        break;

                    case XmlPullParser.END_TAG:
                        Log.d(M, "END_TAG");
                        break;
                }
            try {
                event = Parser.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(M, "END OF DOCUMENT");
        }

    }


    public ChargedPlace readPlaceJson(JsonReader reader) throws IOException {
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
