package com.example.appbrodasampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;


public class PageWithBannerAds extends AppCompatActivity {
    int index=0;
    String[] adUnits = AppBrodaPlacementHandler.loadPlacements("bannerAds");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_with_banner_ads);

        loadBannerAd(adUnits);
    }

    private void loadBannerAd(String[] adUnits) {
        if(adUnits==null || adUnits.length==0) return; //wrapper logic to handle errors

        AdView adview = new AdView(this);

        String adUnitId = adUnits[index];
        adview.setAdUnitId(adUnitId);
        adview.setAdSize(AdSize.BANNER);
        adview.loadAd(new AdRequest.Builder()
                .build());

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.addView(adview, params);

        setContentView(layout);
        adview.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Toast.makeText(PageWithBannerAds.this, "Banner ad loading failed @index :"+index, Toast.LENGTH_SHORT).show();
                loadNextAd(); //call to triggers next load
            }
            @Override
            public void onAdLoaded() {
                Toast.makeText(PageWithBannerAds.this, "Banner ad loaded @index :"+index, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void loadNextAd() { //triggers next ad load
        if (index == adUnits.length) {
            index = 0;
            return;
        }
        index++;
        loadBannerAd(adUnits);
    }
}
