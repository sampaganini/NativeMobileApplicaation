package com.example.fixnow;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.fixnow.api.ApiService;
import com.example.fixnow.api.RetrofitClient;
import com.example.fixnow.models.Login;
import com.example.fixnow.models.Technican;
import com.example.fixnow.models.User;
import com.example.fixnow.models.findTechRequest;
import com.example.fixnow.utils.LocationUtils;
import com.example.fixnow.utils.PersmissionHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    private GoogleMap map;
    private static final int LODZ_ZOOM = 15;
    private FusedLocationProviderClient fusedLocationClient;
    private PersmissionHelper appCompatPermissionHelper = new PersmissionHelper();
    private LatLng UserLatLng;

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

    }

    private void enableMyLocation() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                setMyLocation();
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                setMyLocation();
                            } else {
                                UserLatLng = LocationUtils.LODZ_COORDINATES;
                                map.animateCamera(
                                        CameraUpdateFactory
                                                .newLatLngZoom(LocationUtils.LODZ_COORDINATES, LODZ_ZOOM));
                                map.addMarker(new MarkerOptions().position(LocationUtils.LODZ_COORDINATES).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                displaySpecialists(UserLatLng);
                            }
                        }
                );
        locationPermissionRequest.launch(new String[]{
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
                            UserLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            if(LocationUtils.isLodz(UserLatLng)) {
                                map.animateCamera(
                                        CameraUpdateFactory.newLatLngZoom(UserLatLng, 15));
                                map.addMarker(new MarkerOptions().position(UserLatLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                displaySpecialists(UserLatLng);
                            }
                            else
                            {
                                UserLatLng = LocationUtils.LODZ_COORDINATES;
                                map.animateCamera(
                                        CameraUpdateFactory
                                                .newLatLngZoom(LocationUtils.LODZ_COORDINATES, LODZ_ZOOM));
                                map.addMarker(new MarkerOptions().position(LocationUtils.LODZ_COORDINATES).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                displaySpecialists(UserLatLng);

                            }
                        } else {
                            UserLatLng = LocationUtils.LODZ_COORDINATES;
                            map.animateCamera(
                                    CameraUpdateFactory
                                            .newLatLngZoom(LocationUtils.LODZ_COORDINATES, LODZ_ZOOM));
                            map.addMarker(new MarkerOptions().position(LocationUtils.LODZ_COORDINATES).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            displaySpecialists(UserLatLng);
                        }
                    }
                });
    }

    public void displaySpecialists(LatLng userLatLng) {
        SharedPreferences sharedPref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", null);
       // Retrofit retrofit = RetrofitClient.getRetrofitClient("http://192.168.100.155:2223");
        Bundle bundle = getIntent().getExtras();
        //String token = bundle.getString("token");
        Retrofit retrofit = RetrofitClient.getRetrofitWithToken("http://192.168.100.155:2223",token);
        ApiService service = retrofit.create(ApiService.class);
        String typeSTR =  bundle.getString("type");
        Long type = null;
        BottomNavigationView bottomNav = findViewById(R.id.user_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        if(typeSTR.equals("Hydraulik"))
        {
            type = Long.valueOf(1);
        }
        if(typeSTR.equals("Mechanik"))
        {
            type = Long.valueOf(2);
        }
        if(typeSTR.equals("Stolarz"))
        {
            type = Long.valueOf(3);
        }
        if(typeSTR.equals("Elektryk"))
        {
            type = Long.valueOf(4);
        }
        Call<List<Technican>> call = service.findTechnicans(new findTechRequest(new Float(userLatLng.latitude),new Float(userLatLng.longitude),type));
        call.enqueue(new Callback<List<Technican>>() {
            @Override
            public void onResponse(Call<List<Technican>> call, Response<List<Technican>> response) {
                List<Technican> technicans = response.body();
                if(technicans == null || technicans.size() == 0)
                {
                    if(response.code() != 200)
                    {
                        System.out.println("!!!!!!!!!!!!" + response.code());
                        Toast.makeText(getApplicationContext(), "Błąd "+ response.code(), Toast.LENGTH_LONG).show();
                    }
                    else {
                        int code = response.code();
                        Toast.makeText(getApplicationContext(), "W pobliżu nie ma takich specjalistów", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    for(Technican u : technicans) {
                        LatLng specialistLatLng = new LatLng(u.getLocationLat(),u.getLocationLon());
                        Marker marker = map.addMarker(new MarkerOptions().position(specialistLatLng));
                        marker.setTag(u.getAccountId());
                    }
                    onClickTechnican(technicans);
                }
            }
            @Override
            public void onFailure(Call<List<Technican>> call, Throwable t) {
                System.out.println("Błąd łączenia"+t.getMessage());
                Toast.makeText(getApplicationContext(),"Problem z wyszukiwaniem",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onClickTechnican(List<Technican> technicans)
    {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Long id = (Long) (marker.getTag());
                for (Technican t : technicans) {
                    if (t.getAccountId().equals(id)) {
                        Intent i = new Intent(getApplicationContext(), SpecialistProfileActivity.class);
                        i.putExtra("account_id",t.getAccountId());
                        if(t.getRating()!= null) {
                            i.putExtra("rating", t.getRating());
                        }
                        else{
                            i.putExtra("rating",new Long(0));
                        }
                        i.putExtra("desc",t.getDescription());
                        i.putExtra("service",t.getServiceId());
                        i.putExtra("name",t.getName());
                        i.putExtra("last_name",t.getLast_name());
                        i.putExtra("id",t.getId());
                        startActivity(i);
                    }
                }
                return false;
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Intent i = null;
                    switch (item.getItemId()) {
                        case R.id.item1:
                            i = new Intent(getApplicationContext(), Login.class);
                            startActivity(i);
                            return true;

                        case R.id.item2:
                            i = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(i);
                            return true;

                        case R.id.item3:
                            i = new Intent(getApplicationContext(), SearchActivity.class);
                            startActivity(i);
                            return true;

                        case R.id.item4:
                            i = new Intent(getApplicationContext(), UserActivity.class);
                            startActivity(i);
                            return true;

                    }
                        return true;
                }
            };

}