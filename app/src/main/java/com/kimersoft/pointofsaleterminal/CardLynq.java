package com.kimersoft.pointofsaleterminal;

import android.os.Build;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kimersoft.pointofsaleterminal.util.StringUtility;

public class CardLynq extends BaseActivity implements View.OnClickListener {

    public static final String TAG = PSAMActivity.class.getSimpleName();
    Button btn_init,btn_power_on;
    EditText et_receiver;
    int cardLocation = 1;
    int powerValue = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardlynqtest);

        btn_init = (Button) findViewById(R.id.btn_init);
        btn_power_on = (Button) findViewById(R.id.btn_power_on);
        et_receiver = (EditText) findViewById(R.id.et_display);

        btn_init.setOnClickListener(this);
        btn_power_on.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_init:
                reset();
                break;
            case R.id.btn_power_on:
                openPower();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onStop() {
        try {
            mIzkcService.CloseCard();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.onStop();
    }

    private void openPower() {
        int s;
        try {
            s = mIzkcService.openCard(cardLocation);
            et_receiver.setText(s+"");
            if (s != 0) {
                Log.i(TAG, "open success!");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void reset() {
        try {
            byte[] dataBuf=mIzkcService.ResetCard(powerValue);
            if (dataBuf != null) {
                et_receiver.setText(StringUtility.ByteArrayToString(dataBuf, dataBuf.length));
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}