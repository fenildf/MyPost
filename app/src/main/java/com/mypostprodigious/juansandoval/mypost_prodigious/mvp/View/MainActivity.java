package com.mypostprodigious.juansandoval.mypost_prodigious.mvp.View;

import android.os.Handler;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mypostprodigious.juansandoval.mypost_prodigious.App;
import com.mypostprodigious.juansandoval.mypost_prodigious.R;
import com.mypostprodigious.juansandoval.mypost_prodigious.Utils.Constants;
import com.mypostprodigious.juansandoval.mypost_prodigious.Utils.EspressoIdlingResource;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.DaggerMainScreenComponent;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.MainScreenContract;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.MainScreenModule;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.Post;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Presenter.MainScreenPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainScreenContract.View {

    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Constants constants;

    @Inject MainScreenPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        constants = new Constants();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.my_list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.simple_post_item, list);
                        listView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, constants.REFRESH_DELAY);
            }
        });
        list = new ArrayList<>();
        DaggerMainScreenComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .mainScreenModule(new MainScreenModule(this))
                .build().inject(this);


        //Increment the counter before making a network request
        EspressoIdlingResource.increment();

        //Call the method in MainPresenter to make Network Request
        mainPresenter.loadPost();
    }

    @Override
    public void showPost(List<Post> posts) {
        //Loop through the posts and get the title of the post and add it to our list object
        for (int i = 0; i < posts.size(); i++) {
            list.add(posts.get(i).getTitle());
        }
        //Create the array adapter and set it to list view
        adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.simple_post_item, list);
        listView.setAdapter(adapter);
        //Decrement after loading the posts
        EspressoIdlingResource.decrement();
    }

    @Override
    public void showError(String message) {
        //Show error message Toast
        Toast.makeText(getApplicationContext(), "Error" + message, Toast.LENGTH_SHORT).show();

        // If there is no network connection we get an error and decrement the counter because the call has finished
        EspressoIdlingResource.decrement();
    }

    @Override
    public void showComplete() {
        Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_SHORT).show();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
