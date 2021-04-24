package com.duarbd.duarclientapp.presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.duarbd.duarclientapp.R;
import com.duarbd.duarclientapp.databinding.ActivityOngoingOrderDetailsBinding;
import com.duarbd.duarclientapp.model.ModelDeliveryRequest;

public class ActivityOngoingOrderDetails extends AppCompatActivity {
    private static final String TAG = "ActivityOngoingOrderDet";
    private ActivityOngoingOrderDetailsBinding binding;
    private ModelDeliveryRequest deliveryRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOngoingOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    void init(){
        deliveryRequest=getIntent().getParcelableExtra(getString(R.string.parcel));
        binding.layoutDeliveryDetails.tvDeliveryId.setText("Delivery Id: "+deliveryRequest.getDeliveryRequestId());
        binding.layoutDeliveryDetails.tvCustomerName.setText(deliveryRequest.getCustomerName());
        binding.layoutDeliveryDetails.tvCustomerNumber.setText(deliveryRequest.getCustomerNumber());
        binding.layoutDeliveryDetails.tvDeliveryAddress.setText(deliveryRequest.getDeliveryArea()+"\nAddress Note: "+
                (deliveryRequest.getDeliveryAddressExtra().equals("")?"N/A":deliveryRequest.getDeliveryAddressExtra()));
        updateDeliveryStatus(deliveryRequest.getDeliveryStatus()+"");
    }

    private void updateDeliveryStatus(String statusCode){
        Log.d(TAG, "updateDeliveryStatus: code="+statusCode);
        switch (statusCode){
            case "0":
                binding.layoutProgressStepview.statusIcon0.setBackgroundResource(R.drawable.stepview_complete);
                binding.layoutProgressStepview.statusIcon1.setBackgroundResource(R.drawable.stepview_incomplete);
                binding.layoutProgressStepview.statusIcon2.setBackgroundResource(R.drawable.stepview_incomplete);
                binding.layoutProgressStepview.statusIcon3.setBackgroundResource(R.drawable.stepview_incomplete);
                binding.layoutProgressStepview.statusIcon4.setBackgroundResource(R.drawable.stepview_incomplete);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    binding.layoutProgressStepview.statusText0.setTextColor(getResources().getColor(R.color.black0,null));
                    binding.layoutProgressStepview.statusText1.setTextColor(getResources().getColor(R.color.black8,null));
                    binding.layoutProgressStepview.statusText2.setTextColor(getResources().getColor(R.color.black8,null));
                    binding.layoutProgressStepview.statusText3.setTextColor(getResources().getColor(R.color.black8,null));
                    binding.layoutProgressStepview.statusText4.setTextColor(getResources().getColor(R.color.black8,null));
                }else {
                    binding.layoutProgressStepview.statusText0.setTextColor(getResources().getColor(R.color.black0));
                    binding.layoutProgressStepview.statusText1.setTextColor(getResources().getColor(R.color.black8));
                    binding.layoutProgressStepview.statusText2.setTextColor(getResources().getColor(R.color.black8));
                    binding.layoutProgressStepview.statusText3.setTextColor(getResources().getColor(R.color.black8));
                    binding.layoutProgressStepview.statusText4.setTextColor(getResources().getColor(R.color.black8));
                }
                break;
            case "1":
                binding.layoutProgressStepview.statusIcon0.setBackgroundResource(R.drawable.stepview_complete);
                binding.layoutProgressStepview.statusIcon1.setBackgroundResource(R.drawable.stepview_complete);
                binding.layoutProgressStepview.statusIcon2.setBackgroundResource(R.drawable.stepview_incomplete);
                binding.layoutProgressStepview.statusIcon3.setBackgroundResource(R.drawable.stepview_incomplete);
                binding.layoutProgressStepview.statusIcon4.setBackgroundResource(R.drawable.stepview_incomplete);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    binding.layoutProgressStepview.statusText0.setTextColor(getResources().getColor(R.color.black0,null));
                    binding.layoutProgressStepview.statusText1.setTextColor(getResources().getColor(R.color.black0,null));
                    binding.layoutProgressStepview.statusText2.setTextColor(getResources().getColor(R.color.black8,null));
                    binding.layoutProgressStepview.statusText3.setTextColor(getResources().getColor(R.color.black8,null));
                    binding.layoutProgressStepview.statusText4.setTextColor(getResources().getColor(R.color.black8,null));
                }else {
                    binding.layoutProgressStepview.statusText0.setTextColor(getResources().getColor(R.color.black0));
                    binding.layoutProgressStepview.statusText1.setTextColor(getResources().getColor(R.color.black0));
                    binding.layoutProgressStepview.statusText2.setTextColor(getResources().getColor(R.color.black8));
                    binding.layoutProgressStepview.statusText3.setTextColor(getResources().getColor(R.color.black8));
                    binding.layoutProgressStepview.statusText4.setTextColor(getResources().getColor(R.color.black8));
                }
                break;
            case "2":
                binding.layoutProgressStepview.statusIcon0.setBackgroundResource(R.drawable.stepview_complete);
                binding.layoutProgressStepview.statusIcon1.setBackgroundResource(R.drawable.stepview_complete);
                binding.layoutProgressStepview.statusIcon2.setBackgroundResource(R.drawable.stepview_complete);
                binding.layoutProgressStepview.statusIcon3.setBackgroundResource(R.drawable.stepview_incomplete);
                binding.layoutProgressStepview.statusIcon4.setBackgroundResource(R.drawable.stepview_incomplete);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    binding.layoutProgressStepview.statusText0.setTextColor(getResources().getColor(R.color.black0,null));
                    binding.layoutProgressStepview.statusText1.setTextColor(getResources().getColor(R.color.black0,null));
                    binding.layoutProgressStepview.statusText2.setTextColor(getResources().getColor(R.color.black0,null));
                    binding.layoutProgressStepview.statusText3.setTextColor(getResources().getColor(R.color.black8,null));
                    binding.layoutProgressStepview.statusText4.setTextColor(getResources().getColor(R.color.black8,null));
                }else {
                    binding.layoutProgressStepview.statusText0.setTextColor(getResources().getColor(R.color.black0));
                    binding.layoutProgressStepview.statusText1.setTextColor(getResources().getColor(R.color.black0));
                    binding.layoutProgressStepview.statusText2.setTextColor(getResources().getColor(R.color.black0));
                    binding.layoutProgressStepview.statusText3.setTextColor(getResources().getColor(R.color.black8));
                    binding.layoutProgressStepview.statusText4.setTextColor(getResources().getColor(R.color.black8));
                }
                break;
            case "3":
                binding.layoutProgressStepview.statusIcon0.setBackgroundResource(R.drawable.stepview_complete);
                binding.layoutProgressStepview.statusIcon1.setBackgroundResource(R.drawable.stepview_complete);
                binding.layoutProgressStepview.statusIcon2.setBackgroundResource(R.drawable.stepview_complete);
                binding.layoutProgressStepview.statusIcon3.setBackgroundResource(R.drawable.stepview_complete);
                binding.layoutProgressStepview.statusIcon4.setBackgroundResource(R.drawable.stepview_incomplete);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    binding.layoutProgressStepview.statusText0.setTextColor(getResources().getColor(R.color.black0,null));
                    binding.layoutProgressStepview.statusText1.setTextColor(getResources().getColor(R.color.black0,null));
                    binding.layoutProgressStepview.statusText2.setTextColor(getResources().getColor(R.color.black0,null));
                    binding.layoutProgressStepview.statusText3.setTextColor(getResources().getColor(R.color.black0,null));
                    binding.layoutProgressStepview.statusText4.setTextColor(getResources().getColor(R.color.black8,null));
                }else {
                    binding.layoutProgressStepview.statusText0.setTextColor(getResources().getColor(R.color.black0));
                    binding.layoutProgressStepview.statusText1.setTextColor(getResources().getColor(R.color.black0));
                    binding.layoutProgressStepview.statusText2.setTextColor(getResources().getColor(R.color.black0));
                    binding.layoutProgressStepview.statusText3.setTextColor(getResources().getColor(R.color.black0));
                    binding.layoutProgressStepview.statusText4.setTextColor(getResources().getColor(R.color.black8));
                }
                break;
            case "4":
                binding.layoutProgressStepview.statusIcon0.setBackgroundResource(R.drawable.stepview_complete);
                binding.layoutProgressStepview.statusIcon1.setBackgroundResource(R.drawable.stepview_complete);
                binding.layoutProgressStepview.statusIcon2.setBackgroundResource(R.drawable.stepview_complete);
                binding.layoutProgressStepview.statusIcon3.setBackgroundResource(R.drawable.stepview_complete);
                binding.layoutProgressStepview.statusIcon4.setBackgroundResource(R.drawable.stepview_complete);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    binding.layoutProgressStepview.statusText0.setTextColor(getResources().getColor(R.color.black0,null));
                    binding.layoutProgressStepview.statusText1.setTextColor(getResources().getColor(R.color.black0,null));
                    binding.layoutProgressStepview.statusText2.setTextColor(getResources().getColor(R.color.black0,null));
                    binding.layoutProgressStepview.statusText3.setTextColor(getResources().getColor(R.color.black0,null));
                    binding.layoutProgressStepview.statusText4.setTextColor(getResources().getColor(R.color.black0,null));
                }else {
                    binding.layoutProgressStepview.statusText0.setTextColor(getResources().getColor(R.color.black0));
                    binding.layoutProgressStepview.statusText1.setTextColor(getResources().getColor(R.color.black0));
                    binding.layoutProgressStepview.statusText2.setTextColor(getResources().getColor(R.color.black0));
                    binding.layoutProgressStepview.statusText3.setTextColor(getResources().getColor(R.color.black0));
                    binding.layoutProgressStepview.statusText4.setTextColor(getResources().getColor(R.color.black0));
                }
                break;

        }
    }
}