package model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by pablomesa on 14/01/16.
 */
public class WeatherMarker {

    private LatLng latLng;

    private String location;

    public WeatherMarker( LatLng latLng, String location)
    {
        this.latLng = latLng;
        this.location = location;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
