package com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule;


import android.app.Application;

import javax.inject.Singleton;

import dagger.Provides;

public class AppModule {

    Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }
}