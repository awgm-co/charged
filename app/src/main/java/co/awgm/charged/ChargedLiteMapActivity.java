package co.awgm.charged;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Andrew on 24/11/2017.
 */

public class ChargedLiteMapActivity extends AppCompatActivity implements
        OnMapReadyCallback{

    private static String M = "LITE_MAPS_ACTIVITY";
    private GoogleMap mLiteMap;
    private float SinglePlaceZoom = 20;
    private ChargedPlace chargedPlace;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chargedPlace = (ChargedPlace) getIntent().getParcelableExtra("parcel_data");
        // Set the layout
        setContentView(R.layout.lite_map_fragment);
        // Get the map and register for the ready callback
        SupportMapFragment liteMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.liteMap);
        liteMapFragment.getMapAsync(this);

    }

    /**
     * Called when the map is ready to add all markers and objects to the map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(M,"onLiteMapReady");
        mLiteMap = googleMap;

        Marker marker = mLiteMap.addMarker(new MarkerOptions()
                            .position(new LatLng(
                                    Double.parseDouble(chargedPlace.getLat()),
                                    Double.parseDouble(chargedPlace.getLng())
                            ))
                            .title(chargedPlace.getName().toUpperCase())
                            .snippet(chargedPlace.getInfo())
                            //.icon(BitmapDescriptorFactory.fromFile("charged_map_marker")));
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_chargedmapmarker)));
        marker.showInfoWindow();
        // Center camera on the given ChargedPlace
        mLiteMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(
                        Double.parseDouble(chargedPlace.getLat()),
                        Double.parseDouble(chargedPlace.getLng())
                ),
                SinglePlaceZoom)
        );
    }

    private static BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }



}
