package com.duarbd.duarclientapp.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.duarbd.duarclientapp.R;
import com.duarbd.duarclientapp.databinding.ActivityLoginBinding;
import com.duarbd.duarclientapp.model.ModelClient;
import com.duarbd.duarclientapp.model.ModelResponse;
import com.duarbd.duarclientapp.model.ModelToken;
import com.duarbd.duarclientapp.network.viewmodel.ViewModelDuarClientApp;
import com.duarbd.duarclientapp.room.ViewModelRoom;
import com.duarbd.duarclientapp.tools.GlobalKey;
import com.duarbd.duarclientapp.tools.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

public class ActivityLogin extends AppCompatActivity {
    private static final String TAG = "ActivityLogin";
    private ActivityLoginBinding binding;

    Observable<String> phoneObservable;
    Observable<String> passwordObservable;
    Observable<Boolean> observable;

    private ViewModelDuarClientApp viewModelDuarClientApp;
    private ViewModelRoom viewModelRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        initObservables();


        binding.btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(binding.phoneNumber.getText().toString(),binding.password.getText().toString());
            }
        });


    }//end of onCreate


    void init (){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Client Login");
        viewModelDuarClientApp=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelDuarClientApp.class);
        viewModelRoom=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelRoom.class);
    }
    void initObservables(){
        phoneObservable = RxTextView.textChanges(binding.phoneNumber).skip(1).map(new Function<CharSequence, String>() {
            @Override
            public String apply(CharSequence charSequence) throws Exception {
                return charSequence.toString();
            }
        });

        passwordObservable = RxTextView.textChanges(binding.password).skip(1).map(new Function<CharSequence, String>() {
            @Override
            public String apply(CharSequence charSequence) throws Exception {
                return charSequence.toString();
            }
        });

        observable=observable.combineLatest(phoneObservable, passwordObservable, new BiFunction<String, String, Boolean>() {
            @Override
            public Boolean apply(String s, String s2) throws Exception {
                return isValid(s,s2);
            }
        });

        observable.subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                updateSignnButtor(aBoolean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        phoneObservable.subscribe(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                isValidNumber(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void isValidNumber(String s) {
        if(s.equals("") ){
            binding.outlinedTextField1.setHelperText("Can't be empty.");
        }else if(s.length()!=11) {
            binding.outlinedTextField1.setHelperText("Invalid phone number.");
        }else {
            binding.outlinedTextField1.setHelperText("");
        }
    }

    private Boolean isValid(String s1,String s2){
        if(s1.equals("") || s2.equals("") || s1.length()!=11){
            return false;
        }else {
            return true;
        }
    }

    private void updateSignnButtor(Boolean b){
        if(b){
            binding.btnSignin.setEnabled(b);
        }else {
            binding.btnSignin.setEnabled(b);
        }
    }

    void login(String phonenumber,String password){
        //todo show waiting dialog
        ModelClient client=new ModelClient(phonenumber,password);
        viewModelDuarClientApp.clientLogin(client).observe(this, new Observer<ModelResponse>() {
            @Override
            public void onChanged(ModelResponse modelResponse) {
                if(modelResponse!=null && modelResponse.getResponse()==1) {
                    Utils.savePrefBoolean(GlobalKey.IS_LOGGED_IN,true);
                    Utils.savePref(GlobalKey.CLIENT_ID,client.getClientid());
                    Utils.savePref(GlobalKey.CLIENT_NAME,modelResponse.getClientBusinessName());
                    storeFCMToken(client.getClientid());
                    saveClientInf(modelResponse);//saving client info into local DB
                }
                else Toast.makeText(ActivityLogin.this, "Wrong Credential", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void saveClientInf(ModelResponse response){
        viewModelRoom.saveClientInfo(response);
    }
    private void storeFCMToken(String clientid) {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                Utils.savePref(GlobalKey.FCM_TOKEN,s);
                viewModelDuarClientApp.storeToken2(new ModelToken(clientid,"client",s))
                           .observe(ActivityLogin.this, new Observer<ModelResponse>() {
                               @Override
                               public void onChanged(ModelResponse modelResponse) {
                                   if(modelResponse!=null && modelResponse.getResponse()==1) {
                                       Utils.savePrefBoolean(GlobalKey.IS_TOKEN_AVAILABLE,true);
                                       startActivity(new Intent(ActivityLogin.this,ActivityHome.class));finish();
                                   }
                                   else Log.d(TAG, "onChanged: storing token failed");
                               }
                           });
                //viewModelDuarClientApp.storeFCMToken(new ModelToken(clientid,"client",s));
            }
        });
    }
}