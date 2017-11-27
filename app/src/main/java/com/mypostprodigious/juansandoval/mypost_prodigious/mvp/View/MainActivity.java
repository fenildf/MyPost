package com.mypostprodigious.juansandoval.mypost_prodigious.mvp.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mypostprodigious.juansandoval.mypost_prodigious.App;
import com.mypostprodigious.juansandoval.mypost_prodigious.Data.AppRepository;
import com.mypostprodigious.juansandoval.mypost_prodigious.R;
import com.mypostprodigious.juansandoval.mypost_prodigious.Utils.EspressoIdlingResource;
import com.mypostprodigious.juansandoval.mypost_prodigious.Utils.SharedPreference;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.MainScreenContract;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.Post;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Presenter.MainScreenPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends FragmentActivity implements MainScreenContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, View.OnLongClickListener {

    private MainScreenContract.Presenter mPresenter;
    private ListView listView;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private TextView textView;

    @Inject
    AppRepository repository;

    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inject dependency
        App.getAppComponent().inject(this);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.my_list_view);
        textView = (TextView) findViewById(R.id.simple_post_item_text);
        swipeContainer.setOnRefreshListener(this);
        list = new ArrayList<>();

        new MainScreenPresenter(repository, this);

        mPresenter.loadPostFromRemoteDataStore();
    }

    @Override
    public void showPost(List<Post> posts) {
        for (int i = 0; i < posts.size(); i++) {
            list.add(posts.get(i).getTitle());
        }
        //Create the array adapter and set it to list view
        adapter = new ArrayAdapter<>(this, R.layout.simple_post_item, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error loading post", Toast.LENGTH_SHORT).show();
        if (swipeContainer != null)
            swipeContainer.post(new Runnable() {
                @Override
                public void run() {
                    swipeContainer.setRefreshing(false);
                }
            });
    }

    @Override
    public void showComplete() {
        Toast.makeText(this, "Completed loading", Toast.LENGTH_SHORT).show();
        if (swipeContainer != null)
            swipeContainer.post(new Runnable() {
                @Override
                public void run() {
                    swipeContainer.setRefreshing(false);
                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(MainScreenContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh Completed", Toast.LENGTH_SHORT).show();
        mPresenter.loadPostFromRemoteDataStore();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, FavoriteListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(this, "OnLong Clicked", Toast.LENGTH_SHORT).show();
        return true;
    }


    public class PostsListAdapter extends BaseAdapter {

        private Context context;
        ArrayList<Post> postsList;
        SharedPreference sharedPreference;

        public PostsListAdapter(Context context, ArrayList<Post> postsList) {

            this.context = context;
            this.postsList = postsList;
            sharedPreference = new SharedPreference();
        }

        private class ViewHolder {
            TextView txtTitle;
        }

        @Override
        public int getCount() {
            return postsList.size();
        }

        @Override
        public Object getItem(int position) {
            return postsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.simple_post_item, parent, false);
                holder = new ViewHolder();
                holder.txtTitle = (TextView) convertView
                        .findViewById(R.id.simple_post_item_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final Post postList = (Post) getItem(position);
            holder.txtTitle.setText(postList.getTitle());

            holder.txtTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    sharedPreference.addFavorite(context, postsList.get(position));
                }
            });
            return convertView;
        }
    }
}
