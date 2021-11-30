package com.example.fixnow;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.fixnow.utils.LocationUtils;
import com.example.fixnow.utils.PersmissionHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;


public class RegisterActivity3 extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    private GoogleMap map;
    private static final int LODZ_ZOOM = 15;
    private FusedLocationProviderClient fusedLocationClient;
    private PersmissionHelper appCompatPermissionHelper = new PersmissionHelper();
    private SeekBar radius;
    private EditText realRadius;
    private LatLng UserLatLng;
    private Circle mapCircle;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        getSupportActionBar().hide();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        enableMyLocation();
        radius = findViewById(R.id.seekBar);
        register = findViewById(R.id.button5);
        realRadius = findViewById(R.id.editTextTextPersonName5);
        radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // When seek bar progress is changed, change image alpha value.
                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                realRadius.setText(""+progressChangedValue);
                if(UserLatLng != null) {
                    if(mapCircle != null)
                    {
                        mapCircle.remove();
                    }
                    mapCircle = drawCircle(UserLatLng, progressChangedValue);
                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Bundle bundle = getIntent().getExtras();
                    String type = bundle.getString("type");
                    String login = bundle.getString("login");
                    String password = bundle.getString("password");
                    String name = bundle.getString("name");
                    String surname = bundle.getString("surname");
                    String telephone = bundle.getString("telephone");
                    String specialization = bundle.getString("specialization");
                    Intent i = new Intent(getApplicationContext(), RegisterActivity4.class);
                    i.putExtra("type", type);
                    i.putExtra("login",login);
                    i.putExtra("password",password);
                    i.putExtra("name",name);
                    i.putExtra("surname",surname);
                    i.putExtra("telephone",telephone);
                    i.putExtra("specialization",specialization);
                    i.putExtra("longitude",UserLatLng.longitude);
                    i.putExtra("latitude",UserLatLng.latitude);
                    i.putExtra("radius", (Parcelable) realRadius);
                    startActivity(i);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        // enableMyLocation();
    }

    private void enableMyLocation() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                setMyLocation();
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                setMyLocation();
                            } else {
                                map.animateCamera(
                                        CameraUpdateFactory
                                                .newLatLngZoom(LocationUtils.LODZ_COOR, LODZ_ZOOM));
                                map.addMarker(new MarkerOptions().position(LocationUtils.LODZ_COOR));
                            }
                        }
                );
        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
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
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            UserLatLng = new LatLng(location.getLatitude(),location.getLongitude());

                            map.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(UserLatLng,15));
                            map.addMarker(new MarkerOptions().position(UserLatLng));
                        }
                        else
                        {
                            map.animateCamera(
                                    CameraUpdateFactory
                                            .newLatLngZoom(LocationUtils.LODZ_COOR, LODZ_ZOOM));
                            map.addMarker(new MarkerOptions().position(LocationUtils.LODZ_COOR));
                        }
                    }
                });

    }

    private Circle drawCircle(LatLng point, int radius){

        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle
        circleOptions.center(point);

        // Radius of the circle
        circleOptions.radius(radius*1000);

        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK);

        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000);

        // Border width of the circle
        circleOptions.strokeWidth(2);

        // Adding the circle to the GoogleMap
        mapCircle = map.addCircle(circleOptions);
        return mapCircle;

    }

}