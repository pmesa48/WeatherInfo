package services;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pablomesa on 14/01/16.
 */
public class WeatherService
{
    public final static String API_KEY = "56b2806613fa46abc4bc598f079143b6";

    public final static String SERVICE_URL = "http://api.openweathermap.org/data/2.5/weather?";

    private static String IMG_URL = "http://openweathermap.org/img/w/";


    private JSONObject jsonObject;




    public String getWeatherData(String location) {
        HttpURLConnection con = null ;
        InputStream is = null;

        String[] params = location.split("&");

        try {
            con = (HttpURLConnection) ( new URL(SERVICE_URL + "lat="+params[0]+"&lon="+params[1]+"&APPID="+API_KEY )).openConnection();
            Log.d("WeatherData",SERVICE_URL + "lat="+params[0]+"&lon="+params[1]+"&APPID="+API_KEY);
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();
            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();

        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }


    public byte[] getImage(String code) {
        HttpURLConnection con = null ;
        InputStream is = null;
        try {
            Log.d("Imagen",IMG_URL+code+".png");
            con = (HttpURLConnection) ( new URL(IMG_URL + code+".png")).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            //con.setDoOutput(true);
            con.connect();

            // Let's read the response
            is = con.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ( is.read(buffer) != -1)
                baos.write(buffer);

            return baos.toByteArray();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }

}
