package com.example.fixnow.models;

public class findTechRequest {
    private Float locationLat;
    private Float locationLon;
    private Long type;

    public findTechRequest(Float locationLat, Float locationLon, Long type) {
        this.locationLat = locationLat;
        this.locationLon = locationLon;
        this.type = type;
    }

    public Float getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(Float locationLat) {
        this.locationLat = locationLat;
    }

    public Float getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(Float locationLon) {
        this.locationLon = locationLon;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
