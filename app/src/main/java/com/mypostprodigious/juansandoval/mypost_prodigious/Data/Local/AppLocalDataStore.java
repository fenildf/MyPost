package com.mypostprodigious.juansandoval.mypost_prodigious.Data.Local;


import android.content.Context;
import android.database.Observable;
import android.support.annotation.NonNull;

import com.mypostprodigious.juansandoval.mypost_prodigious.Data.AppDataStore;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;

import java.util.List;

import javax.inject.Inject;

public class AppLocalDataStore implements AppDataStore {

    private StorIOContentResolver mStorIOContentResolver;

    @Inject
    public AppLocalDataStore(@NonNull Context context) {
    }

    @Override
    public Observable<List<DatabaseContract.Post>> getPost() {
        return null;
    }
}
