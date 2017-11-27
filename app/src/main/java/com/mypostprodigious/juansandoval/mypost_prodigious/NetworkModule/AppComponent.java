package com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule;


import com.mypostprodigious.juansandoval.mypost_prodigious.Data.Remote.AppRemoteDataStore;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.View.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(MainActivity activity);

    void inject(AppRemoteDataStore appRemoteDataStore);
}
