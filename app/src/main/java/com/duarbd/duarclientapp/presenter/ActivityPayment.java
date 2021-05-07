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
import com.duarbd.duarclientapp.adapter.AdapterPayment;
import com.duarbd.duarclientapp.databinding.ActivityPaymentBinding;
import com.duarbd.duarclientapp.interfaces.AdapterDeliveryHistoryCallbacks;
import com.duarbd.duarclientapp.interfaces.AdapterPaymentCallback;
import com.duarbd.duarclientapp.model.ModelDeliveryRequest;
import com.duarbd.duarclientapp.model.ModelResponse;
import com.duarbd.duarclientapp.network.viewmodel.ViewModelDuarClientApp;
import com.duarbd.duarclientapp.tools.GlobalKey;
import com.duarbd.duarclientapp.tools.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityPayment extends AppCompatActivity implements AdapterPaymentCallback {
    private static final String TAG = "ActivityPayment";
    private ActivityPaymentBinding binding;

    private ViewModelDuarClientApp viewModelDuarClientApp;

    private List<ModelDeliveryRequest> deliveryHistory;
    private AdapterPayment adapterPayment;
    private Dialog dialogLoading;
    private int receivableAmount;
    public static DecimalFormat twodigits = new DecimalFormat("00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();


        binding.calculateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = twodigits.format(binding.datepicker.getSelectedDay()) + "-" +
                        twodigits.format((binding.datepicker.getSelectedMonth() + 1)) + "-" +
                        twodigits.format(binding.datepicker.getSelectedYear());
                getDeliveryHistoryFromServer(Utils.getPref(GlobalKey.CLIENT_ID, ""), date);
            }
        });
    }

    private void getDeliveryHistoryFromServer(String clientid, String date) {
        dialogLoading.show();
        viewModelDuarClientApp.getDeliveryHistoryByClientId(clientid).observe(ActivityPayment.this,
                new Observer<List<ModelDeliveryRequest>>() {
                    @Override
                    public void onChanged(List<ModelDeliveryRequest> modelDeliveryRequests) {
                        if (modelDeliveryRequests != null && modelDeliveryRequests.get(0).getResponse().equals("1")) {
                            if (modelDeliveryRequests.get(0).getStatus().equals("NothingFound")) {
                                receivableAmount=0;binding.tvAmount.setText("Total Receivable 00 Tk");
                                deliveryHistory.clear(); adapterPayment.notifyDataSetChanged();
                                binding.tvNothingFound.setVisibility(View.VISIBLE);
                                binding.recycDeliveryHistory.setVisibility(View.GONE);
                                dialogLoading.dismiss();
                            } else {
                                deliveryHistory.clear();receivableAmount=0;
                                for (ModelDeliveryRequest delivery : modelDeliveryRequests) {
                                    String palceddate[] = delivery.getRequestPlacedTime().split(" ");
                                    if (date.equals(palceddate[0])) {
                                        if(delivery.getClientPaymentStatus().equals("due")) receivableAmount=receivableAmount+delivery.getProductPrice();
                                        deliveryHistory.add(delivery);
                                    }
                                }
                                binding.tvAmount.setText("Total Receivable "+receivableAmount+" Tk");
                                adapterPayment.notifyDataSetChanged();
                                if(deliveryHistory.isEmpty()){
                                    binding.tvNothingFound.setVisibility(View.VISIBLE);
                                    binding.recycDeliveryHistory.setVisibility(View.GONE);
                                }else {
                                    binding.tvNothingFound.setVisibility(View.GONE);
                                    binding.recycDeliveryHistory.setVisibility(View.VISIBLE);
                                }
                                dialogLoading.dismiss();
                            }
                        } else {
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityPayment.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void init() {
        receivableAmount=0;
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Payment");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dialogLoading = Utils.setupLoadingDialog(ActivityPayment.this);
        binding.datepicker.setFirstVisibleDate(2021, Calendar.JANUARY, 01);
        binding.datepicker.setLastVisibleDate(2021, Calendar.DECEMBER, 31);
        binding.datepicker.setFollowScroll(true);
        String date[] = Utils.getCustentDateArray();
        binding.datepicker.setSelectedDate(Integer.valueOf(date[2]), Integer.valueOf(date[1]) - 1, Integer.valueOf(date[0]));

        viewModelDuarClientApp = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelDuarClientApp.class);

        deliveryHistory = new ArrayList<>();
        adapterPayment = new AdapterPayment(deliveryHistory, ActivityPayment.this, ActivityPayment.this);
        binding.recycDeliveryHistory.setLayoutManager(new LinearLayoutManager(ActivityPayment.this));
        binding.recycDeliveryHistory.setAdapter(adapterPayment);

    }


    @Override
    public void onViewDetailsClicked(ModelDeliveryRequest deliveryRequest) {
        startActivity(new Intent(ActivityPayment.this, ActivityOngoingOrderDetails.class)
                .putExtra(getString(R.string.parcel), deliveryRequest));
    }

    @Override
    public void markAsPaid(ModelDeliveryRequest deliveryRequest) {
        dialogLoading.show();
        deliveryRequest.setClientPaymentStatus("received");
        viewModelDuarClientApp.updateClientPaymentStatus(deliveryRequest).observe(ActivityPayment.this,
                new Observer<ModelResponse>() {
                    @Override
                    public void onChanged(ModelResponse response) {
                        if(response!=null&&response.getResponse()==1){
                            String date = twodigits.format(binding.datepicker.getSelectedDay()) + "-" +
                                    twodigits.format((binding.datepicker.getSelectedMonth() + 1)) + "-" +
                                    twodigits.format(binding.datepicker.getSelectedYear());
                            getDeliveryHistoryFromServer(Utils.getPref(GlobalKey.CLIENT_ID, ""), date);
                        }else {
                            deliveryRequest.setClientPaymentStatus("due");
                            adapterPayment.notifyDataSetChanged();

                            dialogLoading.dismiss();
                            Toast.makeText(ActivityPayment.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}