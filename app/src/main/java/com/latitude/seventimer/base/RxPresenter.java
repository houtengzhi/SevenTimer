package com.latitude.seventimer.base;

import android.support.v4.util.ArrayMap;

import com.latitude.seventimer.util.L;

import java.util.Map;
import java.util.Set;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by yechy on 2017/3/15.
 */
public class RxPresenter<T extends BaseContract.IView> implements BaseContract.IPresenter<T> {
    private final static String TAG = "RxPresenter";

    protected T mView;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(T view) {
        L.d(TAG, "attachView()");
        this.mView = view;
    }

    @Override
    public void detachView() {
        L.d(TAG, "detachView()");
        this.mView = null;
        clearDisposable();
    }

    @Override
    public void cancelAllSubscribe() {
        clearDisposable();
    }

    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }
}
