package com.latitude.seventimer.ui.weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.latitude.seventimer.R;
import com.latitude.seventimer.base.BaseRVFragment;
import com.latitude.seventimer.model.WeatherLocation;
import com.latitude.seventimer.model.AstroWeatherCluster;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by cloud on 2019/4/20.
 */
public class WeatherFragment extends BaseRVFragment<WeatherPresenter> implements WeatherContract.IView {
    private RecyclerView mWeatherRecycler;
    private WeatherAdapter mWeatherAdapter;
    private SwipeRefreshLayout mRefreshLayout;

    private LocationManager locationManager;
    private String provider;
    private ActionListener mActionListener;

    private WeatherLocation mSelectedLocation;

    public interface ActionListener {
        void onBackClicked();
        void onSetLocation();
    }

    public void setOnActionListener(ActionListener listener) {
        mActionListener = listener;
    }

    public static WeatherFragment newInstance() {
        Bundle args = new Bundle();
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_weather;
    }

    @Override
    protected boolean isInflateLayout() {
        return true;
    }

    @Override
    protected void setupComponent() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void configViews() {
        View view = getParentView();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            Toolbar toolbar = view.findViewById(R.id.toolbar_weather);
            toolbar.setTitle("Hefei");
            toolbar.setSubtitle("23324jfjjf");
            activity.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mActionListener != null) {
                        mActionListener.onSetLocation();
                    }
                }
            });
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                setHasOptionsMenu(true);
            }
        }

        mRefreshLayout = view.findViewById(R.id.refresh_layout_weather);
        mWeatherRecycler = view.findViewById(R.id.recycler_weather);
        mWeatherAdapter = new WeatherAdapter();
        mWeatherRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mWeatherRecycler.setAdapter(mWeatherAdapter);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.fetchAstroWeather(mSelectedLocation.getLatitude(), mSelectedLocation.getLongitude());
            }
        });
    }

    @Override
    protected void initDatas() {
        //获取当前位置
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            //当没有可用的位置提供器时，弹出Toast
            Toast.makeText(getActivity(), R.string.tips, Toast.LENGTH_SHORT).show();
            return;
        }

        requestPermissions();
    }

    private void requestPermissions() {
        mRxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(provider);
                            if (mSelectedLocation == null) {
                                mSelectedLocation = new WeatherLocation((float) location.getLatitude(),
                                        (float) location.getLongitude());
                            } else {
                                mSelectedLocation.setLatitude((float) location.getLatitude());
                                mSelectedLocation.setLongitude((float) location.getLongitude());
                            }
                            mPresenter.fetchAstroWeather((float) location.getLatitude(), (float) location.getLongitude());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void refreshLocationInfo(WeatherLocation address) {

    }

    @Override
    public void refreshWeather(AstroWeatherCluster cluster) {
        mRefreshLayout.setRefreshing(false);
        mWeatherAdapter.setData(cluster.getList());
        mWeatherAdapter.notifyDataSetChanged();
    }
}
