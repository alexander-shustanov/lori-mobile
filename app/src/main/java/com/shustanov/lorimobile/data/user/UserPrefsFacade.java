package com.shustanov.lorimobile.data.user;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EBean(scope = EBean.Scope.Singleton)
public class UserPrefsFacade {
    @Pref
    UserPrefs_ userPrefs;

    public String getUserId() {
        return userPrefs.userId().get();
    }

    public void setUserId(String userId) {
        userPrefs.userId().put(userId);
    }

    public void clear() {
        userPrefs.clear();
    }
}
