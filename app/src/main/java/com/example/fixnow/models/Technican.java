package com.example.fixnow.models;

public class Technican {

    private Long id;

    private Long accountId;

    private Float locationLat;

    private Float locationLon;

    private Float radius;

    private Long serviceId;

    private String description;

    private Float rating;

    private String name;

    private String last_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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

    public Float getRadius() {
        return radius;
    }

    public void setRadius(Float radius) {
        this.radius = radius;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
