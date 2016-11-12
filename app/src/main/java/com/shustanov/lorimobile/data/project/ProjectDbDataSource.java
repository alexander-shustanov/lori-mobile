package com.shustanov.lorimobile.data.project;

import com.shustanov.lorimobile.data.DataSource;
import com.shustanov.lorimobile.data.DbHelper;
import com.shustanov.lorimobile.data.greendao.ProjectDao;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;

/**
 * ProjectDbDataSource
 * </p>
 * alexander.shustanov on 10.11.16
 */
@EBean(scope = EBean.Scope.Singleton)
public class ProjectDbDataSource extends DataSource<Project> {

    @Bean
    DbHelper dbHelper;

    @Override
    protected Property getIdProperty() {
        return ProjectDao.Properties.Id;
    }

    @Override
    protected AbstractDao<Project, Void> getDao() {
        return dbHelper.getProjectDao();
    }
}
