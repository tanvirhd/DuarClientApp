package com.duarbd.duarclientapp.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.duarbd.duarclientapp.R;
import com.duarbd.duarclientapp.databinding.ActivityBusinessProfileBinding;
import com.duarbd.duarclientapp.model.ModelClient;
import com.duarbd.duarclientapp.model.ModelClientRoom;
import com.duarbd.duarclientapp.model.ModelResponse;
import com.duarbd.duarclientapp.network.viewmodel.ViewModelDuarClientApp;
import com.duarbd.duarclientapp.room.ViewModelRoom;
import com.duarbd.duarclientapp.tools.GlobalKey;
import com.duarbd.duarclientapp.tools.Utils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ActivityBusinessProfile extends AppCompatActivity {
    private static final String TAG = "ActivityBusinessProfile";
    private ActivityBusinessProfileBinding binding;

    private ViewModelRoom viewModelRoom;
    private ViewModelDuarClientApp viewModelDuarClientApp;
    private Dialog dialogLoading;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBusinessProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        binding.btnShowUpdatePassWordBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        binding.updatePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.newPass.getText().toString().equals(binding.retypedPass.getText().toString())){
                    updateClientPassword(binding.newPass.getText().toString());
                }else {
                    Toast.makeText(ActivityBusinessProfile.this, "Password didn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    void init(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bottomSheetBehavior=BottomSheetBehavior.from(binding.bottomsheet);

        if(Utils.getPrefBoolean(GlobalKey.IS_PASSWORD_UPDATED,false)){
            binding.group.setVisibility(View.GONE);
        }else  binding.group.setVisibility(View.VISIBLE);

        dialogLoading= Utils.setupLoadingDialog(ActivityBusinessProfile.this);
        viewModelRoom=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelRoom.class);
        viewModelDuarClientApp=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelDuarClientApp.class);

    }

    private void getClientInfo(String clientID) {
        dialogLoading.show();
        viewModelRoom.getClientInfo(clientID).observe(ActivityBusinessProfile.this,
                new Observer<ModelClientRoom>() {
                    @Override
                    public void onChanged(ModelClientRoom modelClientRoom) {
                        if (modelClientRoom != null) {
                            updateUI(modelClientRoom);
                            dialogLoading.dismiss();
                        } else {
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityBusinessProfile.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void updateUI(ModelClientRoom client){
        binding.tvBusinessInitial.setText(client.getClientBusinessName().charAt(0)+"");
        binding.tvBusinessName.setText(client.getClientBusinessName());
        binding.tvProductType.setText(client.getClientProductType());
        binding.tvContactNumber.setText(client.getClientContactNumber());
        binding.tvBusinessAddress.setText(client.getClientAddress());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getClientInfo(Utils.getPref(GlobalKey.CLIENT_ID,""));
    }

    void updateClientPassword(String updatedPassword){
        dialogLoading.show();
        ModelClient client=new ModelClient(Utils.getPref(GlobalKey.CLIENT_ID,""),updatedPassword);
        viewModelDuarClientApp.updateClientPassword(client).observe(ActivityBusinessProfile.this,
                new Observer<ModelResponse>() {
                    @Override
                    public void onChanged(ModelResponse response) {
                        if(response!=null&&response.getResponse()==1){
                            binding.group.setVisibility(View.GONE);
                            Utils.savePrefBoolean(GlobalKey.IS_PASSWORD_UPDATED,true);
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityBusinessProfile.this, "Password Updated.", Toast.LENGTH_SHORT).show();
                        }else {
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityBusinessProfile.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}