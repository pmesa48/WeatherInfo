package model;

/**
 * Created by pablomesa on 14/01/16.
 */
public class BestWeather {


    private UserPreferences up;

    private float temp1, temp2, rain1, rain2, wind1, wind2;

    private int clouds1, clouds2;

    public BestWeather(float temp1, float temp2, float rain1, float rain2, float wind1, float wind2, int clouds1, int clouds2) {
        this.temp1 = temp1;
        this.temp2 = temp2;
        this.rain1 = rain1;
        this.rain2 = rain2;
        this.wind1 = wind1;
        this.wind2 = wind2;
        this.clouds1 = clouds1;
        this.clouds2 = clouds2;
        up = new UserPreferences();
    }

    public UserPreferences getUp() {
        return up;
    }

    public void setUp(UserPreferences up) {
        this.up = up;
    }

    public float getTemp1() {
        return temp1;
    }

    public void setTemp1(float temp1) {
        this.temp1 = temp1;
    }

    public float getTemp2() {
        return temp2;
    }

    public void setTemp2(float temp2) {
        this.temp2 = temp2;
    }

    public float getRain1() {
        return rain1;
    }

    public void setRain1(float rain1) {
        this.rain1 = rain1;
    }

    public float getRain2() {
        return rain2;
    }

    public void setRain2(float rain2) {
        this.rain2 = rain2;
    }

    public float getWind1() {
        return wind1;
    }

    public void setWind1(float wind1) {
        this.wind1 = wind1;
    }

    public float getWind2() {
        return wind2;
    }

    public void setWind2(float wind2) {
        this.wind2 = wind2;
    }

    public int getClouds1() {
        return clouds1;
    }

    public void setClouds1(int clouds1) {
        this.clouds1 = clouds1;
    }

    public int getClouds2() {
        return clouds2;
    }

    public void setClouds2(int clouds2) {
        this.clouds2 = clouds2;
    }


    public boolean isOneBetterThanTwo()
    {
        int one = 0;
        int two = 0;

        if( clouds1 > clouds2 )
            two++;
        else if( clouds2 > clouds1 )
            one++;

        if(temp1 > temp2)
            one++;
        else if ( temp2 > temp1 )
            two++;

        if( temp1 > temp2 && temp1 < up.getTempMax() && temp1 > up.getTempMin() )
            one+=3;
        else if( temp2 > temp1 && temp2 < up.getTempMax() && temp2 > up.getTempMin() )
            two+=3;

        if( rain1 > rain2 )
            two++;
        else if( rain2 > rain1 )
            one++;

        if( wind1 > wind2 )
            two++;
        else if( wind2 > wind1 )
            one++;

        if( one >= two )
            return true;
        else
            return false;
    }
}
