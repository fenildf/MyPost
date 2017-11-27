package com.mypostprodigious.juansandoval.mypost_prodigious.mvp.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mypostprodigious.juansandoval.mypost_prodigious.R;
import com.mypostprodigious.juansandoval.mypost_prodigious.Utils.SharedPreference;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.Post;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment {

    private ImageView backArrow;
    private ListView favouriteListView;
    public static FavouritesListAdapter favouritsListAdapter;
    SharedPreference sharedPreference;
    private ArrayList<Post> favouritesPostList;

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference = new SharedPreference();
        try {
            favouritesPostList = sharedPreference.loadFavorites(getActivity());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        favouriteListView = (ListView) view.findViewById(R.id.favourits_list);
        backArrow = (ImageView) view.findViewById(R.id.back_img);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume", "onResume Called");
        if (favouritesPostList != null) {
            try {
                favouritsListAdapter = new FavouritesListAdapter(getActivity(), favouritesPostList);
                favouriteListView.setAdapter(favouritsListAdapter);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            favouritsListAdapter.notifyDataSetChanged();
        }
    }

    public void goBack() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    public class FavouritesListAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflater;
        ArrayList<Post> favouritesPostList;

        public FavouritesListAdapter(Context context, ArrayList<Post> favouritesBeanSampleList) {
            this.context = context;
            this.favouritesPostList = favouritesBeanSampleList;
        }

        public int getCount() {
            return favouritesPostList.size();
        }

        public Object getItem(int position) {
            return favouritesPostList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            TextView txtTitlePost;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.simple_post_item, parent, false);
                holder = new ViewHolder();
                holder.txtTitlePost = (TextView) convertView.findViewById(R.id.simple_post_item_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtTitlePost.setText(favouritesPostList.get(position).getTitle());
            holder.txtTitlePost.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    sharedPreference.removeFavorite(context, favouritesPostList.get(position));
                    favouritesPostList.remove(favouritesPostList.get(position));
                    notifyDataSetChanged();
                    return true;
                }
            });

            return convertView;
        }
    }
}
