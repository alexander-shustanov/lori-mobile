package com.shustanov.lorimobile.ui.list;

import com.depthguru.mvp.annotations.EPresenter;
import com.depthguru.mvp.api.Presenter;
import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * ListViewPresenter
 * </p>
 * alexander.shustanov on 10.11.16
 */
@EBean
public abstract class ListViewPresenter<T, V extends ListView<T>> extends Presenter<V> {
    private CompositeSubscription compositeSubscription;

    private boolean clearPending;
    private List<T> items = new ArrayList<>();
    private V view;

    @AfterInject
    protected void init() {
        compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(repository().getAll().subscribe(this::onItemsReceived, this::onItemsGetError));
        view = getView();
    }

    private void onItemsGetError(Throwable throwable) {
        view.refreshFailed(throwable);
        view.stopRefresh();
    }

    public void refresh() {
        repository().refresh();
        clearPending = true;
        compositeSubscription.add(
                repository()
                        .getAll()
                        .subscribe(this::onItemsReceived, this::onItemsGetError, view::stopRefresh)
        );
    }

    private void onItemsReceived(List<T> items) {
        if (clearPending) {
            this.items.clear();
            clearPending = false;
        }
        this.items.addAll(items);
        view.setItems(this.items);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }

    protected abstract EntityApi<T, ?> api();

    protected abstract Repository<T> repository();
}
