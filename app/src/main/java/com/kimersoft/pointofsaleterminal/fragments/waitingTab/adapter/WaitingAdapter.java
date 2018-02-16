package com.kimersoft.pointofsaleterminal.fragments.waitingTab.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kimersoft.pointofsaleterminal.R;
import com.kimersoft.pointofsaleterminal.models.WalletItem;
import com.kimersoft.pointofsaleterminal.utils.ItemSelector;

import java.util.ArrayList;

/**
 * Created by Maher on 04/12/2017.
 */
public class WaitingAdapter extends RecyclerView.Adapter<WaitingAdapter.RecycleView_Holder> {

    private final Context mContext;
    private final ArrayList<WalletItem> items;
    private String item;
    private Activity activity;


    public WaitingAdapter(Context context, ArrayList<WalletItem> items, String item) {

        this.mContext = context;
        this.items = items;
        this.item = item;
        this.activity = (Activity) context;
    }

    @Override
    public RecycleView_Holder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_waiting, parent, false);
        RecycleView_Holder vh = new RecycleView_Holder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecycleView_Holder holder, int position) {





    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }


    class RecycleView_Holder extends RecyclerView.ViewHolder {


        RelativeLayout rlBgd;
        LinearLayout llHeader;
        Button btnRed, btnGreen, btnYellow, btnBlue;
        TextView tvTable;


        public RecycleView_Holder(final View itemView) {
            super(itemView);

            rlBgd = itemView.findViewById(R.id.rl_bgd);
            llHeader = itemView.findViewById(R.id.ll_header);
            btnRed = itemView.findViewById(R.id.btn_red);
            btnGreen = itemView.findViewById(R.id.btn_green);
            btnYellow = itemView.findViewById(R.id.btn_yellow);
            btnBlue = itemView.findViewById(R.id.btn_blue);
            tvTable = itemView.findViewById(R.id.tv_table);


            new ItemSelector(activity, llHeader, rlBgd,tvTable, btnGreen, btnBlue, btnYellow, btnRed).switchItem(item);

        }

    }
}
