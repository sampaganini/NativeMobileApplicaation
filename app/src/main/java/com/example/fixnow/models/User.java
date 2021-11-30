package com.example.fixnow.models;

public class User {
    private String login;
    private String password;
    private String type;
    private String name;
    private String last_name;
    private int radius;
    private Double longitude;
    private Double latitude;
    private String telephone;
    private String Specialization;
    private String description;
    private String company;

    public User(String login, String password, String type, String name, String last_name, int radius, Double longitude, Double latitude, String specialization, String description, String company,String tel) {
        this.login = login;
        this.password = password;
        this.type = type;
        this.name = name;
        this.last_name = last_name;
        this.radius = radius;
        this.longitude = longitude;
        this.latitude = latitude;
        Specialization = specialization;
        this.description = description;
        this.company = company;
        this.telephone = tel;
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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
