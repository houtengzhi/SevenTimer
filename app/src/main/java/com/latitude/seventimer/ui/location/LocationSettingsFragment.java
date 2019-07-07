package com.latitude.seventimer.ui.location;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.latitude.seventimer.R;
import com.latitude.seventimer.base.BaseRVFragment;
import com.latitude.seventimer.model.AstroWeather;
import com.latitude.seventimer.model.database.WeatherLocation;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import static android.app.Activity.RESULT_OK;

/**
 * Created by cloud on 2019/4/21.
 */
public class LocationSettingsFragment extends BaseRVFragment<LocationSettingsPresenter>
        implements LocationSettingsContract.IView {

    public static LocationSettingsFragment newInstance() {
        Bundle args = new Bundle();
        LocationSettingsFragment fragment = new LocationSettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private SwipeRecyclerView mLocationRecyclerView;
    private LocationAdapter mLocationAdapter;
    private EditText mLocationEditText;

    private ListPopupWindow mPopupWindow;
    private ArrayAdapter mPopupAdapter;

    public void setOnActionListener(ActionListener listener) {
        this.mActionListener = listener;
    }

    interface ActionListener {
        void onBackCick();
    }

    private ActionListener mActionListener;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_location_settings;
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
            Toolbar toolbar = view.findViewById(R.id.toolbar_location_settings);
            activity.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                setHasOptionsMenu(false);
            }
        }

        mLocationEditText = view.findViewById(R.id.edit_text_address);
        mLocationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String city = "合肥";
                mPresenter.startSearchLocation(city, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLocationRecyclerView = view.findViewById(R.id.recycler_location);
        mLocationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLocationAdapter = new LocationAdapter();
        mLocationRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int adapterPosition) {
                WeatherLocation address = mLocationAdapter.getItem(adapterPosition);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("selected_address", address);
                intent.putExtras(bundle);
                getActivity().setResult(RESULT_OK, intent);
                //销毁当前活动
                getActivity().finish();
            }
        });
        mLocationRecyclerView.setAdapter(mLocationAdapter);
    }

    @Override
    protected void initDatas() {
        mPresenter.getAllLocations();
        mPresenter.registerSearchLocation();
    }

    @Override
    public void refreshSuggestionLocation(List<WeatherLocation> locationList) {
        showSuggestLocationPopup(mLocationEditText);
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupAdapter.clear();
            mPopupAdapter.addAll(locationList);
            mPopupAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void refreshLocationList(List<WeatherLocation> locationList) {
        mLocationAdapter.setData(locationList);
        mLocationAdapter.notifyDataSetChanged();
    }

    private void showSuggestLocationPopup(View anchorView) {
        if (mPopupWindow == null) {
            mPopupWindow = new ListPopupWindow(getActivity());
            mPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startWeatherProfile((WeatherLocation) mPopupAdapter.getItem(position));
                }
            });
            mPopupAdapter = new ArrayAdapter<WeatherLocation>(getActivity(), android.R.layout.simple_list_item_1);
            mPopupWindow.setAdapter(mPopupAdapter);
            mPopupWindow.setAnchorView(anchorView);
        }
        mPopupWindow.show();
    }

    private void startWeatherProfile(WeatherLocation location) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("selected_address", location);
        intent.putExtras(bundle);
        getActivity().setResult(RESULT_OK, intent);
        //销毁当前活动
        getActivity().finish();
    }
}
