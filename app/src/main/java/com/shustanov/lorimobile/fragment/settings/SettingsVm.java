package com.shustanov.lorimobile.fragment.settings;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.design.widget.TextInputLayout;

import com.shustanov.lorimobile.BR;
import com.shustanov.lorimobile.R;

/**
 * SettingsVm
 * </p>
 * alexander.shustanov on 04.11.16
 */
public class SettingsVm extends BaseObservable {
    private final String initialEndpoint;

    private String endpoint;

    public SettingsVm(String initialEndpoint) {
        this.initialEndpoint = initialEndpoint;
        this.endpoint = initialEndpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        if(!this.endpoint.equals(endpoint)) {
            this.endpoint = endpoint;
            notifyPropertyChanged(BR.edited);
            notifyPropertyChanged(BR.error);
        }
    }

    @Bindable
    public boolean isEdited() {
        return !initialEndpoint.equals(endpoint);
    }

    @Bindable
    public int getError() {
        if(isEdited()) {
            return R.string.restart_app_to_change_endpoint;
        }
        return -1;
    }

    @BindingAdapter("app:error")
    public static void bindError(TextInputLayout textInputLayout, int error) {
        if(error == -1) {
            textInputLayout.setErrorEnabled(false);
        } else {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(textInputLayout.getContext().getString(error));
        }
    }
}
