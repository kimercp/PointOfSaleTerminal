package com.kimersoft.pointofsaleterminal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.smartdevice.aidl.IZKCService;

public class zkcInterface extends AppCompatActivity {

    public static IZKCService mIzkcService;
    public boolean bindSuccessFlag = false;
    public static int module_flag = 0;
    public static int DEVICE_MODEL = 0;
    // variable serviceConnection
    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIzkcService = IZKCService.Stub.asInterface(service);
            if(mIzkcService!=null){
                try {
                    Toast.makeText(zkcInterface.this, getString(R.string.service_bind_success), Toast.LENGTH_SHORT).show();
                    DEVICE_MODEL = mIzkcService.getDeviceModel();
                    mIzkcService.setModuleFlag(module_flag);
                    if(module_flag==3){
                        mIzkcService.openBackLight(1);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                bindSuccessFlag = true;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIzkcService = null;
            bindSuccessFlag = false;
            Toast.makeText(zkcInterface.this, getString(R.string.service_bind_fail), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zkc_interface);

        // bind service with android apk installed earlier on device
        Intent intent = new Intent("com.zkc.aidl.all");
        intent.setPackage("com.smartdevice.aidl");
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);


    }
}
