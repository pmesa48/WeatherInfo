package model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by pablomesa on 14/01/16.
 */
public class Location {

    private float longitude;
    private float latitude;
    private long sunset;
    private long sunrise;
    private String country;
    private String city;
    private LatLng latLng;
    private LatLng oldLatLng;

    public float getLongitude() {
        return longitude;
    }
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    public float getLatitude() {
        return latitude;
    }
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    public long getSunset() {
        return sunset;
    }
    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
    public long getSunrise() {
        return sunrise;
    }
    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude,longitude);
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public LatLng getOldLatLng() {
        return oldLatLng;
    }

    public void setOldLatLng(LatLng oldLatLng) {
        this.oldLatLng = oldLatLng;
    }
}
