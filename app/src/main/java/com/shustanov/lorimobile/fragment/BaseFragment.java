package com.shustanov.lorimobile.fragment;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.activity.BaseActivity;

import org.androidannotations.annotations.EFragment;

import java.net.ConnectException;

@EFragment
public abstract class BaseFragment extends Fragment {
    protected final void snackBar(String text) {
        ((BaseActivity) getActivity()).snackBar(text);
    }

    protected final void snackBar(@StringRes int textId) {
        ((BaseActivity) getActivity()).snackBar(textId);
    }

    protected boolean checkNetwork(Throwable throwable) {
        if(throwable instanceof ConnectException) {
            snackBar(R.string.check_network);
            return true;
        }
        return false;
    }
}
