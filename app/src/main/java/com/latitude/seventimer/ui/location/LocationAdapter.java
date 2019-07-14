package com.latitude.seventimer.ui.location;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.latitude.seventimer.R;
import com.latitude.seventimer.model.database.WeatherLocation;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by cloud on 2019/7/6.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private List<WeatherLocation> mDatas;

    public LocationAdapter() {
    }

    public LocationAdapter(List<WeatherLocation> datas) {
        mDatas = datas;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout_location, viewGroup, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder locationViewHolder, int i) {
        WeatherLocation location = mDatas.get(i);
        locationViewHolder.title.setText(location.getAddress());

    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        } else {
            return 0;
        }
    }

    public WeatherLocation getItem(int position) {
        if (mDatas != null && mDatas.size() > position) {
            return mDatas.get(position);
        }
        return null;
    }

    public void setData(List<WeatherLocation> datas) {
        if (mDatas == null) {
            mDatas = datas;
        } else {
            mDatas.clear();
            mDatas.addAll(datas);
        }
    }

    public void removeItem(int position) {
        if (mDatas != null && mDatas.size() > position && position > -1) {
            mDatas.remove(position);
        }
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView temp;
        TextView title;
        TextView subtitle;
        ImageView weather;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            temp = itemView.findViewById(R.id.tv_temperature);
            title = itemView.findViewById(R.id.tv_title);
            subtitle = itemView.findViewById(R.id.tv_subtitle);
            weather = itemView.findViewById(R.id.iv_weather);
        }
    }
}
