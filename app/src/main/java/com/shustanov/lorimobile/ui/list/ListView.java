package com.shustanov.lorimobile.ui.list;

import com.depthguru.mvp.api.View;

import java.util.List;

/**
 * ListView
 * </p>
 * alexander.shustanov on 10.11.16
 */
public interface ListView<T> extends View {
    void setItems(List<T> items);

    void refreshFailed(Throwable throwable);

    void stopRefresh();
}
