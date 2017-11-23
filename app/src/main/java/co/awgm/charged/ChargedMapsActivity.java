package co.awgm.charged;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import co.awgm.charged.PermissionUtils.PermissionDeniedDialog;

public class ChargedMapsActivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;
    private static String M = "CHARGED_MAPS_ACTIVITY";
    private GoogleMap mMap;
    private LocationManager locationManager;
    Toolbar toolbar;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;
    private LatLng lastKnownLatLng;
    private CameraPosition mCameraPosition;
    // Keys for storing activity state.
    public static final String KEY_CAMERA_POSITION = "camera_position";
    public static final String KEY_LOCATION = "location";

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-32.0667187,115.8329096);
    private static final int DEFAULT_ZOOM = 18;
    private boolean mLocationPermissionGranted;
    boolean mLocationPermissionDenied;

    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int MY_LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int LOCATION_LAYER_PERMISSION_REQUEST_CODE = 2;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;


    private UiSettings mUiSettings;

    private PlacesDatabaseHelper db;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(M, "OnCreateOptionsMenu()");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(M, "onOptionsItemSelected()");
        switch (item.getItemId()) {
            case R.id.action_settings:
                //Log.d(M, "onOptionsItemSelected():case:action_settings");
                Intent toolbarSettings = new Intent(this,
                        SettingsActivity.class);
                startActivity(toolbarSettings);
                return true;

            case R.id.action_nearby:
                getDeviceLocation();
                Toast.makeText(this, "Refreshing the Map...", Toast.LENGTH_LONG).show();
                Log.d(M, "Refreshing the Map");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(M, "onSaveInstanceState Line 325");
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(M, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charged_maps);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            Log.d(M, "Retrieving previous Location and Camera Position");
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);



        //New PlacesDatabaseHelper
        db = new PlacesDatabaseHelper(this);




        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        if (savedInstanceState == null) {
            // First incarnation of this activity.
            mapFragment.setRetainInstance(true);
        }

        // Get the current location of the device and set the position of the map.
        //getDeviceLocation();

        // Prompt the user for permission.
        getLocationPermission();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(M, "Map is Ready now");
        mMap = googleMap;

        mUiSettings = mMap.getUiSettings();

        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(M, "Passed Permissions Check");
            mMap.setMyLocationEnabled(true);

        }

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mUiSettings.setMapToolbarEnabled(true);
        mUiSettings.setIndoorLevelPickerEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);

        AddMarkers();
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
    }


    /**
     * Checks if the map is ready (which depends on whether the Google Play services APK is
     * available. This should be called prior to calling any methods on GoogleMap.
     */
    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }





    public void setMyLocationButtonEnabled(View v) {
        //Default: un-checked
        if (!checkReady()) {
            return;
        }
        // Enables/disables the my location button (this DOES NOT enable/disable the my location
        // dot/chevron on the map). The my location button will never appear if the my location
        // layer is not enabled.
        // First verify that the location permission has been granted.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mUiSettings.setMyLocationButtonEnabled(true);
        } else {
            getLocationPermission();
        }
    }

    public void setMyLocationLayerEnabled(View v) {
        //Default: un-checked
        if (!checkReady()) {
            return;
        }
        // Enables/disables the my location layer (i.e., the dot/chevron on the map). If enabled, it
        // will also cause the my location button to show (if it is enabled); if disabled, the my
        // location button will never show.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mUiSettings.setMyLocationButtonEnabled(true);
        } else {
            PermissionUtils.requestPermission(this, LOCATION_LAYER_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, false);
        }
    }


        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
     private void getLocationPermission() {
        Log.d(M, "getLocationPermission()");
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Log.d(M, "onMyLocationButtonClick()");
        Toast.makeText(this, "Refreshing Map...", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    mUiSettings.setMyLocationButtonEnabled(true);
                    updateLocationUI();
                    getDeviceLocation();
                }
            }
        }

        if (requestCode == MY_LOCATION_PERMISSION_REQUEST_CODE) {
            // Enable the My Location button if the permission has been granted.
            if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                mUiSettings.setMyLocationButtonEnabled(true);
                getDeviceLocation();
                updateLocationUI();
            } else {
                mLocationPermissionDenied = true;
            }

        } else if (requestCode == LOCATION_LAYER_PERMISSION_REQUEST_CODE) {
            // Enable the My Location layer if the permission has been granted.
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mUiSettings.setMyLocationButtonEnabled(true);
                getDeviceLocation();
                updateLocationUI();
            } else {
                mLocationPermissionDenied = true;
            }
            }

        }
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            PermissionUtils.PermissionDeniedDialog
                    .newInstance(false).show(getSupportFragmentManager(), "dialog");
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private  void AddMarkers() {
        Log.d(M, "AddMarkers()");

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier("charged_map_marker","drawable", getPackageName()));
//        Bitmap markerBitmap = Bitmap.createScaledBitmap(bitmap, 80, 80, false);

        // ArrayList to store the places
        ArrayList<ChargedPlace> places;

        //Get all the places from the database and store them in the ArrayList
        places = db.getAllPlaces();

        //For Each place in the ArrayList
        for (ChargedPlace p: places) {

            //Add a marker to the map with each places information
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(
                            Double.parseDouble(p.getLat()),
                            Double.parseDouble(p.getLng())
                    ))
                    .title(p.getName().toUpperCase())
                    .snippet(p.getInfo())
                    //.icon(BitmapDescriptorFactory.fromFile("charged_map_marker")));
                    .icon(bitmapDescriptorFromVector(this, R.drawable.ic_chargedmapmarker)));


            //REFERENCE:
            //https://stackoverflow.com/questions/41509791/how-to-fix-custom-size-of-google-maps-marker-in-android


            /*Un-Comment to see the marker text in the log*/

            //Log.d(M,p.getName().toUpperCase());
            //Log.d(M, p.getInfo());
        }
    }
    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        Log.d(M, "getDeviceLocation()");
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */

        if (mLocationPermissionGranted) {
            Log.d(M, "Current location is available. locating user");
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();

            locationResult.addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = location;
                        lastKnownLatLng = new LatLng(
                                mDefaultLocation.latitude,
                                mDefaultLocation.longitude);


                        mMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(lastKnownLatLng, DEFAULT_ZOOM));
                        mMap.getUiSettings().setMyLocationButtonEnabled(true);

                    } else {
                        Log.d(M, "Current location is null. Using defaults.");

                        mMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                }

            });


        }
    }
    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        Log.d(M, "updateLocationUI()");
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }

    }
    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


}
