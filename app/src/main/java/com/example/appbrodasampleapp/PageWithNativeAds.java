package com.example.appbrodasampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MediaAspectRatio;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

public class PageWithNativeAds extends AppCompatActivity {
    private AdLoader adLoader;
    private String[] placement;
    private int nativeIndex = 0;

    private boolean adFound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_with_native_ads);

        placement = AppBrodaPlacementHandler.loadPlacements("nativeAds");

        loadNativeAds(placement);
    }

    public void loadNativeAds(String[] placement){
        if(placement==null || placement.length==0) //wapper logic to handle errors
            return;
        NativeAdOptions adOptions =
                new NativeAdOptions.Builder().setMediaAspectRatio(MediaAspectRatio.PORTRAIT).build();
        adLoader = new AdLoader.Builder(this, placement[nativeIndex]).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        Log.d("TAG","Native ad loaded");

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(Color.parseColor("#636161"))).build();
                        TemplateView template = findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);

                        // Show the ad.
                        if(adLoader.isLoading()){
                            Toast.makeText(PageWithNativeAds.this,"Native ads loading @index: "+nativeIndex,Toast.LENGTH_SHORT).show();
                        } else {

                        }
                        if (isDestroyed()) {
                            nativeAd.destroy();
                            return;
                        }
                    }
                }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Toast.makeText(PageWithNativeAds.this,"Native ad loading failed @index: "+nativeIndex,Toast.LENGTH_SHORT).show();
                loadNextAd();
            }
        }).withNativeAdOptions(adOptions
                )
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private  void loadNextAd() { //triggers next ad load
        if (nativeIndex == placement.length) {
            nativeIndex = 0;
            return;
        }
        nativeIndex++;
        loadNativeAds(placement);
    }
}