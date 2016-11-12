package com.shustanov.lorimobile.ui.fragment;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.ui.activity.BaseActivity;
import com.shustanov.lorimobile.ui.activity.login.LoginActivity_;

import org.androidannotations.annotations.EFragment;

import java.net.ConnectException;

import retrofit2.adapter.rxjava.HttpException;

@EFragment
public abstract class BaseFragment extends Fragment {
    protected final void snackBar(String text) {
        ((BaseActivity) getActivity()).snackBar(text);
    }

    protected final void snackBar(@StringRes int textId) {
        ((BaseActivity) getActivity()).snackBar(textId);
    }

    protected boolean checkNetworkAndLogin(Throwable throwable) {
        if(throwable instanceof ConnectException) {
            snackBar(R.string.check_network);
            return true;
        } else if(throwable instanceof HttpException) {
            if(((HttpException) throwable).code() == 401) {
                snackBar(R.string.should_relogin);
                getActivity().finish();
                getActivity().startActivity(new Intent(getContext(), LoginActivity_.class));
                return true;
            }
        }
        return false;
    }

    protected void setupRefreshLayout(SwipeRefreshLayout refreshLayout) {
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
    }
}
