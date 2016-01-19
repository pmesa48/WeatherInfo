package model;


/**
 * Created by pablomesa on 14/01/16.
 */
public class Weather {

    public CurrentConditions currentCondition = new CurrentConditions();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Rain rain = new Rain();
    public Snow snow = new Snow()	;
    public Clouds clouds = new Clouds();

    public Location location;

    private String data;

    public byte[] iconData;

    public String getJson() {
        return data;
    }

    public void setJson(String json) {
        this.data = json;
    }
}
