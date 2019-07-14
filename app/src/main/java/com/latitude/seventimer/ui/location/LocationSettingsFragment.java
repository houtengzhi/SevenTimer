package com.latitude.seventimer.ui.location;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.PopupWindow;

import com.latitude.seventimer.R;
import com.latitude.seventimer.base.BaseRVFragment;
import com.latitude.seventimer.model.AstroWeather;
import com.latitude.seventimer.model.database.WeatherLocation;
import com.latitude.seventimer.util.DisplayUtil;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;

import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchUIUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private String mSelectedCity = "合肥";

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
                mPresenter.startSearchLocation(mSelectedCity, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLocationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable editable = mLocationEditText.getText();
                String keyword = editable != null ? editable.toString() : "";
                mPresenter.startSearchLocation(mSelectedCity, keyword);
            }
        });

        mLocationRecyclerView = view.findViewById(R.id.recycler_location);
        mLocationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLocationAdapter = new LocationAdapter();
//        mLocationRecyclerView.setItemViewSwipeEnabled(true);
        mLocationRecyclerView.setSwipeItemMenuEnabled(true);
        mLocationRecyclerView.setSwipeItemMenuEnabled(0, false);

        mLocationRecyclerView.setOnItemMoveListener(new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                return false;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
                int adapterPosition = srcHolder.getAdapterPosition();
                WeatherLocation location = mLocationAdapter.getItem(adapterPosition);
                mLocationAdapter.removeItem(adapterPosition);
                mLocationAdapter.notifyDataSetChanged();
                mPresenter.deleteLocation(location);
            }
        });
        mLocationRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int adapterPosition) {
                WeatherLocation location = mLocationAdapter.getItem(adapterPosition);
                startWeatherProfile(location);
            }
        });
        mLocationRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem item = new SwipeMenuItem(getContext());
                item.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                item.setWidth(DisplayUtil.dip2px(getContext(), 100));
                item.setBackgroundColor(Color.RED);
                item.setText(R.string.Delete);
                item.setTextSize(20);
                item.setTextColor(Color.WHITE);
                rightMenu.addMenuItem(item);
            }
        });
        mLocationRecyclerView.setOnItemMenuClickListener(new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition) {
                menuBridge.closeMenu();
                WeatherLocation location = mLocationAdapter.getItem(adapterPosition);
                int menuPos = menuBridge.getPosition();
                switch (menuPos) {
                    case 0:
                        mLocationAdapter.removeItem(adapterPosition);
                        mLocationAdapter.notifyDataSetChanged();
                        mPresenter.deleteLocation(location);
                        break;
                }
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
            mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setHeight(DisplayUtil.dip2px(getActivity(), 300));
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
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            mPopupWindow.show();
            mPopupWindow.getListView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        }
    }

    private void startWeatherProfile(WeatherLocation location) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.putExtra("selected_location", location);
        intent.putExtras(bundle);
        getActivity().setResult(RESULT_OK, intent);
        //销毁当前活动
        getActivity().finish();
    }
}
