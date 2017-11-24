package com.mypostprodigious.juansandoval.mypost_prodigious.mvp;


import dagger.Provides;

public class MainScreenModule {
    private final MainScreenContract.View mView;

    public MainScreenModule(MainScreenContract.View mView) {
        this.mView = mView;
    }
}
