package com.shustanov.lorimobile.api;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.api.sharedpreferences.StringPrefField;

/**
 * ApiPrefsFacade
 * </p>
 * alexander.shustanov on 04.11.16
 */
@EBean(scope = EBean.Scope.Singleton)
public class ApiPrefsFacade {
    @Pref
    ApiPrefs_ apiPrefs;

    private String endpoint;

    @AfterInject
    void init() {
        endpoint = apiPrefs.endpoint().get();
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        apiPrefs.endpoint().put(endpoint);
    }
}
