package com.shustanov.lorimobile.activity;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.shustanov.lorimobile.R;

import org.androidannotations.annotations.EActivity;

import java.net.ConnectException;

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
}
