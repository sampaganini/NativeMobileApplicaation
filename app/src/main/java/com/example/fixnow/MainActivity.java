package com.example.fixnow;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.fixnow.R;
import com.example.fixnow.utils.LocationUtils;
import com.example.fixnow.utils.PersmissionHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    private GoogleMap map;
    private static final int LODZ_ZOOM = 15;
    private FusedLocationProviderClient fusedLocationClient;
    private PersmissionHelper appCompatPermissionHelper = new PersmissionHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        enableMyLocation();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
       // enableMyLocation();
    }


    private void enableMyLocation() {
        appCompatPermissionHelper.startPermissionRequest(this, isGranted -> {
            if (isGranted) {
                setMyLocation();
                //myLocationButton.setVisibility(View.VISIBLE);
            } else {
                System.out.println("lol");
                map.animateCamera(
                        CameraUpdateFactory
                                .newLatLngZoom(LocationUtils.LODZ_COOR, LODZ_ZOOM));
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @SuppressLint("NoPermission")
    private void setMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if(location != null)
            {
                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(latLng,15));
                map.addMarker(new MarkerOptions().position(latLng));
            }
            else{
                map.animateCamera(
                        CameraUpdateFactory
                                .newLatLngZoom(LocationUtils.LODZ_COOR, LODZ_ZOOM)
                );
            }

        });

    }

}