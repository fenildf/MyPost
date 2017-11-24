package com.mypostprodigious.juansandoval.mypost_prodigious.mvp.View;

import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mypostprodigious.juansandoval.mypost_prodigious.App;
import com.mypostprodigious.juansandoval.mypost_prodigious.Data.AppRepository;
import com.mypostprodigious.juansandoval.mypost_prodigious.R;
import com.mypostprodigious.juansandoval.mypost_prodigious.Utils.EspressoIdlingResource;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.MainScreenContract;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.Post;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Presenter.MainScreenPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainScreenContract.View, SwipeRefreshLayout.OnRefreshListener {

    private MainScreenContract.Presenter mPresenter;
    private ListView listView;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;

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
        swipeContainer.setOnRefreshListener(this);
        list = new ArrayList<>();

        new MainScreenPresenter(repository, this);

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
        mPresenter.loadPostFromRemoteDatatore();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
