package com.duarbd.duarclientapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duarbd.duarclientapp.R;
import com.duarbd.duarclientapp.interfaces.AdapterPaymentCallback;
import com.duarbd.duarclientapp.model.ModelDeliveryRequest;

import java.util.List;

public class AdapterPayment extends  RecyclerView.Adapter<AdapterPayment.ViewHolderAdapterPayment>{

    List<ModelDeliveryRequest> deliveryRequestList;
    Context context;
    AdapterPaymentCallback callback;

    public AdapterPayment(List<ModelDeliveryRequest> deliveryRequestList, Context context, AdapterPaymentCallback callback) {
        this.deliveryRequestList = deliveryRequestList;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolderAdapterPayment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_patment,parent,false);
        ViewHolderAdapterPayment viewHolderAdapterPayment=new ViewHolderAdapterPayment(view);
        return viewHolderAdapterPayment;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapterPayment holder, int position) {
        ModelDeliveryRequest deliveryRequest=deliveryRequestList.get(position);
        holder.tvap_deliveryid.setText("Delivery ID: "+deliveryRequest.getDeliveryRequestId());
        holder.tvap_deliverycharge.setText("Delivery Charge: "+deliveryRequest.getDeliveryCharge()+" Tk");
        holder.tvap_pickupCharge.setText("Pickup  Charge: "+deliveryRequest.getPickupCharge()+" Tk");
        holder.tvap_price.setText("Bill: "+deliveryRequest.getProductPrice()+" Tk");

        holder.tvap_vieworderdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onViewDetailsClicked(deliveryRequest);
            }
        });
        if(deliveryRequest.getClientPaymentStatus().equals("due")){
            holder.tvap_PaymentStatus.setText("Due");
            holder.tvap_PaymentStatus.setBackground(context.getResources().getDrawable(R.drawable.bg_cr4_red_fill));
            holder.tvap_markAsPaid.setVisibility(View.VISIBLE);
        }else {
            holder.tvap_PaymentStatus.setText("Received");
            holder.tvap_PaymentStatus.setBackground(context.getResources().getDrawable(R.drawable.bg_cr4_green));
            holder.tvap_markAsPaid.setVisibility(View.GONE);
        }

        holder.tvap_markAsPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.markAsPaid(deliveryRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryRequestList.size();
    }

    class ViewHolderAdapterPayment extends RecyclerView.ViewHolder {

        TextView tvap_deliveryid,tvap_price,tvap_deliverycharge,tvap_pickupCharge,tvap_vieworderdetails;
        TextView tvap_PaymentStatus,tvap_markAsPaid;
        public ViewHolderAdapterPayment(@NonNull View itemView) {
            super(itemView);
            tvap_deliveryid=itemView.findViewById(R.id.tvap_deliveryid);
            tvap_price=itemView.findViewById(R.id.tvap_price);
            tvap_deliverycharge=itemView.findViewById(R.id.tvap_deliverycharge);
            tvap_pickupCharge=itemView.findViewById(R.id.tvap_pickupCharge);
            tvap_vieworderdetails=itemView.findViewById(R.id.tvap_vieworderdetails);
            tvap_PaymentStatus=itemView.findViewById(R.id.tvap_PaymentStatus);
            tvap_markAsPaid=itemView.findViewById(R.id.tvap_markAsPaid);
        }
    }
}
