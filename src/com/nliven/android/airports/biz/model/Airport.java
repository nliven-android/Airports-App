package com.nliven.android.airports.biz.model;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table AIRPORT.
 */
public class Airport {

    private Long id;
    private String code;
    private String icao;
    private String name;
    private String city;
    private String state;
    private Double latitude;
    private Double longitude;
    private Integer runwayLength;
    private String url;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Airport() {
    }

    public Airport(Long id) {
        this.id = id;
    }

    public Airport(Long id, String code, String icao, String name, String city, String state, Double latitude, Double longitude, Integer runwayLength, String url) {
        this.id = id;
        this.code = code;
        this.icao = icao;
        this.name = name;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.runwayLength = runwayLength;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getRunwayLength() {
        return runwayLength;
    }

    public void setRunwayLength(Integer runwayLength) {
        this.runwayLength = runwayLength;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // KEEP METHODS - put your custom methods here
    
    @Override
    public String toString() {        
        return (this.id + "|" + this.name + "|" + this.city + "|" + this.state + "|" + 
                this.latitude + "|" + this.longitude + "|" + this.runwayLength + "|" + this.url);
    }
    // KEEP METHODS END

}
