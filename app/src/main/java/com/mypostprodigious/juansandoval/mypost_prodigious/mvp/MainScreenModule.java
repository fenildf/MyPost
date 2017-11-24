package com.mypostprodigious.juansandoval.mypost_prodigious.mvp;


import com.mypostprodigious.juansandoval.mypost_prodigious.Utils.CustomScope;

import dagger.Module;
import dagger.Provides;

@Module
public class MainScreenModule {
    private final MainScreenContract.View mView;

    public MainScreenModule(MainScreenContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    MainScreenContract.View providesMainScreenContractView() {
        return mView;
    }
}
