package com.kimersoft.pointofsaleterminal.fragments.waitingTab;

import android.content.Context;

import com.kimersoft.pointofsaleterminal.models.WalletItem;
import com.kimersoft.pointofsaleterminal.volleyHelpers.JsonToObjectParser;
import com.kimersoft.pointofsaleterminal.volleyHelpers.VolleyCallback;
import com.kimersoft.pointofsaleterminal.volleyHelpers.WaitngManager;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by maher on 14/11/2017.
 */

public class WaitingInteractor {


    public interface OnWaitngFinishedListener {
        void onSuccess(ArrayList<WalletItem> walletItems);

        void onError();
    }


    public void getListAssets(String wallet, String code, final OnWaitngFinishedListener listener, final Context context) {
        WaitngManager waitngManager = new WaitngManager(context);
        waitngManager.getWaitingAssets(wallet, code, new VolleyCallback() {
            @Override
            public void onSuccess(Object response) {

                ArrayList<WalletItem> walletItems = new JsonToObjectParser().parseWalletData(((JSONObject) response));
                listener.onSuccess(walletItems);
            }

            @Override
            public void onError(Object error) {
                listener.onError();
            }
        });
    }
}
