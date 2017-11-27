package com.mypostprodigious.juansandoval.mypost_prodigious.Utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    public static final String PREFS_NAME = "MYPOST_APP";
    public static final String FAVORITES = "Favorite";

    public SharedPreference() {
        super();
    }


    public void storeFavorites(Context context, List<Post> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public ArrayList<Post> loadFavorites(Context context) {
        SharedPreferences settings;
        List<Post> favorites;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Post[] favoriteItems = gson.fromJson(jsonFavorites, Post[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Post>(favorites);
        } else
            return null;

        return (ArrayList<Post>) favorites;
    }


    public void addFavorite(Context context, Post post) {
        List<Post> favorites = loadFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Post>();
        favorites.add(post);
        storeFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Post post) {
        ArrayList<Post> favorites = loadFavorites(context);
        if (favorites != null) {
            favorites.remove(post);
            storeFavorites(context, favorites);
        }
    }
}
