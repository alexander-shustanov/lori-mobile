package com.shustanov.lorimobile.data.task;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;

import java.util.List;

public abstract class DataSource<Entity> {
    public void save(Entity entity) {
        getDao().save(entity);
    }

    public void delete(Entity entity) {
        getDao().delete(entity);
    }

    public List<Entity> getAll() {
        return getDao().loadAll();
    }

    public Entity getById(String id) {
        return getDao().queryBuilder().where(getIdProperty().eq(id)).build().list().get(0);
    }

    public void saveAll(List<Entity> entities) {
        getDao().saveInTx(entities);
    }

    public void clear() {
        getDao().deleteAll();
    }

    protected abstract Property getIdProperty();

    protected abstract AbstractDao<Entity, Void> getDao();
}
