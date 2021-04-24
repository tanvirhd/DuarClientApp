package com.duarbd.duarclientapp.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;

import android.os.Bundle;
import android.view.View;

import com.duarbd.duarclientapp.R;
import com.duarbd.duarclientapp.databinding.ActivityShopBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ActivityShop extends AppCompatActivity {
    private static final String TAG = "ActivityShop";
    private ActivityShopBinding binding;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        init();

        binding.addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        binding.layoutAddBottomsheet.ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    void init(){
        bottomSheetBehavior=BottomSheetBehavior.from(binding.layoutAddBottomsheet.layoutAddBottomsheet);
    }
}