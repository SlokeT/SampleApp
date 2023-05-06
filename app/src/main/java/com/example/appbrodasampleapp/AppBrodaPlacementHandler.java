package com.example.appbrodasampleapp;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.Set;

public class AppBrodaPlacementHandler {

    static SharedPreferences sharedPreferences;
    static  FirebaseRemoteConfig mFirebaseRemoteConfig;
    private static String sharedPreferenceKey = "AppBroda_pref";
    private static String abAppKey;
    public static void initRemoteConfigAndSavePlacements(Context context) {
        abAppKey = context.getPackageName().replace(".","_")+"_";

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
               .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                        } else {
                        }
                    }
                });

        sharedPreferences = context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> remoteConfigKeys = mFirebaseRemoteConfig.getKeysByPrefix(abAppKey);



        for(String key : remoteConfigKeys){
            String newKey =  getKey(trimPrefix(key,abAppKey));
            String value = mFirebaseRemoteConfig.getString(key);
            editor.putString(newKey, value);
        }
        editor.apply();
    }

    public static void fetchAndSavePlacements(FirebaseRemoteConfig mFirebaseRemoteConfig,Context context) {
        //abAppKey= context.getResources().getString(R.string.AB_appKey);
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                        } else {
                        }
                    }
                });

        sharedPreferences = context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> remoteConfigKeys = mFirebaseRemoteConfig.getKeysByPrefix(abAppKey);

        for(String key : remoteConfigKeys){
            String newKey =  getKey(trimPrefix(key,abAppKey));
            String value = mFirebaseRemoteConfig.getString(key);
            editor.putString(newKey, value);
        }
        editor.apply();
    }

    public static String[] loadPlacements(String key){
        String newKey =  getKey(key);
        String value =sharedPreferences.getString(newKey, "");

        if(value=="") {
            return new String[]{};
        }
        String[] placement = convertToarray(value);
        return placement==null?new String[] {}:placement;
    }

    public static String[] convertToarray(String value){
        String[] array;
        value = value.substring(1, value.length() - 1);
        array = value.split(",");
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim().replaceAll("^\"|\"$", "");
        }
        return array;
    }

    private static String getKey(String key){
        return abAppKey+key;
    }
    private static String trimPrefix(String key,String abAppKey){
        return String.valueOf(key.startsWith(abAppKey)?key.substring(abAppKey.length()):key);
    }
}