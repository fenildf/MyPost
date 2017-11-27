package com.mypostprodigious.juansandoval.mypost_prodigious.Data;


import com.mypostprodigious.juansandoval.mypost_prodigious.Data.Local.AppLocalDataStore;
import com.mypostprodigious.juansandoval.mypost_prodigious.Data.Remote.AppRemoteDataStore;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.Post;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class AppRepository implements AppDataStore {

    private AppLocalDataStore mAppLocalDataStore;
    private AppRemoteDataStore mAppRemoteDataStore;


    @Inject
    public AppRepository(AppLocalDataStore mAppLocalDataStore, AppRemoteDataStore mAppRemoteDataStore) {
        this.mAppLocalDataStore = mAppLocalDataStore;
        this.mAppRemoteDataStore = mAppRemoteDataStore;
    }

    @Override
    public Observable<List<Post>> getPost() {
        return Observable.concat(mAppLocalDataStore.getPost(), mAppRemoteDataStore.getPost())
                .first(new Func1<List<Post>, Boolean>() {
                    @Override
                    public Boolean call(List<Post> posts) {
                        //Commeted this log due Arrayindexoutofbound exception at first lauch of application
//                        Log.d("Repo", posts.get(0).getTitle());
                        return posts != null;
                    }
                });
    }
}
