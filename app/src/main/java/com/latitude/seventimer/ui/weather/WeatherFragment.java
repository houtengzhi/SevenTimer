package com.latitude.seventimer.ui.weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.latitude.seventimer.R;
import com.latitude.seventimer.base.BaseRVFragment;
import com.latitude.seventimer.model.database.WeatherLocation;
import com.latitude.seventimer.model.AstroWeatherCluster;
import com.latitude.seventimer.ui.about.AboutActivity;
import com.latitude.seventimer.ui.location.LocationActivity;
import com.latitude.seventimer.util.AppDefs;
import com.latitude.seventimer.util.L;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;

/**
 * Created by cloud on 2019/4/20.
 */
public class WeatherFragment extends BaseRVFragment<WeatherPresenter> implements WeatherContract.IView {
    private Toolbar mToolbar;
    private TextView mUpdateTimeTv;
    private RecyclerView mWeatherRecycler;
    private WeatherAdapter mWeatherAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private TextView mToolbarTitleTextView;
    private ProgressBar mProgressBar;

    private LocationManager locationManager;
    private String provider;
    private ActionListener mActionListener;

    private WeatherLocation mSelectedLocation;

    private static final String TAG = "WeatherFragment";

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
            mToolbar = view.findViewById(R.id.toolbar_weather);
//            mToolbar.setTitle("Hefei");
//            mToolbar.setSubtitle("23324jfjjf");
            activity.setSupportActionBar(mToolbar);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    switch (id) {
                        case R.id.action_about:
                            AboutActivity.Companion.actionStart(getActivity());
                            break;

                        case R.id.action_settings:
                            break;
                    }
                    return false;
                }
            });
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                setHasOptionsMenu(true);
            }
        }

        mToolbarTitleTextView = view.findViewById(R.id.tv_toolbar_title);
        mUpdateTimeTv = view.findViewById(R.id.tv_weather_update_time);
        mRefreshLayout = view.findViewById(R.id.refresh_layout_weather);
        mWeatherRecycler = view.findViewById(R.id.recycler_weather);
        mProgressBar = view.findViewById(R.id.progressbar_weather);
        mWeatherAdapter = new WeatherAdapter();
        mWeatherRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mWeatherRecycler.setAdapter(mWeatherAdapter);

        mToolbarTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(LocationActivity.getStartIntent(getActivity()),
                        AppDefs.REQUEST_CODE_GET_LOCATION);
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.fetchAstroWeather(mSelectedLocation.getLatitude(), mSelectedLocation.getLongitude());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.weather_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected void initDatas() {
        //获取当前位置
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        requestLocationPermissions();
    }

    private void getLocationProvider() {
        //获取所有可用的位置提供器
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        List<String> providerList = locationManager.getProviders(criteria,true);
        if (providerList == null || providerList.size() == 0) {
            //当没有可用的位置提供器时，弹出Toast
            Toast.makeText(getActivity(), R.string.tips, Toast.LENGTH_SHORT).show();
            return;
        } else if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            provider = providerList.get(0);
            L.d(TAG, "Available location provider: " + Arrays.toString(providerList.toArray()));
            Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestLocationPermissions() {
        mRxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            getLocationProvider();

                            @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(provider);
                            if (location == null) {
                                L.w(TAG, "Current location is null.");
                            } else {
                                setSelectedLocation(new WeatherLocation(1, (float) location.getLatitude(),
                                        (float) location.getLongitude()));
                            }
                            mPresenter.fetchAstroWeather((float) location.getLatitude(), (float) location.getLongitude());
                            mPresenter.fetchLocationInfo(mSelectedLocation);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AppDefs.REQUEST_CODE_GET_LOCATION:
                if (resultCode == RESULT_OK) {
                    WeatherLocation location = data.getParcelableExtra("selected_location");
                    refreshLocationInfo(location);
                    mPresenter.fetchAstroWeather(location.getLatitude(), location.getLongitude());
                }
                break;
            default:
                break;
        }
    }

    private void setSelectedLocation(WeatherLocation location) {
        mSelectedLocation = location;
        mPresenter.insertOrUpdateLocation(location);
    }

    @Override
    public void refreshLocationInfo(WeatherLocation address) {
        setSelectedLocation(address);
        mToolbarTitleTextView.setText(address.getAddress());
//        mToolbar.setSubtitle(getString(R.string.latitude_longitude, address.getLatitude(), address.getLongitude()));
    }

    @Override
    public void refreshWeather(AstroWeatherCluster cluster) {
        mRefreshLayout.setRefreshing(false);
        mWeatherAdapter.setData(cluster.getList());
        mWeatherAdapter.notifyDataSetChanged();
        mUpdateTimeTv.setText(getString(R.string.update_time_xx, cluster.getUpdateTime()));
    }

    @Override
    public void showProgressbar(boolean isShow) {
        mProgressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
