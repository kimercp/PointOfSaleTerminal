package com.kimersoft.pointofsaleterminal.fragments.waitingTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimersoft.pointofsaleterminal.R;
import com.kimersoft.pointofsaleterminal.fragments.waitingTab.adapter.WaitingAdapter;
import com.kimersoft.pointofsaleterminal.models.User;
import com.kimersoft.pointofsaleterminal.models.WalletItem;
import com.kimersoft.pointofsaleterminal.utils.ItemSelector;

import java.util.ArrayList;

public class WaitingFragment extends Fragment implements WaitingView{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rvWaiting;




    public WaitingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaitingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WaitingFragment newInstance(String param1, String param2) {
        WaitingFragment fragment = new WaitingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_waiting, container, false);

        rvWaiting=view.findViewById(R.id.rv_waiting);
        rvWaiting.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        WaitingAdapter waitingAdapter=new WaitingAdapter(getActivity(),new ArrayList<WalletItem>(), ItemSelector.WAITING_ITEM);
        rvWaiting.setAdapter(waitingAdapter);


        User user=User.getCurrentUser(getActivity());
        WaitingPresenter waitingPresenter=new WaitingPresenter(this,getActivity());
        waitingPresenter.getListAssets(user.getWalletKey(),"001L9qun1bcf8");

        return view;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void networkError() {

    }

    @Override
    public void loadAllAssets(ArrayList<WalletItem> walletItems) {
        WaitingAdapter waitingAdapter=new WaitingAdapter(getActivity(),walletItems, ItemSelector.WAITING_ITEM);
        rvWaiting.setAdapter(waitingAdapter);
    }

    @Override
    public void loadNoAssets(ArrayList<WalletItem> walletItems) {

    }
}
