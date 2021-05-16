package com.duarbd.duarclientapp.presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.duarbd.duarclientapp.R;
import com.duarbd.duarclientapp.databinding.ActivityBusinessProfileBinding;

public class ActivityBusinessProfile extends AppCompatActivity {
    private static final String TAG = "ActivityBusinessProfile";
    private ActivityBusinessProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBusinessProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
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
    }
}