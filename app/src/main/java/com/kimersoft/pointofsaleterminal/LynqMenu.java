package com.kimersoft.pointofsaleterminal;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kimersoft.pointofsaleterminal.fragments.allTab.AllFragment;
import com.kimersoft.pointofsaleterminal.fragments.closedTab.ClosedFragment;
import com.kimersoft.pointofsaleterminal.fragments.deliverTab.DeliverFragment;
import com.kimersoft.pointofsaleterminal.fragments.pickupTab.PickupFragment;
import com.kimersoft.pointofsaleterminal.fragments.startedTab.StartedFragment;
import com.kimersoft.pointofsaleterminal.fragments.waitingTab.WaitingFragment;

import java.util.ArrayList;

public class LynqMenu extends AppCompatActivity {

    private RelativeLayout rlAll, rlWaiting, rlStarted, rlDeliver, rlPickup, rlClosed, rlRedeem, rlSettings, rlLynq;
    private LinearLayout llMainContent;
    private ArrayList<RelativeLayout> navBar;
    private int waitingBg, startedBg, deliverBg, pickupBg, whiteBg;
    private Drawable waitingSelected, waitingUnselected, startedSelected, startedUnselected, pickupSelected, pickupUnselected,
            deliverSelected, deliverUnselected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lynq_menu);

        //Initializing values
        navBar = new ArrayList<>();
        waitingBg = ContextCompat.getColor(this, R.color.colorWaitingBackground);
        startedBg = ContextCompat.getColor(this, R.color.colorStartedBackground);
        deliverBg = ContextCompat.getColor(this, R.color.colorDeliverBackground);
        pickupBg = ContextCompat.getColor(this, R.color.colorPickupBackground);
        whiteBg = ContextCompat.getColor(this, R.color.colorWhite);
        waitingSelected = ContextCompat.getDrawable(this, R.drawable.btn_waiting_nav_bar_selected);
        waitingUnselected = ContextCompat.getDrawable(this, R.drawable.btn_waiting_nav_bar_unselected);
        startedSelected = ContextCompat.getDrawable(this, R.drawable.btn_started_nav_bar_selected);
        startedUnselected = ContextCompat.getDrawable(this, R.drawable.btn_started_nav_bar_unselected);
        pickupSelected = ContextCompat.getDrawable(this, R.drawable.btn_pickup_nav_bar_selected);
        pickupUnselected = ContextCompat.getDrawable(this, R.drawable.btn_pickup_nav_bar_unselected);
        deliverSelected = ContextCompat.getDrawable(this, R.drawable.btn_deliver_nav_bar_selected);
        deliverUnselected = ContextCompat.getDrawable(this, R.drawable.btn_deliver_nav_bar_unselected);

        //Bind UI
        llMainContent = findViewById(R.id.ll_main_content);
        rlAll = findViewById(R.id.rl_all);
        rlWaiting = findViewById(R.id.rl_waiting);
        rlStarted = findViewById(R.id.rl_started);
        rlDeliver = findViewById(R.id.rl_deliver);
        rlPickup = findViewById(R.id.rl_pickup);
        rlClosed = findViewById(R.id.rl_closed);
        rlRedeem = findViewById(R.id.rl_redeem);
        rlSettings = findViewById(R.id.rl_settings);
        rlLynq = findViewById(R.id.rl_lynq);

        //Filling NavBar list with buttons
        navBar.add(rlAll);
        navBar.add(rlWaiting);
        navBar.add(rlStarted);
        navBar.add(rlDeliver);
        navBar.add(rlPickup);
        navBar.add(rlClosed);
        navBar.add(rlLynq);


        //Calling first fragment
        onClickBehavior(whiteBg, waitingSelected, waitingUnselected, navBar, rlAll);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_recycler_content, new AllFragment()).commit();



        //Perform NavBar Clicks
        rlWaiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBehavior(waitingBg, waitingSelected, waitingUnselected, navBar, rlWaiting);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_recycler_content, new WaitingFragment()).commit();

            }
        });
        rlStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBehavior(startedBg, startedSelected, startedUnselected, navBar, rlStarted);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_recycler_content, new StartedFragment()).commit();
            }
        });
        rlDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBehavior(deliverBg, deliverSelected, deliverUnselected, navBar, rlDeliver);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_recycler_content, new DeliverFragment()).commit();
            }
        });
        rlPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBehavior(pickupBg, pickupSelected, pickupUnselected, navBar, rlPickup);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_recycler_content, new PickupFragment()).commit();
            }
        });
        rlAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBehavior(whiteBg, waitingSelected, waitingUnselected, navBar, rlAll);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_recycler_content, new AllFragment()).commit();
            }
        });
        rlClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBehavior(whiteBg, waitingSelected, waitingUnselected, navBar, rlClosed);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_recycler_content, new ClosedFragment()).commit();
            }
        });
        rlRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(LynqMenu.this).initiateScan();
            }
        });
    }

    private void onClickBehavior(int bgdColor, Drawable selected, Drawable unselected, ArrayList<RelativeLayout> navBar, RelativeLayout current){

        //Setting main background color
        llMainContent.setBackgroundColor(bgdColor);

        //Changing NavBar colors based on selected tab
        current.setBackground(selected);
        for(RelativeLayout rl : navBar){
            if(rl != current){
                rl.setBackground(unselected);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
