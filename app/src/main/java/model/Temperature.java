package model;

/**
 * Created by pablomesa on 14/01/16.
 */
public class Temperature {

    public final static float KELVIN_CELSIUS = Float.parseFloat("273.15");
    private float temp;
    private float minTemp;
    private float maxTemp;

    public float getTemp() {
        return temp;
    }
    public void setTemp(float temp) {
        this.temp = temp-KELVIN_CELSIUS;
    }
    public float getMinTemp() {
        return minTemp;
    }
    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp-KELVIN_CELSIUS;
    }
    public float getMaxTemp() {
        return maxTemp;
    }
    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp-KELVIN_CELSIUS;
    }
}
