package com.pmesa48.weatherinfo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import model.BestWeather;

public class ComparisonActivity extends AppCompatActivity {

    //Los del layout de arriba
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txt4;
    private TextView txt5;
    private ImageView img1;

    //Los del layout de abajo
    private TextView txt6;
    private TextView txt7;
    private TextView txt8;
    private TextView txt9;
    private TextView txt10;
    private ImageView img2;

    private RelativeLayout rl1;

    private RelativeLayout rl2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        initComponents();
        fillContent(getIntent());
    }


    public void initComponents()
    {
        txt1 = (TextView)findViewById(R.id.textView);
        txt2 = (TextView)findViewById(R.id.textView2);
        txt3 = (TextView)findViewById(R.id.textView3);
        txt4 = (TextView)findViewById(R.id.textView4);
        txt5 = (TextView)findViewById(R.id.textView5);
        txt6 = (TextView)findViewById(R.id.textView6);
        txt7 = (TextView)findViewById(R.id.textView7);
        txt8 = (TextView)findViewById(R.id.textView8);
        txt9 = (TextView)findViewById(R.id.textView9);
        txt10 = (TextView)findViewById(R.id.textView10);

        img1 = (ImageView)findViewById(R.id.imageView);
        img2 = (ImageView)findViewById(R.id.imageView2);

        rl1 = (RelativeLayout)findViewById(R.id.topLayout);
        rl2 = (RelativeLayout)findViewById(R.id.bottomLayout);

    }

    public void fillContent(Intent intent)
    {

        float temp1 = Float.parseFloat(intent.getStringExtra("temp1"));
        float temp2 = Float.parseFloat(intent.getStringExtra("temp2"));

        float wind1 = Float.parseFloat(intent.getStringExtra("wind1"));
        float wind2 = Float.parseFloat(intent.getStringExtra("wind2"));

        float rain1 = Float.parseFloat(intent.getStringExtra("rain1"));
        float rain2 = Float.parseFloat(intent.getStringExtra("rain2"));

        int cloud1 = Integer.parseInt(intent.getStringExtra("clouds1"));
        int cloud2 = Integer.parseInt(intent.getStringExtra("clouds2"));

        BestWeather bw = new BestWeather(temp1,temp2,rain1,rain2,wind1,wind2,cloud1,cloud2);

        if( bw.isOneBetterThanTwo( ) )
        {
            rl1.setBackgroundColor(Color.parseColor("#98fb98"));
            rl2.setBackgroundColor(Color.parseColor("#ffe3eb"));
        }
        else
        {
            rl2.setBackgroundColor(Color.parseColor("#98fb98"));
            rl1.setBackgroundColor(Color.parseColor("#ffe3eb"));
        }


        txt1.setText(intent.getStringExtra("name1"));
        txt2.setText("Temperatura: " + intent.getStringExtra("temp1") + "ºC");
        txt3.setText("Viento: " + intent.getStringExtra("wind1")+"m/s");
        txt4.setText("Lluvia: " + intent.getStringExtra("rain1")+"ml");
        txt5.setText("Nubes: " + intent.getStringExtra("clouds1")+"%");

        txt6.setText(intent.getStringExtra("name2"));
        txt7.setText("Temperatura: " + intent.getStringExtra("temp2")+"ºC");
        txt8.setText("Viento: " + intent.getStringExtra("wind2")+"m/s");
        txt9.setText("Lluvia: " + intent.getStringExtra("rain2")+"ml");
        txt10.setText("Nubes: " + intent.getStringExtra("clouds2")+"%");
    }

    public void checkValues()
    {

    }

}
