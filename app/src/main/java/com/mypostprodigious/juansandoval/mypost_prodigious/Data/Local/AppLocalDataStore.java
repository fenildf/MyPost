package com.mypostprodigious.juansandoval.mypost_prodigious.Data.Local;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mypostprodigious.juansandoval.mypost_prodigious.Data.AppDataStore;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.Post;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.PostStorIOContentResolverDeleteResolver;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.PostStorIOContentResolverGetResolver;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.PostStorIOContentResolverPutResolver;
import com.pushtorefresh.storio.contentresolver.ContentResolverTypeMapping;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.impl.DefaultStorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.queries.Query;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class AppLocalDataStore implements AppDataStore {

    private StorIOContentResolver mStorIOContentResolver;

    @Inject
    public AppLocalDataStore(@NonNull Context context) {
        mStorIOContentResolver = DefaultStorIOContentResolver.builder()
                .contentResolver(context.getContentResolver())
                .addTypeMapping(Post.class, ContentResolverTypeMapping.<Post>builder()
                        .putResolver(new PostStorIOContentResolverPutResolver())
                        .getResolver(new PostStorIOContentResolverGetResolver())
                        .deleteResolver(new PostStorIOContentResolverDeleteResolver())
                        .build()
                ).build();
    }


    @Override
    public Observable<List<Post>> getPost() {
        Log.d("LOCAL", "Loaded from local");
        return mStorIOContentResolver.get()
                .listOfObjects(Post.class)
                .withQuery(Query.builder().uri(DatabaseContract.Post.CONTENT_URI).build())
                .prepare()
                .asRxObservable();
    }

    public void savePostToDatabase(List<Post> posts) {
        mStorIOContentResolver.put().objects(posts).prepare().executeAsBlocking();
    }
}