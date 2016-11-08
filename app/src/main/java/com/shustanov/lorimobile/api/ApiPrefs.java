package com.shustanov.lorimobile.api;

import com.shustanov.lorimobile.BuildConfig;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * ApiPrefs
 * </p>
 * alexander.shustanov on 04.11.16
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface ApiPrefs {
    @DefaultString(BuildConfig.DEFAULT_API_PATH)
    String endpoint();
}
