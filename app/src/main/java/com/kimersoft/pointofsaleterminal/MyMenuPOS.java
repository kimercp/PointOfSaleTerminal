package com.kimersoft.pointofsaleterminal;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MyMenuPOS extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_menu_pos);
        // full screen on device
        makeFullscreen();

        // buttons
        ImageView imgLogo = (ImageView) findViewById(R.id.imgLogo);
        ImageView imgMenu = (ImageView) findViewById(R.id.imgMenu);
        ImageView imgCardLynq = (ImageView) findViewById(R.id.imgCardLynq);

        // set OnClickListener for each button
        imgLogo.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        imgCardLynq.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imgLogo:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.imgMenu:
                intent = new Intent(this, LynqMenu.class);
                startActivity(intent);
                break;
            case R.id.imgCardLynq:
                intent = new Intent(this, CardLynqPSAM2.class);
                startActivity(intent);
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
