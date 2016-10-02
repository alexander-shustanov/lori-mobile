package com.shustanov.lorimobile.data;

import android.content.Context;

import com.shustanov.lorimobile.data.greendao.DaoMaster;
import com.shustanov.lorimobile.data.greendao.DaoSession;
import com.shustanov.lorimobile.data.greendao.TaskDao;
import com.shustanov.lorimobile.data.greendao.TimeEntryDao;
import com.shustanov.lorimobile.data.greendao.UserDao;

import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class DbHelper extends DaoMaster.OpenHelper {

    private DaoMaster daoMaster;
    private DaoSession session;

    public DbHelper(Context context) {
        super(context, "lori");
        daoMaster = new DaoMaster(getWritableDatabase());
        session = daoMaster.newSession();
    }

    public DaoSession getSession() {
        return session;
    }

    public TaskDao getTaskDao() {
        return session.getTaskDao();
    }

    public UserDao getUserDao() {
        return session.getUserDao();
    }

    public TimeEntryDao getTimeEntryDao() {
        return session.getTimeEntryDao();
    }
}
