package com.example.MobileAppDevelopmentEXE1;

import android.app.Application;

import com.example.MobileAppDevelopmentEXE1.Utilities.SharePreferencesManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharePreferencesManager.init(this);
    }
}