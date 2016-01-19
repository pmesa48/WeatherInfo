package com.pmesa48.weatherinfo;

import android.Manifest;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import interfaces.OnTaskCompleted;
import model.Weather;
import model.WeatherMarker;
import services.AsyncJSONRequest;
import services.LocationService;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnTaskCompleted, LocationListener, GoogleMap.OnMarkerClickListener {

    public final static int COMPARISONS = 2;
    private GoogleMap mMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private Location mLastLocation;

    private LocationService locationService;

    private TextView txtTemp;

    private TextView txtPlace;

    private TextView txtRain;

    private TextView txtClouds;

    private TextView txtWind;

    private ImageView imgCurrentCondition;

    private Bitmap bitmap;

    private Bitmap bg1;

    private List<Weather> markers;

    private LatLng marker1;

    private LatLng marker2;

    private WeatherMarker home;

    private HashMap<String, Weather> hashMap;

    private ImageButton imageButton;

    private EditText search;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setTitle("Clima Actual");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        locationService = new LocationService(this);


        //Items
        txtTemp = (TextView) findViewById(R.id.txtTemp);
        txtClouds = (TextView) findViewById(R.id.txtClouds);
        txtPlace = (TextView) findViewById(R.id.txtPlace);
        txtRain = (TextView) findViewById(R.id.txtRain);
        txtWind = (TextView) findViewById(R.id.txtWind);
        imgCurrentCondition = (ImageView) findViewById(R.id.weatherIcon);

        //Others
        markers = new ArrayList<Weather>();
        marker1 = null;
        marker2 = null;

        hashMap = new HashMap<>();

        search = (EditText)findViewById(R.id.search_text);

        imageButton = (ImageButton)findViewById(R.id.buttonSearch);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ajustarMapa();
            }
        });

    }


    public void ajustarMapa()
    {
        String name = search.getText().toString();
        Geocoder geocoder = new Geocoder(this);

        try {
            List<Address> ad = geocoder.getFromLocationName(name, 1);
            if( ad.size() > 0 )
            {
                mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(ad.get(0).getLatitude(), ad.get(0).getLongitude()), 6.0f) );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                String loc = getLocationNameFromGeo(latLng);

                LatLng current = new LatLng(latLng.latitude, latLng.longitude);
                mMap.addMarker(new MarkerOptions().position(current).title(loc));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                buscarInfo(current);

            }
        });

        if (client == null) {
            client = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "You may have some permissions issues", Toast.LENGTH_LONG).show();

            return;
        }
        locationService.getMyLocation();
        /*if( locationService != null )
            Toast.makeText(this, "lat:"+locationService.getLatitude()+" lon:"+locationService.getLongitude(), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();*/



        Geocoder gdc = new Geocoder(this);
        String loc = getLocationNameFromGeo(new LatLng(locationService.getLatitude(),locationService.getLongitude()));
        LatLng current = new LatLng(locationService.getLatitude(),locationService.getLongitude());
        home = new WeatherMarker(current,loc);
            mMap.addMarker(new MarkerOptions().position(current).title(loc));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(current));


        buscarInfo(current);


    }

    public String getLocationNameFromGeo(LatLng latLng)
    {
        Geocoder gdc = new Geocoder(this);
        String loc = "";
        try {
            List<Address> addresses = gdc.getFromLocation(latLng.latitude,latLng.longitude,1);

            if(addresses.size()>0)
            {
                loc = addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loc;
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.pmesa48.weatherinfo/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.pmesa48.weatherinfo/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "Error finding city name PERMISSIONS", Toast.LENGTH_LONG).show();

            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                client);
        if (mLastLocation != null) {
            // mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
                Toast.makeText(this, "lat: " + String.valueOf(mLastLocation.getLatitude()), Toast.LENGTH_LONG).show();
            } catch (IOException e) {

                Toast.makeText(this, "Error finding city name", Toast.LENGTH_LONG).show();
            }


        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }



    public void buscarInfo(LatLng latLng)
    {
        AsyncJSONRequest a = new AsyncJSONRequest(this);
        a.execute(new String[]{latLng.latitude+"&"+latLng.longitude});

    }

    @Override
    public void OnTaskCompleted(Weather weather) {
        //TODO actualizar panel de informacion del clima
        //Toast.makeText(this,weather.getJson(),Toast.LENGTH_LONG).show();
        if( weather == null )
        {
            Toast.makeText(getBaseContext(),"Parece que no tienes conexión a internet",Toast.LENGTH_LONG).show();
            return;
        }
        try
        {
        if( bitmap != null ) {
            bitmap = null;
        }
        if( bg1 != null  )
        {
            bg1 = null;
        }
        if (weather.iconData != null && weather.iconData.length > 0) {
            //Toast.makeText(this, "Reemplazando imagen del tiempo", Toast.LENGTH_LONG).show();
            bitmap = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
            if( bitmap != null ) {
                bg1 = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
                imgCurrentCondition.setImageBitmap(bg1);
            }
        }}
        catch (Exception e)
        {
            String uri = "@drawable/img";  // where myresource.png is the file
            // extension removed from the String
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imgCurrentCondition.setImageDrawable(res);
            e.printStackTrace();
        }

        markers.add(weather);
        String key = weather.location.getOldLatLng().latitude + "&" + weather.location.getOldLatLng().longitude;
        hashMap.put(key, weather);
        txtTemp.setText("Temp actual "+Double.parseDouble(weather.temperature.getTemp() + "")+"ºC");
        txtWind.setText("Viento " + weather.wind.getSpeed()+"m/s");
        txtRain.setText("Lluvia " + weather.rain.getAmmount()+"ml");
        txtClouds.setText("Nubes " + weather.clouds.getPerc() + "%");
        txtPlace.setText(weather.location.getCity() + ", " + weather.location.getCountry());


    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 1));
    }


    public void cleanMap()
    {
        mMap.clear();

        Collection<Weather> weathers = hashMap.values();
        Iterator it = weathers.iterator();
        if(it.hasNext( ) )
        {
            Weather weather = (Weather) it.next();
            mMap.addMarker(new MarkerOptions().position(weather.location.getLatLng()).title(weather.location.getCity()));
        }
    }

    public void addMarker(WeatherMarker wm) {
        mMap.addMarker(new MarkerOptions().position(wm.getLatLng()).title(wm.getLocation()));
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        WeatherMarker event = new WeatherMarker(marker.getPosition(), getLocationNameFromGeo(marker.getPosition()));


        if( marker1 != null && marker1.equals(event.getLatLng()))
        {
            marker.setIcon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            marker1 = null;
        }
        else if( marker2 != null && marker2.equals(event.getLatLng()))
        {
            marker.setIcon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            marker2 = null;
        }
        else {

            if (marker1 == null)
                marker1 = marker.getPosition();
            else if (marker2 == null)
                marker2 = marker.getPosition();
            else {
                marker1 = marker2;
                marker2 = marker.getPosition();
            }
        }

        if (hasTwoSelectedItems()) {
            verification();
            marker1 = null;
            marker2 = null;
            //cleanMap();
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_comparison, menu);

        return true;
    }


    public List<Weather> getSelectedWeathers()
    {
        List<Weather> weathers = new ArrayList<>();
        for (int i = 0; i < markers.size(); i++ )
        {
            if( markers.get(i).location.getLatLng().equals(marker1))
            {
                weathers.add(markers.get(i));
            }
            else if(markers.get(i).location.getLatLng().equals(marker2))
            {
                weathers.add(markers.get(i));
            }
        }
        return weathers;
    }

    public boolean hasTwoSelectedItems()
    {
        /*int p = 0;
        for (int i = 0; i < markers.size(); i++ )
        {
            LatLng actual = markers.get(i).location.getLatLng();
            if( marker1 != null && actual.longitude == marker1.longitude && actual.latitude == marker1.latitude)
            {
                p++;
            }
            else if(marker2 != null && actual.longitude == marker2.longitude && actual.latitude == marker2.latitude)
            {
                p++;
            }
        }
        if( p == 2 )
            return true;
        else
            return false;*/

        if( marker1 != null && marker2 != null) {
            String key1 = marker1.latitude + "&" + marker1.longitude;
            String key2 = marker2.latitude + "&" + marker2.longitude;

            if (hashMap.containsKey(key1) && hashMap.containsKey(key2)) {
                return true;
            } else
                return false;
        }
        return false;
    }


    public void showModalDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Deseas comparar estos dos climas?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        verification();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void verification()
    {
        String key1 = marker1.latitude+"&"+marker1.longitude;
        String key2 = marker2.latitude+"&"+marker2.longitude;

        Weather w1 = hashMap.get(key1);
        Weather w2 = hashMap.get(key2);

        if( w1 != null && w2 != null )
        {
            Intent comparison = new Intent(getBaseContext(),ComparisonActivity.class);

            comparison.putExtra("name1",w1.location.getCity()+", "+w1.location.getCountry());
            comparison.putExtra("name2",w2.location.getCity()+", "+w2.location.getCountry());

            comparison.putExtra("temp1",w1.temperature.getTemp()+"");
            comparison.putExtra("temp2",w2.temperature.getTemp()+"");

            comparison.putExtra("wind1",w1.wind.getSpeed()+"");
            comparison.putExtra("wind2",w2.wind.getSpeed()+"");

            comparison.putExtra("hum1",w1.currentCondition.getHumidity()+"");
            comparison.putExtra("hum2",w2.currentCondition.getHumidity()+"");

            comparison.putExtra("rain1",w1.rain.getAmmount()+"");
            comparison.putExtra("rain2",w2.rain.getAmmount()+"");

            comparison.putExtra("clouds1",w1.clouds.getPerc()+"");
            comparison.putExtra("clouds2",w2.clouds.getPerc()+"");

            startActivity(comparison);
        }
    }
}
