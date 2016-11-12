package com.shustanov.lorimobile.ui.list;

import com.depthguru.mvp.annotations.EPresenter;
import com.depthguru.mvp.api.Presenter;
import com.shustanov.lorimobile.data.EntityApi;
import com.shustanov.lorimobile.data.Repository;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * ListViewPresenter
 * </p>
 * alexander.shustanov on 10.11.16
 */
public abstract class ListViewPresenter<T, V extends ListView<T>> extends Presenter<V> {
    private CompositeSubscription compositeSubscription;

    private boolean clearPending;
    private List<T> items = new ArrayList<>();

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(repository().getAll().subscribe(this::onItemsReceived, this::onItemsGetError));
    }

    private void onItemsGetError(Throwable throwable) {
        getView().refreshFailed(throwable);
        getView().stopRefresh();
    }

    public void refresh() {
        repository().refresh();
        clearPending = true;
        compositeSubscription.add(
                repository()
                        .getAll()
                        .subscribe(this::onItemsReceived, this::onItemsGetError, getView()::stopRefresh)
        );
    }

    @Override
    protected void onViewDetached() {
        super.onViewDetached();
        compositeSubscription.unsubscribe();
    }

    private void onItemsReceived(List<T> items) {
        if (clearPending) {
            this.items.clear();
            clearPending = false;
        }
        getView().setItems(items);
    }

    protected abstract EntityApi<T, ?> api();

    protected abstract Repository<T> repository();
}
