package com.kimersoft.pointofsaleterminal;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.com.kimersoft.ypos.TestActivity;

import junit.framework.Test;

public class MyMenuPOS extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_menu_pos);
        // full screen on device
        makeFullscreen();

        // buttons
        ImageView imgLogo = (ImageView) findViewById(R.id.imgLogo);
        LinearLayout linearLayoutMenu = (LinearLayout) findViewById(R.id.layoutMenu);
        LinearLayout linearLayoutCardLynq = (LinearLayout) findViewById(R.id.layoutCardLynq);

        LinearLayout linearLayoutEvents = (LinearLayout) findViewById(R.id.layoutEvents);
        LinearLayout linearLayoutAnalytics = (LinearLayout) findViewById(R.id.layoutAnalytics);

        // set OnClickListener for each button
        imgLogo.setOnClickListener(this);
        linearLayoutMenu.setOnClickListener(this);
        linearLayoutCardLynq.setOnClickListener(this);

        linearLayoutEvents.setOnClickListener(this);
        linearLayoutAnalytics.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imgLogo:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.layoutMenu:
                intent = new Intent(this, LynqMenu.class);
                startActivity(intent);
                break;
            case R.id.layoutCardLynq:
//                intent = new Intent(this, CardLynqPSAM2.class);
                intent = new Intent(this, CardLynq.class);
                startActivity(intent);
                break;
            case R.id.layoutEvents:
                intent = new Intent(getBaseContext(), TestActivity.class);
                startActivity(intent);
                break;
            case R.id.layoutAnalytics:
                intent = new Intent(this, TestActivity.class);
                startActivity(intent);
                break;
        }
    }


    // hide UI action bar and make the app fullscreen
    public void makeFullscreen() {
        getSupportActionBar().hide();
        // API 19 (Kit Kat)
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    // View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        } else {
            if (Build.VERSION.SDK_INT > 10) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }
}
