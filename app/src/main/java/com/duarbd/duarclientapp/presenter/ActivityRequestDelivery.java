package com.duarbd.duarclientapp.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.duarbd.duarclientapp.R;
import com.duarbd.duarclientapp.databinding.ActivityRequestDeliveryBinding;
import com.duarbd.duarclientapp.model.ModelClientRoom;
import com.duarbd.duarclientapp.model.ModelDeliveryRequest;
import com.duarbd.duarclientapp.model.ModelResponse;
import com.duarbd.duarclientapp.network.viewmodel.ViewModelDuarClientApp;
import com.duarbd.duarclientapp.room.ViewModelRoom;
import com.duarbd.duarclientapp.tools.GlobalKey;
import com.duarbd.duarclientapp.tools.Utils;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

public class ActivityRequestDelivery extends AppCompatActivity {
    private static final String TAG = "ActivityRequestDelivery";
    private ActivityRequestDeliveryBinding binding;
    private ViewModelDuarClientApp viewModelDuarClientApp;
    private ViewModelRoom viewModelRoom;
    private ModelClientRoom client;
    private int selectedPickTime;
    private ModelDeliveryRequest deliveryRequest;
    private Dialog dialogLoading;

    private HashMap<String, Integer> deliveryChargeChart;
    private List<String> areaList;
    private ArrayAdapter<String> adapterDeliveryChargeCart;

    private Observable<String> observableDeliveryArea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestDeliveryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Log.d(TAG, "onCreate: "+Utils.getCustentDateTime24HRFormat()+" pickupcode="+pickCodeGenerator());
        init();
        initObservables();

        binding.layoutRequestDeliveryPage.rgPickupTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb15min:
                        selectedPickTime = 15;
                        break;
                    case R.id.rb20min:
                        selectedPickTime = 20;
                        break;
                    case R.id.rb30min:
                        selectedPickTime = 30;
                        break;
                    case R.id.rb45min:
                        selectedPickTime = 45;
                        break;
                }
            }
        });


        binding.layoutRequestDeliveryPage.btnRequestDelivery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogLoading.show();
                if (checkValidity() && client != null) {
                    deliveryRequest = new ModelDeliveryRequest(
                            generate9DigitDeliveryID(client.getClientBusinessName()),
                            client.getClientid(),
                            client.getClientBusinessName(),
                            binding.layoutRequestDeliveryPage.etCustomerName.getText().toString(),
                            binding.layoutRequestDeliveryPage.etCustomerNumber.getText().toString(),
                            binding.layoutRequestDeliveryPage.etProductType.getText().toString(),
                            binding.layoutRequestDeliveryPage.deliveryArea.getText().toString(),
                            binding.layoutRequestDeliveryPage.etDeliveryAddress.getText().toString(),
                            client.getClientAddress(),
                            client.getClientBusinessLocationlat(),
                            client.getClientBusinessLocationlang(),
                            Utils.getPref(GlobalKey.FCM_TOKEN,""),
                            client.getPickupCharge(),
                            deliveryChargeChart.get(binding.layoutRequestDeliveryPage.deliveryArea.getText().toString()),
                            Integer.valueOf(binding.layoutRequestDeliveryPage.etProductPrice.getText().toString()),
                            selectedPickTime,
                            Utils.getCustentDateTime24HRFormat(),
                            pickCodeGenerator()
                    );
                    sendDeliveryRequest(deliveryRequest);
                } else {
                    dialogLoading.dismiss();
                    Toast.makeText(ActivityRequestDelivery.this, "Fill-up all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }//end of onCreate

    private boolean checkValidity() {
        if (binding.layoutRequestDeliveryPage.etCustomerName.getText().toString().equals("") ||
                binding.layoutRequestDeliveryPage.etCustomerNumber.getText().toString().equals("") ||
                binding.layoutRequestDeliveryPage.etCustomerNumber.getText().toString().length() != 11 ||
                binding.layoutRequestDeliveryPage.etProductType.getText().toString().equals("") ||
                binding.layoutRequestDeliveryPage.deliveryArea.getText().toString().equals("") ||
                binding.layoutRequestDeliveryPage.etProductPrice.getText().toString().equals(""))
            return false;
        else return true;
    }

    private void sendDeliveryRequest(ModelDeliveryRequest deliveryRequest){
        viewModelDuarClientApp.sendDeliveryRequest(deliveryRequest).observe(ActivityRequestDelivery.this,
                new Observer<ModelResponse>() {
                    @Override
                    public void onChanged(ModelResponse modelResponse) {
                        if(modelResponse!=null&&modelResponse.getResponse()==1){
                            onBackPressed();
                            Toast.makeText(ActivityRequestDelivery.this, "request sent successfully", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(ActivityRequestDelivery.this, "Something went Wrong!! Try again", Toast.LENGTH_SHORT).show();
                        dialogLoading.dismiss();
                    }
                });
    }


    private void init() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("New Delivery Request");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dialogLoading = setupDialog(ActivityRequestDelivery.this);
        selectedPickTime = 0;
        binding.layoutRequestDeliveryPage.rb15min.setChecked(true);

        deliveryChargeChart = new HashMap<>();
        areaList = new ArrayList<>();
        initDeliveryChageCart();
        areaList.clear();
        areaList.addAll(deliveryChargeChart.keySet());
        adapterDeliveryChargeCart = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, areaList);
        binding.layoutRequestDeliveryPage.deliveryArea.setAdapter(adapterDeliveryChargeCart);

        viewModelRoom = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelRoom.class);
        viewModelDuarClientApp = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelDuarClientApp.class);
        getClientInfo(Utils.getPref(GlobalKey.CLIENT_ID, ""));
    }

    private void initDeliveryChageCart() {
        deliveryChargeChart.put("Kolony", 30);
        deliveryChargeChart.put("Hakirmor", 30);
        deliveryChargeChart.put("Bonani", 50);
        deliveryChargeChart.put("BadurTola", 30);
    }

    private void initObservables() {
        observableDeliveryArea = RxTextView.textChanges(binding.layoutRequestDeliveryPage.deliveryArea).skip(1).map(new Function<CharSequence, String>() {
            @Override
            public String apply(CharSequence charSequence) throws Exception {
                return charSequence.toString();
            }
        });

        observableDeliveryArea.subscribe(new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
                binding.layoutRequestDeliveryPage.tvDeliveryCharge
                        .setText(deliveryChargeChart.get(s) + " Tk");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void getClientInfo(String clientID) {
        dialogLoading.show();
        viewModelRoom.getClientInfo(clientID).observe(ActivityRequestDelivery.this,
                new Observer<ModelClientRoom>() {
                    @Override
                    public void onChanged(ModelClientRoom modelClientRoom) {
                        if (modelClientRoom != null) {
                            updateUI(modelClientRoom);
                            client = modelClientRoom;
                        } else {
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityRequestDelivery.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void updateUI(ModelClientRoom clientInfo) {
        binding.layoutRequestDeliveryPage.etProductType.setText(clientInfo.getClientProductType());
        binding.layoutRequestDeliveryPage.tvPickUpCharge.setText(clientInfo.getPickupCharge() == 0 ? "Not Applicable" :
                clientInfo.getPickupCharge() + " Tk");
        dialogLoading.dismiss();
    }

    private Dialog setupDialog(Activity activity) {
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        //window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);  //this prevents dimming effect
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    private String generate9DigitDeliveryID(String leadingPart) {
        Random random = new Random();
        int min=000000,max=999999;
        return leadingPart.substring(0,2).replaceAll("\\s+","")+random.nextInt(max-min) + min;

    }

    private String pickCodeGenerator(){
        return UUID.randomUUID().toString().substring(0,5);
    }

}