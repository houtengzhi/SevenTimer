package com.latitude.seventimer.ui.location;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.latitude.seventimer.R;
import com.latitude.seventimer.base.BaseRVFragment;
import com.latitude.seventimer.model.database.WeatherLocation;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by cloud on 2019/4/21.
 */
public class LocationSettingsFragment extends BaseRVFragment<LocationSettingsPresenter>
        implements LocationSettingsContract.IView  {

    public static LocationSettingsFragment newInstance() {
        Bundle args = new Bundle();
        LocationSettingsFragment fragment = new LocationSettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private SwipeRecyclerView mLocationRecyclerView;
    private LocationAdapter mLocationAdapter;
    private EditText mLocationEditText;

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

    }

    @Override
    protected void configViews() {
        View view = getParentView();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            Toolbar toolbar = view.findViewById(R.id.toolbar_location_settings);
            activity.setSupportActionBar(toolbar);
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                setHasOptionsMenu(false);
            }
        }

        mLocationEditText = view.findViewById(R.id.edit_text_address);

        mLocationRecyclerView = view.findViewById(R.id.recycler_location);
        mLocationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLocationAdapter = new LocationAdapter();
        mLocationRecyclerView.setAdapter(mLocationAdapter);
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

    }

    @Override
    protected void initDatas() {

    }
}
