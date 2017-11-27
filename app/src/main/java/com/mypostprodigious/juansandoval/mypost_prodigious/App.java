package com.mypostprodigious.juansandoval.mypost_prodigious;


import android.app.Application;

import com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule.AppModule;
import com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule.AppComponent;
import com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule.DaggerAppComponent;
import com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule.NetworkModule;
import com.mypostprodigious.juansandoval.mypost_prodigious.Utils.Constants;

public class App extends Application {
    private static AppComponent mAppComponent;
    Constants constants;

    @Override
    public void onCreate() {
        constants = new Constants();
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(constants.BASE_URL))
                .build();
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}
