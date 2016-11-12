package com.shustanov.lorimobile.ui.activity;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.ui.activity.login.LoginActivity_;

import org.androidannotations.annotations.EActivity;

import java.net.ConnectException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

@EActivity
public abstract class BaseActivity extends AppCompatActivity {
    private CompositeSubscription compositeSubscription;

    protected final void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected final void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    @Override
    protected void onStart() {
        super.onStart();
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeSubscription.unsubscribe();
        compositeSubscription = null;
    }

    public void snackBar(String text) {
        Snackbar.make(getSnackBarView(), text, Snackbar.LENGTH_LONG).show();
    }

    public void snackBar(@StringRes int textId) {
        Snackbar.make(getSnackBarView(), textId, Snackbar.LENGTH_LONG).show();
    }

    protected View getSnackBarView() {
        return findViewById(android.R.id.content);
    }

    protected boolean checkNetwork(Throwable throwable) {
        if(throwable instanceof ConnectException) {
            snackBar(R.string.check_network);
            return true;
        }
        return false;
    }

    protected boolean checkNetworkAndLogin(Throwable throwable) {
        if(throwable instanceof ConnectException) {
            snackBar(R.string.check_network);
            return true;
        } else if(throwable instanceof HttpException) {
            if(((HttpException) throwable).code() == 401) {
                snackBar(R.string.should_relogin);
                finish();
                startActivity(new Intent(this, LoginActivity_.class));
                return true;
            }
        }
        return false;
    }

    protected void setupRefreshLayout(SwipeRefreshLayout refreshLayout) {
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
    }
}
