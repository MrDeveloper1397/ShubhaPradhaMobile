package task.marami.Shubhaprada;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationView extends AppCompatActivity implements OnMapReadyCallback {
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(LocationView.this, "Google Maps Is Ready", Toast.LENGTH_SHORT).show();
        gMap = googleMap;
        if (mLocalPermisionGrant) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission
                            (this,
                                    Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            gMap.setMyLocationEnabled(true);
            gMap.getUiSettings().setMyLocationButtonEnabled(false);
            getDeviceLocation();
        }
    }

    private static final String Fine_Location = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String Coarse_Location = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean mLocalPermisionGrant = false;
    FusedLocationProviderClient mfusedLocationProviderClient;
    public static final int lRequestCode = 1234;


    private GoogleMap gMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_view);

        ImageView gps = (ImageView) findViewById(R.id.Ic_Gps);
        ImageView VentureLoc = (ImageView) findViewById(R.id.ventureloc);
        getLocationPermisions();
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });
        VentureLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geoLocate();
            }
        });
    }
    private void geoLocate() {

        Geocoder geocoder = new Geocoder(LocationView.this);
        Double lat = Double.parseDouble(getIntent().getStringExtra("Lat"));
        Double Lon = Double.parseDouble(getIntent().getStringExtra("Lon"));
        String title = getIntent().getStringExtra("Title");
        gMap.addMarker(new MarkerOptions().position(new LatLng(lat, Lon)).title(title));
        moveCamera(new LatLng(lat, Lon), 15f, title);
    }

    public void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(LocationView.this);
    }

    public void getDeviceLocation() {
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocalPermisionGrant) {
                Task location = mfusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            //Location currentLocatioon = (Location) task.getResult();
                            LatLng latlang=getCurrentLocation(getBaseContext());
                            moveCamera(latlang, 15f, "Current location");
                        } else {
                            Toast.makeText(LocationView.this, "Unable to get current Location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.d("msg", e.getMessage().toString());
        }

    }

    public void moveCamera(LatLng latlng, Float zoom, String title) {
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
        MarkerOptions options = new MarkerOptions().position(latlng).title(title);
        gMap.addMarker(options).showInfoWindow();
        gMap.getUiSettings().setMapToolbarEnabled(true);
    }

    public void getLocationPermisions() {
        String[] Permisions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Fine_Location) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Coarse_Location) == PackageManager.PERMISSION_GRANTED) {
                mLocalPermisionGrant = true;
                initMap();
            }

        } else {
            ActivityCompat.requestPermissions(this, Permisions
                    , lRequestCode);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocalPermisionGrant = false;
        switch (requestCode) {
            case lRequestCode: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocalPermisionGrant = false;
                            break;
                        }
                    }
                    mLocalPermisionGrant = true;
                    initMap();
                }
            }
        }
    }


    public LatLng getCurrentLocation(Context context) {
        try {
            LocationManager locMgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String locProvider = locMgr.getBestProvider(criteria, false);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

            }
            Location location = locMgr.getLastKnownLocation(locProvider);

            // getting GPS status
            boolean isGPSEnabled = locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            boolean isNWEnabled = locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNWEnabled)
            {
                // no network provider is enabled
                return null;
            }
            else
            {
                // First get location from Network Provider
                if (isNWEnabled)
                    if (locMgr != null)
                        location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled)
                    if (location == null)
                        if (locMgr != null)
                            location = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            return new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (NullPointerException ne)
        {
            Log.e("Current Location", "Current Lat Lng is Null");
            return new LatLng(17.3850, 78.4867);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new LatLng(17.3850, 78.4867);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent MainAct=new Intent(LocationView.this,HomeActivity.class);
        startActivity(MainAct);
    }
}
