package com.mypostprodigious.juansandoval.mypost_prodigious.Utils;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class FragmentView<T extends Fragment> {

    private WeakReference<T> fragmentRef;

    protected FragmentView(T fragment) {
        fragmentRef = new WeakReference<>(fragment);
    }

    @Nullable
    public T getFragment() {
        return fragmentRef.get();
    }

    @Nullable
    public BaseActivity getActivity() {
        return (BaseActivity) fragmentRef.get().getActivity();
    }

    @Nullable
    public Context getContext() {
        return getActivity();
    }

    public void showMessage(@StringRes int message) {
        final Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(String message) {
        final Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public void hideKeyboard() {
        final BaseActivity activity = getActivity();
        if (activity == null || activity.getCurrentFocus() == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showKeyboard() {
        final BaseActivity activity = getActivity();
        if (activity == null || activity.getCurrentFocus() == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }
}
