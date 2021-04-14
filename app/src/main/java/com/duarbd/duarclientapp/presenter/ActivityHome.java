package com.duarbd.duarclientapp.presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.duarbd.duarclientapp.R;
import com.duarbd.duarclientapp.databinding.ActivityHomeBinding;

public class ActivityHome extends AppCompatActivity {
    private static final String TAG = "ActivityHome";
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.layoutNoOngoingOrder.requestRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this,ActivityRequestDelivery.class));
            }
        });
    }
}