package com.mypostprodigious.juansandoval.mypost_prodigious.mvp.View;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mypostprodigious.juansandoval.mypost_prodigious.R;

public class FavoriteListActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_post);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        FavoriteFragment fragmentFavourite = FavoriteFragment.newInstance();

        if (manager.findFragmentByTag("fragment_fav") == null) {
            ft.replace(R.id.coordinator_container, fragmentFavourite, "fragment_fav").commit();
        }
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }

}
