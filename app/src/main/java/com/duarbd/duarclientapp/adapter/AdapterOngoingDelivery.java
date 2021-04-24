package com.duarbd.duarclientapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duarbd.duarclientapp.R;
import com.duarbd.duarclientapp.interfaces.AdapterOngoingDeliveryCallbacks;
import com.duarbd.duarclientapp.model.ModelDeliveryRequest;

import java.util.List;

public class AdapterOngoingDelivery extends  RecyclerView.Adapter<AdapterOngoingDelivery.ViewHolderAdapterOngoingDelivery>{

    private List<ModelDeliveryRequest> ongoingDeliveryList;
    private Context context;
    private AdapterOngoingDeliveryCallbacks callbacks;

    public AdapterOngoingDelivery(List<ModelDeliveryRequest> ongoingDeliveryList, Context context, AdapterOngoingDeliveryCallbacks callbacks) {
        this.ongoingDeliveryList = ongoingDeliveryList;
        this.context = context;
        this.callbacks = callbacks;
    }

    @NonNull
    @Override
    public ViewHolderAdapterOngoingDelivery onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_ongoing_order,parent,false);
        ViewHolderAdapterOngoingDelivery viewHolderAdapterOngoingDelivery=new ViewHolderAdapterOngoingDelivery(view);
        return viewHolderAdapterOngoingDelivery;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapterOngoingDelivery holder, int position) {
        ModelDeliveryRequest delivery=ongoingDeliveryList.get(position);
        holder.coustomerName.setText("Customer Name: "+delivery.getCustomerName());
        holder.deliveryAddress.setText("Delivery Address: "+delivery.getDeliveryArea()+"\nAddress Note: "+
                (delivery.getDeliveryAddressExtra().equals("")?"N/A":delivery.getDeliveryAddressExtra()));
        holder.deliveryStage.setText("Stage"+"\n"+"\n"+(delivery.getDeliveryStatus()+1));

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.onViewDetailsClicked(position,delivery.getDeliveryRequestId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return ongoingDeliveryList.size();
    }

    class ViewHolderAdapterOngoingDelivery extends RecyclerView.ViewHolder{
        TextView coustomerName,deliveryAddress, deliveryStage;
        CardView container;

        public ViewHolderAdapterOngoingDelivery(@NonNull View itemView) {
            super(itemView);
            coustomerName=itemView.findViewById(R.id.tvOO_CustomerName);
            deliveryAddress=itemView.findViewById(R.id.tvOO_DeliveryAddress);
            deliveryStage =itemView.findViewById(R.id.tvOO_Stage);
            container=itemView.findViewById(R.id.ol_cardview);
        }
    }
}
