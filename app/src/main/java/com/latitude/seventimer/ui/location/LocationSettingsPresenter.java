package com.latitude.seventimer.ui.location;

import com.latitude.seventimer.base.RxPresenter;
import com.latitude.seventimer.model.IDataHelper;
import com.latitude.seventimer.model.database.WeatherLocation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by cloud on 2019/4/21.
 */
public class LocationSettingsPresenter extends RxPresenter<LocationSettingsContract.IView>
        implements LocationSettingsContract.IPresenter<LocationSettingsContract.IView>  {
    private IDataHelper mDataHelper;

    private PublishSubject<String[]> mSearchSubject;

    @Inject
    public LocationSettingsPresenter(IDataHelper dataHelper) {
        this.mDataHelper = dataHelper;
    }

    @Override
    public void getAllLocations() {
        mDataHelper.queryAllLocations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WeatherLocation>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<WeatherLocation> weatherLocations) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void insertLocation(WeatherLocation location) {

    }

    @Override
    public void deleteLocation(WeatherLocation location) {

    }

    @Override
    public void registerSearchLocation() {
        mSearchSubject = PublishSubject.create();

        Disposable disposable = mSearchSubject.debounce(200, TimeUnit.MILLISECONDS)
                .filter(new Predicate<String[]>() {
                    @Override
                    public boolean test(String[] s) throws Exception {
                        return s[1].length() > 0;
                    }
                })
                .switchMap(new Function<String[], ObservableSource<List<WeatherLocation>>>() {
                    @Override
                    public ObservableSource<List<WeatherLocation>> apply(String[] strings) throws Exception {
                        return mDataHelper.fetchSuggestionLocation(strings[0], strings[1]);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<WeatherLocation>>() {
                    @Override
                    public void accept(List<WeatherLocation> locationList) throws Exception {
                        mView.refreshSuggestionLocation(locationList);
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
    public void startSearchLocation(String city, String keyword) {
        if (mSearchSubject != null) {
            mSearchSubject.onNext(new String[]{city, keyword});
        }
    }

    @Override
    public void fetchSuggestionLocation(String city, String keyword) {
        Disposable disposable = mDataHelper.fetchSuggestionLocation(city, keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<WeatherLocation>>() {
                    @Override
                    public void accept(List<WeatherLocation> locationList) throws Exception {

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
