package com.kimersoft.pointofsaleterminal;

import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @ClassName: IDCardActivity.java
 * @Description: 身份信息页面
 * @author zkc-soft
 * Created by Administrator on 2017/4/12 15:16
 */

public class IDCardActivity extends BaseActivity implements View.OnClickListener{

    private Button btn_getIdentifyInfo;
    private TextView tv_idInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);
        initView();
    }

    private void initView() {
        btn_getIdentifyInfo = (Button) findViewById(R.id.btn_getIdentifyInfo);
        tv_idInfo = (TextView) findViewById(R.id.tv_idInfo);
        btn_getIdentifyInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_getIdentifyInfo:
                try {
                    String result = mIzkcService.getIdentifyInfo();
                    tv_idInfo.setText(result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
