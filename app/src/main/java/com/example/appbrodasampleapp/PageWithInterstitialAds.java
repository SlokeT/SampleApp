package com.example.appbrodasampleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class PageWithInterstitialAds extends AppCompatActivity {
    InterstitialAd mInterstitialAd;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_with_interstitial_ads);

        Intent i= getIntent();

        AdRequest adRequest = new AdRequest.Builder().build();

        String[] placement = AppBrodaPlacementHandler.loadPlacements("interstitialAds");

        //if(adUnits!=null &&  adUnits.length!=0) {
            LoadAd(adRequest,placement);
        //} else {
        //}

        Button showAdButton = findViewById(R.id.showInterstitialAd);
        showAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(PageWithInterstitialAds.this);
                } else {
                }
            }
        });
    }


    public void LoadAd(AdRequest adRequest, String[] placement){
        if(placement==null || placement.length==0) //wapper logic to handle errors
            return;
        InterstitialAd.load(this,placement[index], adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        Toast.makeText(PageWithInterstitialAds.this, "Interstitial Ad Loaded @index: "+index,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Toast.makeText(PageWithInterstitialAds.this, "Interstitial Ad Loading failed @index: "+index,Toast.LENGTH_SHORT).show();
                        mInterstitialAd = null;
                        loadNext(adRequest,placement);
                    }
                });
    }

    public void loadNext(AdRequest adRequest, String[] adUnits){
        if(index==adUnits.length){
            index=0;
            return;
        }
        index++;
        LoadAd(adRequest, adUnits);
    }
}