package com.mypostprodigious.juansandoval.mypost_prodigious;


import android.app.Application;

import com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule.AppModule;
import com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule.DaggerNetComponent;
import com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule.NetComponent;
import com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule.NetworkModule;
import com.mypostprodigious.juansandoval.mypost_prodigious.Utils.Constants;

public class App extends Application {
    private NetComponent mNetComponent;
    Constants constants;

    @Override
    public void onCreate() {
        constants = new Constants();
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(constants.BASE_URL))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
