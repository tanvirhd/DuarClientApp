package com.duarbd.duarclientapp.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.duarbd.duarclientapp.R;
import com.duarbd.duarclientapp.adapter.AdapterDeliveryHistory;
import com.duarbd.duarclientapp.databinding.ActivityDeliveryHistoryBinding;
import com.duarbd.duarclientapp.interfaces.AdapterDeliveryHistoryCallbacks;
import com.duarbd.duarclientapp.model.ModelDeliveryRequest;
import com.duarbd.duarclientapp.network.viewmodel.ViewModelDuarClientApp;
import com.duarbd.duarclientapp.tools.GlobalKey;
import com.duarbd.duarclientapp.tools.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityDeliveryHistory extends AppCompatActivity implements AdapterDeliveryHistoryCallbacks {
    private static final String TAG = "ActivityDeliveryHistory";
    private ActivityDeliveryHistoryBinding binding;
    private ViewModelDuarClientApp viewModelDuarClientApp;

    private List<ModelDeliveryRequest> deliveryHistory;
    private AdapterDeliveryHistory adapterDeliveryHistory;
    private Dialog dialogLoading;
    public DecimalFormat twodigits = new DecimalFormat("00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDeliveryHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        binding.showDEliveryHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date=twodigits.format(binding.datepicker.getSelectedDay())+"-"+
                         twodigits.format( (binding.datepicker.getSelectedMonth()+1))+"-"+
                        twodigits.format(binding.datepicker.getSelectedYear());
                Log.d(TAG, "onClick: "+date);
                getDeliveryHistoryFromServer(Utils.getPref(GlobalKey.CLIENT_ID,""),date);
            }
        });
    }

    private  void init(){

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Previous Deliveries");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dialogLoading=Utils.setupLoadingDialog(ActivityDeliveryHistory.this);
        binding.datepicker.setFirstVisibleDate(2021, Calendar.JANUARY, 01);
        binding.datepicker.setLastVisibleDate(2021, Calendar.DECEMBER, 31);
        binding.datepicker.setFollowScroll(true);
        String date[]= Utils.getCustentDateArray();
        binding.datepicker.setSelectedDate(Integer.valueOf(date[2]),Integer.valueOf(date[1])-1,Integer.valueOf(date[0]));

        viewModelDuarClientApp=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelDuarClientApp.class);

        deliveryHistory=new ArrayList<>();
        adapterDeliveryHistory=new AdapterDeliveryHistory(deliveryHistory,ActivityDeliveryHistory.this,ActivityDeliveryHistory.this);
        binding.recycDeliveryHistory.setLayoutManager(new LinearLayoutManager(ActivityDeliveryHistory.this));
        binding.recycDeliveryHistory.setAdapter(adapterDeliveryHistory);

    }

    private  void getDeliveryHistoryFromServer(String clientid,String date){
        dialogLoading.show();
        viewModelDuarClientApp.getDeliveryHistoryByClientId(clientid).observe(ActivityDeliveryHistory.this,
                new Observer<List<ModelDeliveryRequest>>() {
                    @Override
                    public void onChanged(List<ModelDeliveryRequest> modelDeliveryRequests) {
                        if(modelDeliveryRequests!=null&&!modelDeliveryRequests.get(0).getResponse().equals("0")){
                            if(!modelDeliveryRequests.get(0).getStatus().equals("NothingFound")){
                                deliveryHistory.clear();
                                for(ModelDeliveryRequest delivery:modelDeliveryRequests){
                                    String palceddate[]=delivery.getRequestPlacedTime().split(" ");
                                    if(date.equals(palceddate[0])){
                                        deliveryHistory.add(delivery);
                                    }
                                }
                                adapterDeliveryHistory.notifyDataSetChanged();
                                dialogLoading.dismiss();
                            }else {
                                deliveryHistory.clear();
                                adapterDeliveryHistory.notifyDataSetChanged();
                                dialogLoading.dismiss();
                                Toast.makeText(ActivityDeliveryHistory.this, "0 History Found", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityDeliveryHistory.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    @Override
    public void onViewDetailsClicked(ModelDeliveryRequest deliveryRequest) {
        startActivity(new Intent(ActivityDeliveryHistory.this,ActivityOngoingOrderDetails.class)
          .putExtra(getString(R.string.parcel),deliveryRequest));
    }
}