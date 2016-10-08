package com.shustanov.lorimobile.activity.login;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.Utilities;
import com.shustanov.lorimobile.activity.BaseActivity;
import com.shustanov.lorimobile.activity.main.MainActivity_;
import com.shustanov.lorimobile.api.InvalidLoginException;
import com.shustanov.lorimobile.api.LoginApi;
import com.shustanov.lorimobile.data.DbHelper;
import com.shustanov.lorimobile.data.user.User;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import retrofit2.adapter.rxjava.HttpException;

@EActivity(R.layout.a_login)
public class LoginActivity extends BaseActivity {

    @ViewById(R.id.login_text)
    protected EditText login;
    @ViewById(R.id.login_password)
    protected EditText password;
    @ViewById(R.id.login_progress_bar)
    protected ProgressBar loginProgress;
    @ViewById(R.id.sign_in)
    protected Button signInButton;

    @Bean
    protected LoginApi loginApi;
    @Bean
    protected DbHelper dbHelper;

    @Click(R.id.sign_in)
    protected void signIn() {
        showProgress();
        Utilities.hideKeyBoard(this);
        addSubscription(
                loginApi.
                        login(loginText(), passwordText()).
                        subscribe(
                                this::successLogin,
                                this::unsuccessLogin)
        );
    }

    private void successLogin(User user) {
        hideProgress();
        finish();
        MainActivity_.intent(this).start();
    }

    private void unsuccessLogin(Throwable throwable) {
        if (!checkNetwork(throwable)) {
            if (throwable instanceof HttpException && ((HttpException) throwable).code() == 401) {
                snackBar(R.string.invalid_credentials);
            } else {
                snackBar(R.string.something_went_wrong);
            }
        }
        hideProgress();
    }

    private void hideProgress() {
        loginProgress.setVisibility(View.GONE);
        signInButton.setVisibility(View.VISIBLE);
    }

    private void showProgress() {
        loginProgress.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.GONE);
    }

    @NonNull
    private String passwordText() {
        return password.getText().toString();
    }

    @NonNull
    private String loginText() {
        return this.login.getText().toString();
    }
}