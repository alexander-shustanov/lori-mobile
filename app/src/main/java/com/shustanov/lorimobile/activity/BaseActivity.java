package com.shustanov.lorimobile.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;

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
}
