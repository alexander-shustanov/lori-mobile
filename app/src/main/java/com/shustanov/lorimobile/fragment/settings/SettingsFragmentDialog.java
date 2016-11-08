package com.shustanov.lorimobile.fragment.settings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shustanov.lorimobile.R;
import com.shustanov.lorimobile.activity.login.LoginActivity;
import com.shustanov.lorimobile.activity.login.LoginActivity_;
import com.shustanov.lorimobile.api.ApiPrefsFacade;
import com.shustanov.lorimobile.databinding.FSettingsBinding;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * SettingsFragmentDialog
 * </p>
 * alexander.shustanov on 04.11.16
 */
@EFragment
public class SettingsFragmentDialog extends DialogFragment {
    private FSettingsBinding binding;

    @Bean
    ApiPrefsFacade apiPrefs;
    private SettingsVm vm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FSettingsBinding.inflate(inflater, container, true);
        return binding.getRoot();
    }

    @AfterViews
    void init() {
        vm = new SettingsVm(apiPrefs.getEndpoint());
        binding.setVm(vm);
    }

    @Click(R.id.apply)
    void apply() {
        String endpoint = vm.getEndpoint();
        if(!endpoint.endsWith("/")) {
            endpoint += "/";
        }
        apiPrefs.setEndpoint(endpoint);
        Intent mStartActivity = new Intent(getContext(), LoginActivity_.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(getContext(), mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, mPendingIntent);
        Runtime.getRuntime().exit(0);
    }

    @Click(R.id.cancel)
    void cancel() {
        dismiss();
    }
}
