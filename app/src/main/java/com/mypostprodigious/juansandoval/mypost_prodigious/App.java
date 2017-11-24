package com.mypostprodigious.juansandoval.mypost_prodigious;


import android.app.Application;

import com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule.AppModule;
import com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule.DaggerNetComponent;
import com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule.NetComponent;
import com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule.NetworkModule;

public class App extends Application {
    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule("http://jsonplaceholder.typicode.com/"))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
