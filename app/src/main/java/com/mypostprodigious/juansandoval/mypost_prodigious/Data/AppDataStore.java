package com.mypostprodigious.juansandoval.mypost_prodigious.Data;


import android.database.Observable;

import com.mypostprodigious.juansandoval.mypost_prodigious.Data.Local.DatabaseContract;

import java.util.List;

public interface AppDataStore {

    Observable<List<DatabaseContract.Post>> getPost();
}
