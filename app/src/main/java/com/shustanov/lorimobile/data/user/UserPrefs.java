package com.shustanov.lorimobile.data.user;


import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface UserPrefs {
    @DefaultString("")
    String userId();

    @DefaultString("admin")
    String userName();

    @DefaultString("admin")
    String pass();

    @DefaultBoolean(false)
    boolean authorised();
}
