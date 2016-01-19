package model;

/**
 * Created by pablomesa on 14/01/16.
 */
public class UserPreferences {

    private int tempMax;
    private int tempMin;
    private double minHumidity;
    private double maxHumidity;
    private boolean rain;
    private boolean snow;
    private boolean wind;

    public UserPreferences( ) {
        this.tempMax = 30;
        this.tempMin = 10;
        rain = false;
        snow = false;
        wind = false;
        minHumidity = 0;
        maxHumidity = 1;
    }

    public int getTempMax() {
        return tempMax;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }

    public int getTempMin() {
        return tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

    public boolean isRain() {
        return rain;
    }

    public void setRain(boolean rain) {
        this.rain = rain;
    }

    public boolean isSnow() {
        return snow;
    }

    public void setSnow(boolean snow) {
        this.snow = snow;
    }

    public boolean isWind() {
        return wind;
    }

    public void setWind(boolean wind) {
        this.wind = wind;
    }

    public double getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(double minHumidity) {
        this.minHumidity = minHumidity;
    }

    public double getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(double maxHumidity) {
        this.maxHumidity = maxHumidity;
    }


    public void save()
    {

    }

    public void load()
    {

    }
}
