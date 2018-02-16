package com.kimersoft.pointofsaleterminal.fragments.waitingTab;

import com.kimersoft.pointofsaleterminal.models.WalletItem;

import java.util.ArrayList;

/**
 * Created by maher on 05/12/2017.
 */

public interface WaitingView {

    void showProgress();
    void hideProgress();
    void networkError();
    void loadAllAssets(ArrayList<WalletItem> walletItems);
    void loadNoAssets(ArrayList<WalletItem> walletItems);

}
