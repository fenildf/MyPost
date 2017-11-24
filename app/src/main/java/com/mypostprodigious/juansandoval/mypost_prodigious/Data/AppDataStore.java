package com.mypostprodigious.juansandoval.mypost_prodigious.Data;

import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.Post;

import java.util.List;

import rx.Observable;


public interface AppDataStore {

    Observable<List<Post>> getPost();
}
