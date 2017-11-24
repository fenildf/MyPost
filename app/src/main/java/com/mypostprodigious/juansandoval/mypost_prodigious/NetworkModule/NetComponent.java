package com.mypostprodigious.juansandoval.mypost_prodigious.NetworkModule;


import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface NetComponent {
    Retrofit retrofit();
}
