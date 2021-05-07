package com.duarbd.duarclientapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duarbd.duarclientapp.R;
import com.duarbd.duarclientapp.interfaces.AdapterDeliveryHistoryCallbacks;
import com.duarbd.duarclientapp.model.ModelDeliveryRequest;

import java.util.List;

public class AdapterDeliveryHistory extends RecyclerView.Adapter<AdapterDeliveryHistory.ViewHolderAdapterDeliveryHistory> {
    private static final String TAG = "AdapterDeliveryHistory";
    List<ModelDeliveryRequest> deliveryHistory;
    Context context;
    AdapterDeliveryHistoryCallbacks callbacks;



    public AdapterDeliveryHistory(List<ModelDeliveryRequest> deliveryHistory, Context context, AdapterDeliveryHistoryCallbacks callbacks) {
        this.deliveryHistory = deliveryHistory;
        this.context = context;
        this.callbacks = callbacks;

    }

    @NonNull
    @Override
    public ViewHolderAdapterDeliveryHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_delivery_history,parent,false);
        ViewHolderAdapterDeliveryHistory viewHolderAdapterDeliveryHistory=new ViewHolderAdapterDeliveryHistory(view);
        return viewHolderAdapterDeliveryHistory;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapterDeliveryHistory holder, int position) {
        ModelDeliveryRequest deliveryRequest=deliveryHistory.get(position);
        holder.tvdh_deliveryid.setText("Delivery ID: "+deliveryRequest.getDeliveryRequestId());
        holder.tvdh_deliverycharge.setText("Delivery Charge: "+deliveryRequest.getDeliveryCharge()+" Tk");
        holder.tvdh_pickupCharge.setText("Pickup  Charge: "+deliveryRequest.getPickupCharge()+" Tk");
        holder.tvdh_price.setText("Bill: "+deliveryRequest.getProductPrice()+" Tk");

        holder.tvdh_vieworderdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.onViewDetailsClicked(deliveryRequest);
            }
        });

    }

    @Override
    public int getItemCount() {
        return deliveryHistory.size();
    }

    class  ViewHolderAdapterDeliveryHistory extends RecyclerView.ViewHolder{
        TextView tvdh_deliveryid,tvdh_price,tvdh_deliverycharge,tvdh_pickupCharge,tvdh_vieworderdetails;

        public ViewHolderAdapterDeliveryHistory(@NonNull View itemView) {
            super(itemView);
            tvdh_deliveryid=itemView.findViewById(R.id.tvdh_deliveryid);
            tvdh_price=itemView.findViewById(R.id.tvdh_price);
            tvdh_deliverycharge=itemView.findViewById(R.id.tvdh_deliverycharge);
            tvdh_pickupCharge=itemView.findViewById(R.id.tvdh_pickupCharge);
            tvdh_vieworderdetails=itemView.findViewById(R.id.tvdh_vieworderdetails);
        }
    }
}
