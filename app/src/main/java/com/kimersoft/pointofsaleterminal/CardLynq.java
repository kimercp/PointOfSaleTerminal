package com.kimersoft.pointofsaleterminal;

import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kimersoft.pointofsaleterminal.util.StringUtility;


public class CardLynq extends BaseActivity implements View.OnClickListener {

    public static final String TAG = CardLynq.class.getSimpleName();
    Button btn_init, btn_power_on, btn_power_off, btn_random;
    Button btn_reader;
    EditText et_receiver;
    int cardLocation = 1;
    int powerValue = 2;

    private searchingCardThread myThread;
    int counter = 0;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.cardlynqtest); this one is fully working with extra buttons
        setContentView(R.layout.activity_card_lynq);

//        btn_init = findViewById(R.id.btn_init);
//        btn_power_on = findViewById(R.id.btn_power_on);
//        btn_power_off = findViewById(R.id.btn_power_off);
//        btn_random = findViewById(R.id.btn_random);
        et_receiver = findViewById(R.id.et_display);
        btn_reader = findViewById(R.id.btn_reader);

//        btn_init.setOnClickListener(this);
//        btn_power_on.setOnClickListener(this);
//        btn_power_off.setOnClickListener(this);
//        btn_random.setOnClickListener(this);
        btn_reader.setOnClickListener(this);
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
            case R.id.btn_power_off:
                closePower();
                break;
            case R.id.btn_random:
                testRandom();
                break;
            case R.id.btn_reader:
                startLoop();
                break;
            default:
                break;
        }
    }

    private void startLoop() {
        counter = 0;
        btn_reader.setEnabled(false);
        myThread = new searchingCardThread();
        myThread.start();
    }

    public class searchingCardThread extends Thread {
        private boolean keepRunning = true;

        public void cancel() {
            keepRunning = false;
        }

        private boolean openPowerInThread() {
            int s;
            try {
                s = mIzkcService.openCard(cardLocation);
                if (s != 0) {
                    Log.i(TAG, "open success!");
                    return true;
                }
                return false;
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }

        private String resetInThread() {
            try {
                byte[] dataBuf = mIzkcService.ResetCard(powerValue);
                if (dataBuf != null) {
                    return StringUtility.ByteArrayToString(dataBuf, dataBuf.length);
                }
                return "Card did not found";
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        public void run() {
            while (keepRunning) {
                try {

                    Thread.sleep(500);
                    message += "*";

                    boolean isPowerOn = openPowerInThread();
                    if (isPowerOn) {
                        message = resetInThread();
                        if (message != "Card did not found") keepRunning = false;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_receiver.setText(message);
                            if (counter > 33) btn_reader.setEnabled(true);

                        }
                    });

                    counter++;
                    System.out.println(counter);
                    if (counter > 33) {
                        message = "";
                        cancel();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
            // et_receiver.setText(s+"");
            if (s != 0) {
                Log.i(TAG, "open success!");
                et_receiver.setText(s + " open power success");
            } else et_receiver.setText(s + " open power failed");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, "Catch error exception - openPower", Toast.LENGTH_SHORT).show();
        }
    }

    private void reset() {
        try {
            byte[] dataBuf = mIzkcService.ResetCard(powerValue);
            if (dataBuf != null) {
                et_receiver.setText(StringUtility.ByteArrayToString(dataBuf, dataBuf.length));
            }
            et_receiver.setText(" reset failed - dataBuf is null");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, "Catch error exception - reset", Toast.LENGTH_SHORT).show();
        }
    }


    private void testRandom() {
        byte[] btRandom = new byte[]{(byte) 0x00, (byte) 0x84,
                (byte) 0x00, (byte) 0x00, (byte) 0x08};
        byte[] dataBuf;
        try {
            dataBuf = mIzkcService.CardApdu(btRandom);
            if (dataBuf != null) {
                et_receiver.setText(StringUtility.ByteArrayToString(dataBuf, dataBuf.length));
            }
            et_receiver.setText(" test  failed - dataBuf is null");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, "Catch error exception - test", Toast.LENGTH_SHORT).show();
        }
    }

    private void closePower() {
        int s;
        try {
            s = mIzkcService.CloseCard();
            if (s != 0) {
                Log.i(TAG, "close success!");
                et_receiver.setText(s + " close power success");
            } else et_receiver.setText(s + " close power failed");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, "Catch error exception - close power", Toast.LENGTH_SHORT).show();
        }
    }
}