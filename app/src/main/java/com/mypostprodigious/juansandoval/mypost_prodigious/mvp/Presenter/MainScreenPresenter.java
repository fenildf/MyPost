package com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Presenter;

import android.util.Log;


import com.mypostprodigious.juansandoval.mypost_prodigious.Data.AppRepository;
import com.mypostprodigious.juansandoval.mypost_prodigious.Data.Remote.AppRemoteDataStore;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.MainScreenContract;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.Post;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Aditya on 23-Oct-16.
 */

public class MainScreenPresenter implements MainScreenContract.Presenter {

    private static final String TAG = MainScreenPresenter.class.getSimpleName();
    private Subscription mSubscription;
    private AppRepository mAppRepository;
    private MainScreenContract.View mView;

    public MainScreenPresenter(AppRepository mAppRepository, MainScreenContract.View mView) {
        this.mAppRepository = mAppRepository;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void loadPost() {
        mSubscription = mAppRepository.getPost()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Complete");
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        mView.showError(e.toString());
                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        mView.showPost(posts);
                    }
                });
    }

    @Override
    public void loadPostFromRemoteDatatore() {
        new AppRemoteDataStore().getPost().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Complete");
                        mView.showComplete();
                        loadPost();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        mView.showError(e.toString());
                    }

                    @Override
                    public void onNext(List<Post> posts) {

                    }
                });
    }

    @Override
    public void subscribe() {
        loadPost();
    }

    @Override
    public void unsubscribe() {
        //Unsubscribe Rx subscription
        if (mSubscription != null && mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }
}
