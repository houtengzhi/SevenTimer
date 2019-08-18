package com.latitude.seventimer.ui.weather;

import com.latitude.seventimer.base.RxPresenter;

import com.latitude.seventimer.model.database.WeatherLocation;
import com.latitude.seventimer.model.AstroWeatherBinder;
import com.latitude.seventimer.model.IDataHelper;
import com.latitude.seventimer.util.L;

import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by cloud on 2019/4/20.
 */
public class WeatherPresenter extends RxPresenter<WeatherContract.IView>
        implements WeatherContract.IPresenter<WeatherContract.IView> {

    private static final String TAG = WeatherPresenter.class.getSimpleName();
    private IDataHelper mDataHelper;

    @Inject
    public WeatherPresenter(IDataHelper dataHelper) {
        this.mDataHelper = dataHelper;
    }

    @Override
    public void fetchLocationInfo(float latitude, float longitude) {
        Disposable disposable = mDataHelper.fetchLocationInfo(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherLocation>() {
                    @Override
                    public void accept(WeatherLocation address) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        addDisposable(disposable);
    }

    @Override
    public void fetchLocationInfo(WeatherLocation location) {
        Disposable disposable = mDataHelper.reverseGeoCode(location)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherLocation>() {
                    @Override
                    public void accept(WeatherLocation location) throws Exception {
                        mView.refreshLocationInfo(location);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        addDisposable(disposable);
    }

    @Override
    public void fetchAstroWeather(float latitude, float longitude, boolean showProgressbar) {
        Disposable disposable = mDataHelper.fetchAstroWeather(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        mView.showProgressbar(showProgressbar);
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.showProgressbar(false);
                    }
                })
                .subscribe(new Consumer<AstroWeatherBinder>() {
                    @Override
                    public void accept(AstroWeatherBinder astroWeatherCluster) throws Exception {
                        mView.refreshWeather(astroWeatherCluster);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
        addDisposable(disposable);
    }

    @Override
    public void insertLocation(WeatherLocation location) {
        L.d(TAG, "insertLocation()");
        Disposable disposable = mDataHelper.insertLocation(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        L.d(TAG, "insertLocation: accept:%d", aLong.longValue());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
        addDisposable(disposable);
    }

    @Override
    public void insertOrUpdateLocation(WeatherLocation location) {
        L.d(TAG, "insertOrUpdateLocation()");
        Disposable disposable = mDataHelper.insertOrUpdateLocation(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        L.d(TAG, "insertOrUpdateLocation: accept:%d", aLong.longValue());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
        addDisposable(disposable);
    }
}
