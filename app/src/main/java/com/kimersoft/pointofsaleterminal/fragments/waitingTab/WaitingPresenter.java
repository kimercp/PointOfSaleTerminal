package com.kimersoft.pointofsaleterminal.fragments.waitingTab;

import android.content.Context;

import com.kimersoft.pointofsaleterminal.models.WalletItem;

import java.util.ArrayList;

/**
 * Created by maher on 14/11/2017.
 */

public class WaitingPresenter implements WaitingInteractor.OnWaitngFinishedListener {

    private WaitingView waitingView;
    private WaitingInteractor waitingInteractor;
    private Context mcContext;

    public WaitingPresenter(WaitingView waitingView, Context mcContext) {
        this.waitingView = waitingView;
        this.mcContext = mcContext;
        waitingInteractor = new WaitingInteractor();
    }

    public void getListAssets(String wallet,String code) {
            waitingView.showProgress();
            waitingInteractor.getListAssets(wallet,code, this,mcContext);
        }

    @Override
    public void onSuccess(ArrayList<WalletItem> walletItems) {
        waitingView.hideProgress();
        if(!walletItems.isEmpty())
            waitingView.loadAllAssets(walletItems);
        else waitingView.loadNoAssets(walletItems);

    }

    @Override
    public void onError() {
        waitingView.hideProgress();
        waitingView.networkError();
    }

}
