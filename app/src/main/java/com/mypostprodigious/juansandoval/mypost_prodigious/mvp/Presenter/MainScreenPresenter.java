package com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Presenter;


import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.MainScreenContract;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.Post;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class MainScreenPresenter implements MainScreenContract.Presenter {

    public Retrofit retrofit;
    MainScreenContract.View mView;

    @Inject
    public MainScreenPresenter(Retrofit retrofit, MainScreenContract.View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }

    @Override
    public void loadPost() {
        retrofit.create(PostService.class).getPostList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Post>>() {

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.showComplete();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        mView.showPost(posts);
                    }
                });
    }

    public interface PostService {
        @GET("/posts")
        Observable<List<Post>> getPostList();
    }
}
