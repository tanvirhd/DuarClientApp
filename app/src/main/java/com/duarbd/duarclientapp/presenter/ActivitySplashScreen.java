package com.duarbd.duarclientapp.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.duarbd.duarclientapp.databinding.ActivitySplashScreenBinding;
import com.duarbd.duarclientapp.model.ModelResponse;
import com.duarbd.duarclientapp.model.ModelToken;
import com.duarbd.duarclientapp.network.viewmodel.ViewModelDuarClientApp;
import com.duarbd.duarclientapp.tools.GlobalKey;
import com.duarbd.duarclientapp.tools.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class ActivitySplashScreen extends AppCompatActivity {
    private static final String TAG = "ActivitySplashScreen";
    private ViewModelDuarClientApp viewModelDuarClientApp;
    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding=ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModelDuarClientApp=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelDuarClientApp.class);

        //todo deprecated
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Utils.getPrefBoolean(GlobalKey.IS_LOGGED_IN,false)) {
                    updateFCMToken();

                } else {
                    startActivity(new Intent(ActivitySplashScreen.this,ActivityLogin.class));finish();
                }
            }
        }, 1000);
    }

    private void updateFCMToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                Utils.savePref(GlobalKey.FCM_TOKEN,s);
                viewModelDuarClientApp.updateToken2(
                        new ModelToken(Utils.getPref(GlobalKey.CLIENT_ID,""),s))
                        .observe(ActivitySplashScreen.this, new Observer<ModelResponse>() {
                            @Override
                            public void onChanged(ModelResponse modelResponse) {
                                if(modelResponse!=null && modelResponse.getResponse()==1) {
                                    Utils.savePrefBoolean(GlobalKey.IS_TOKEN_AVAILABLE,true);
                                    Log.d(TAG, "onChanged: token updated successfully");
                                }
                                else Log.d(TAG, "onChanged: token update failed");
                                startActivity(new Intent(ActivitySplashScreen.this,ActivityHome.class));finish();
                            }
                        });
                //viewModelDuarClientApp.updateFCMToken(new ModelToken(Utils.getPref(GlobalKey.CLIENT_ID,""),s));
            }
        });
    }
}