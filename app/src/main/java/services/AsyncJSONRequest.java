package services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import interfaces.OnTaskCompleted;
import model.Weather;

/**
 * Created by pablomesa on 14/01/16.
 */
public class AsyncJSONRequest extends AsyncTask<String, Void, Weather> {


    private OnTaskCompleted listener;

    public AsyncJSONRequest(OnTaskCompleted listener){
        this.listener=listener;
    }

    @Override
    protected Weather doInBackground(String... params) {
        Weather weather = new Weather();
        Log.d("params",params[0]);
        String data = ( (new WeatherService()).getWeatherData(params[0]));
        //Log.d("Mensaje", data);
        try {
            weather = Parser.getWeather(data);
            weather.setJson(data);


            // Let's retrieve the icon
           weather.iconData = ( (new WeatherService()).getImage(weather.currentCondition.getIcon()));

            String[] old = params[0].split("&");
            weather.location.setOldLatLng( new LatLng(Double.parseDouble(old[0]), Double.parseDouble(old[1])));

        } catch (JSONException e) {
            return null;

        }
        return weather;
    }

    protected void onPostExecute(Weather weather){
        // your stuff
        listener.OnTaskCompleted(weather);
    }
}
