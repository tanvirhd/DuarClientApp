package com.duarbd.duarclientapp.presenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.duarbd.duarclientapp.R;
import com.duarbd.duarclientapp.adapter.AdapterOngoingDelivery;
import com.duarbd.duarclientapp.databinding.ActivityHomeBinding;
import com.duarbd.duarclientapp.interfaces.AdapterOngoingDeliveryCallbacks;
import com.duarbd.duarclientapp.model.ModelDeliveryRequest;
import com.duarbd.duarclientapp.model.ModelOngoingDelivery;
import com.duarbd.duarclientapp.network.viewmodel.ViewModelDuarClientApp;
import com.duarbd.duarclientapp.tools.GlobalKey;
import com.duarbd.duarclientapp.tools.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ActivityHome extends AppCompatActivity implements AdapterOngoingDeliveryCallbacks,
        NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "ActivityHome";
    private ActivityHomeBinding binding;
    private Dialog dialogLoading;
    private List<ModelDeliveryRequest> ongoingDeliveryList;
    private AdapterOngoingDelivery adapterOngoingDelivery;
    private ViewModelDuarClientApp viewModelDuarClientApp;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialogLoading=setupDialog(ActivityHome.this);

        setSupportActionBar(binding.toolbar);
        binding.toolbarText.setText(Utils.getPref(GlobalKey.CLIENT_NAME,"Duar Client"));

        initNavDrawer();
        init();

        binding.layoutNoOngoingOrder.requestRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this,ActivityRequestDelivery.class));
            }
        });

        binding.btnRequestRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this,ActivityRequestDelivery.class));
            }
        });

        binding.layoutOngoingOrderPage.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRequestedDeliveryListFromServer(Utils.getPref(GlobalKey.CLIENT_ID,""),"swipeRefresh");
            }
        });
    }

    void initNavDrawer(){
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                binding.drawerlayout, binding.toolbar, R.string.open, R.string.close);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black0, null));
        else actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black0));
        binding.drawerlayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        binding.homeNav.setNavigationItemSelectedListener(this);
    }

    void init(){
        ongoingDeliveryList=new ArrayList<>();
        adapterOngoingDelivery=new AdapterOngoingDelivery(ongoingDeliveryList,this,this);
        binding.layoutOngoingOrderPage.recycOngoingOrder.setAdapter(adapterOngoingDelivery);
        binding.layoutOngoingOrderPage.recycOngoingOrder.setLayoutManager(new LinearLayoutManager(this));

        viewModelDuarClientApp=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelDuarClientApp.class);
    }

    void getRequestedDeliveryListFromServer(String clientId){
        dialogLoading.show();
        viewModelDuarClientApp.getRequestedDeliveryListByClientId(clientId).observe(ActivityHome.this,
                new Observer<List<ModelDeliveryRequest>>() {
                    @Override
                    public void onChanged(List<ModelDeliveryRequest> modelDeliveryRequests) {
                        if(modelDeliveryRequests!=null&&!modelDeliveryRequests.get(0).getResponse().equals("0")){
                            if(!modelDeliveryRequests.get(0).getStatus().equals("NothingFound")){
                                ongoingDeliveryList.clear();
                                for(ModelDeliveryRequest request:modelDeliveryRequests)
                                    if(!request.getStatus().equals("4")) ongoingDeliveryList.add(request);
                                adapterOngoingDelivery.notifyDataSetChanged();
                                dialogLoading.dismiss();
                            }else {
                                ongoingDeliveryList.clear();
                                adapterOngoingDelivery.notifyDataSetChanged();
                                dialogLoading.dismiss();
                                Toast.makeText(ActivityHome.this, "No ongoing request found", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            dialogLoading.dismiss();
                            Toast.makeText(ActivityHome.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void getRequestedDeliveryListFromServer(String clientId,String swipeRefresh){
        viewModelDuarClientApp.getRequestedDeliveryListByClientId(clientId).observe(ActivityHome.this,
                new Observer<List<ModelDeliveryRequest>>() {
                    @Override
                    public void onChanged(List<ModelDeliveryRequest> modelDeliveryRequests) {
                        if(modelDeliveryRequests!=null&&!modelDeliveryRequests.get(0).getResponse().equals("0")){
                            if(!modelDeliveryRequests.get(0).getStatus().equals("NothingFound")){
                                Log.d(TAG, "onChanged: !=0 called");
                                ongoingDeliveryList.clear();
                                for(ModelDeliveryRequest request:modelDeliveryRequests)
                                    if(!request.getStatus().equals("4")) ongoingDeliveryList.add(request);
                                adapterOngoingDelivery.notifyDataSetChanged();
                                binding.layoutOngoingOrderPage.swipeRefresh.setRefreshing(false);
                            }else {
                                ongoingDeliveryList.clear();
                                adapterOngoingDelivery.notifyDataSetChanged();
                                binding.layoutOngoingOrderPage.swipeRefresh.setRefreshing(false);
                                Toast.makeText(ActivityHome.this, "No ongoing request found", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            binding.layoutOngoingOrderPage.swipeRefresh.setRefreshing(false);
                            Toast.makeText(ActivityHome.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onViewDetailsClicked(int position, String deliveryid) {
        startActivity(new Intent(ActivityHome.this,ActivityOngoingOrderDetails.class)
                .putExtra(getString(R.string.parcel),ongoingDeliveryList.get(position)));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.businessProfile:
                Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
                binding.drawerlayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.deliveryHistory:
                startActivity(new Intent(ActivityHome.this,ActivityDeliveryHistory.class));
                break;
            case R.id.payments:
                startActivity(new Intent(ActivityHome.this,ActivityPayment.class));
                break;
            case R.id.myShop:
                startActivity(new Intent(ActivityHome.this,ActivityShop.class));
                break;
            case R.id.logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                binding.drawerlayout.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.drawerlayout.closeDrawer(GravityCompat.START);
        getRequestedDeliveryListFromServer(Utils.getPref(GlobalKey.CLIENT_ID,""));
    }


    private Dialog setupDialog(Activity activity) {
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        //window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);  //this prevents dimming effect
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }
}