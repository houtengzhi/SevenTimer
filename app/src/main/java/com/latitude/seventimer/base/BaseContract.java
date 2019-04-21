package com.latitude.seventimer.base;

/**
 * Created by yechy on 2017/3/15.
 */
public interface BaseContract {

    interface IPresenter<T> {
        void attachView(T view);
        void detachView();
        void cancelAllSubscribe();
    }

    interface IView {
    }
}
