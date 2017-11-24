package com.mypostprodigious.juansandoval.mypost_prodigious.mvp;

import java.util.List;

import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.Model.*;

public interface MainScreenContract {
    interface View {

        void showPost(List<Post> posts);

        void showError(String message);

        void showComplete();
    }

    interface Presenter {
        void loadPost();
    }
}
