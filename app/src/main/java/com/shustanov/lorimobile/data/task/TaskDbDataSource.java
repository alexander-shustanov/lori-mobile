package com.shustanov.lorimobile.data.task;

import com.shustanov.lorimobile.data.DbHelper;
import com.shustanov.lorimobile.data.greendao.TaskDao;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;

@EBean(scope = EBean.Scope.Singleton)
class TaskDbDataSource extends DataSource<Task> {

    @Bean
    protected DbHelper dbHelper;

    private TaskDao taskDao;

    @AfterInject
    protected void init() {
        taskDao = dbHelper.getTaskDao();
    }

    @Override
    protected Property getIdProperty() {
        return TaskDao.Properties.Id;
    }

    @Override
    protected AbstractDao<Task, Void> getDao() {
        return taskDao;
    }
}
