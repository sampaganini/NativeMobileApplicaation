package com.example.fixnow.utils;

import com.google.android.gms.maps.model.LatLng;

public class LocationUtils {
    private static final int LODZ_RADIUS = 40;
    public static final LatLng LODZ_COORDINATES = new LatLng(51.7833,19.4667);

    public static boolean isLodz(LatLng latitudeLongitude)
    {
        return calculateDistance(latitudeLongitude,LODZ_COORDINATES) < LODZ_RADIUS;
    }
    public static double calculateDistance(LatLng latLng1, LatLng latLng2)
    {
        double theta = latLng1.longitude - latLng2.longitude;
        double firstPointRadians = Math.toRadians(latLng1.latitude);
        double secondPointRadians = Math.toRadians(latLng2.latitude);
        double dist = Math.sin(firstPointRadians) * Math
                .sin(secondPointRadians);
        dist += Math.cos(firstPointRadians) * Math
                .cos(secondPointRadians) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return dist;
    }
}
