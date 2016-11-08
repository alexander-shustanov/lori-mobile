package com.shustanov.lorimobile.data.user;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.api.sharedpreferences.StringPrefField;

@EBean(scope = EBean.Scope.Singleton)
public class UserPrefsFacade {
    @Pref
    UserPrefs_ userPrefs;

    public String getUserId() {
        return userPrefs.userId().get();
    }

    public String getUserName() {
        return userPrefs.userName().get();
    }

    public String getPass() {
        return userPrefs.pass().get();
    }

    public boolean isAuthorised() {
        return userPrefs.authorised().get();
    }

    public void setUserId(String userId) {
        userPrefs.userId().put(userId);
    }

    public void setUserName(String userName) {
        userPrefs.userName().put(userName);
    }

    public void setPass(String pass) {
        userPrefs.pass().put(pass);
    }

    public void setAuthorised(boolean authorised) {
        userPrefs.authorised().put(authorised);
    }

    public void clear() {
        userPrefs.clear();
    }
}
