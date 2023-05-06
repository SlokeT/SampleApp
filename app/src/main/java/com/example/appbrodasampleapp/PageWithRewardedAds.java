package com.example.appbrodasampleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class PageWithRewardedAds extends AppCompatActivity {
    private RewardedAd rewardedAd;
    private String placement[];
    private int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_with_rewarded_ads);

        Intent i =getIntent();
        placement = AppBrodaPlacementHandler.loadPlacements("rewardedAds");
        AdRequest adRequest = new AdRequest.Builder().build();
        loadRewardedAd(placement);
    }

    public void loadRewardedAd(String[] placement){
        if(placement==null || placement.length==0) //wapper logic to handle errors
            return;
        RewardedAd.load(this, placement[index],
                new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        rewardedAd = null;
                        Toast.makeText(PageWithRewardedAds.this, "Rewared Ad failed to load @index: "+index,Toast.LENGTH_SHORT).show();
                        loadNextAd();
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        Toast.makeText(PageWithRewardedAds.this, "Rewared Ad loaded @index: "+index,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void showAd(View v){
        if (rewardedAd != null) {
            Activity activityContext = PageWithRewardedAds.this;
            rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d("TAG", "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                }

            });
        } else {
            Log.d("TAG", "The rewarded ad wasn't ready yet.");
        }
    }

    private  void loadNextAd() { //triggers next ad load
        if (index == placement.length) {
            index = 0;
            return;
        }
        index++;
        loadRewardedAd(placement);
    }
}