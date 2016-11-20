package com.shustanov.lorimobile.ui.list;

import com.depthguru.mvp.annotations.EMvpView;
import com.depthguru.mvp.annotations.stretegy.Single;
import com.depthguru.mvp.annotations.stretegy.Skip;
import com.depthguru.mvp.api.View;

import java.util.List;

/**
 * ListView
 * </p>
 * alexander.shustanov on 10.11.16
 */
@EMvpView
public interface ListView<T> extends View {
    @Single
    void setItems(List<T> items);

    @Skip
    void refreshFailed(Throwable throwable);

    @Skip
    void stopRefresh();
}
