package com.mypostprodigious.juansandoval.mypost_prodigious.mvp;

import java.util.List;

import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.*;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Presenter.BasePresenter;
import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.View.BaseView;

public interface MainScreenContract {
    interface View extends BaseView<Presenter> {

        void showPost(List<Post> posts);

        void showError(String message);

        void showComplete();

    }

    interface Presenter extends BasePresenter {
        void loadPost();

        void loadPostFromRemoteDataStore();

    }
}
