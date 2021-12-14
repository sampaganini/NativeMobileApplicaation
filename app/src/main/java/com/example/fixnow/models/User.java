package com.example.fixnow.models;

public class User {
    private String login;
    private String password;
    private String type;
    private String name;
    private String last_name;
    private Float radius;
    private Float location_lon;
    private Float location_lat;
    private String phone;
    private Long service_id;
    private String description;

    public User(String login, String password, String type, String name, String last_name, Float radius, Float locationLon, Float latitude, Long serviceId, String description, String tel) {
        this.login = login;
        this.password = password;
        this.type = type;
        this.name = name;
        this.last_name = last_name;
        this.radius = radius;
        this.location_lon = locationLon;
        this.location_lat = latitude;
        this.service_id = serviceId;
        this.description = description;
        this.phone = tel;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Float getRadius() {
        return radius;
    }

    public void setRadius(Float radius) {
        this.radius = radius;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getTelephone() {
        return phone;
    }

    public void setTelephone(String telephone) {
        this.phone = telephone;
    }

    public Float getLocationLon() {
        return location_lon;
    }

    public void setLocationLon(Float locationLon) {
        this.location_lon = locationLon;
    }

    public Float getLocationLat() {
        return location_lat;
    }

    public void setLocationLat(Float locationLat) {
        this.location_lat = locationLat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getServiceId() {
        return service_id;
    }

    public void setServiceId(Long serviceId) {
        this.service_id = serviceId;
    }
}
