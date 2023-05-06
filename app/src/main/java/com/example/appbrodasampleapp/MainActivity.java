package com.example.appbrodasampleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AppBrodaPlacementHandler.initRemoteConfigAndSavePlacements(MainActivity.this);
    }

    public void bannerPage(View v){
        Intent i = new Intent(this, PageWithBannerAds.class);
        startActivity(i);
    }

    public void interstitialPage(View v){
        Intent i = new Intent(this, PageWithInterstitialAds.class);
        startActivity(i);
    }

    public void rewardedPage(View v){
        Intent i = new Intent(this, PageWithRewardedAds.class);
        startActivity(i);
    }

    public void nativePage(View v){
        Intent i = new Intent(this, PageWithNativeAds.class);
        startActivity(i);
    }
}