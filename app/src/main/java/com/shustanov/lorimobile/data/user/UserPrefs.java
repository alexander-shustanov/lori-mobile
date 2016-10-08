package com.shustanov.lorimobile.data.user;


import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface UserPrefs {
    @DefaultString("")
    String userId();
}
