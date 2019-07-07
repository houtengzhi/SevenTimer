package com.latitude.seventimer.ui.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.latitude.seventimer.R;
import com.latitude.seventimer.model.AstroWeather;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by cloud on 2019/4/20.
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<AstroWeather> mDatas;

    public WeatherAdapter() {
    }

    public WeatherAdapter(List<AstroWeather> datas) {
        mDatas = datas;
    }

    public void setData(List<AstroWeather> datas) {
        if (mDatas == null) {
            mDatas = datas;
        } else {
            mDatas.clear();
            mDatas.addAll(datas);
        }
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout_astro_weather, viewGroup, false);
        return new WeatherViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder viewHolder, int i) {
        AstroWeather astroWeather = mDatas.get(i);
        viewHolder.date.setText(astroWeather.getDate());
        viewHolder.hour.setText(String.valueOf(astroWeather.getHour()));
        viewHolder.astroCloud.setImageResource(astroWeather.getAstroCloudId());
        viewHolder.astroSeeing.setImageResource(astroWeather.getAstroSeeingId());
        viewHolder.astroTransparency.setImageResource(astroWeather.getAstroTransparencyId());
        viewHolder.temp.setText(String.valueOf(astroWeather.getTemp()));
        viewHolder.astroRainsnow.setImageResource(astroWeather.getAstroRainsnowId());
        viewHolder.astroRh.setImageResource(astroWeather.getAstroRhId());
        viewHolder.astroUnstable.setImageResource(astroWeather.getAstroUnstableId());
        viewHolder.astroWind.setImageResource(astroWeather.getAstroWindId());
        if (astroWeather.getIsShowDivider() == AstroWeather.NOSHOWDIVIDER) {
            viewHolder.divider.setVisibility(View.GONE);
        } else if (astroWeather.getIsShowDivider() == AstroWeather.SHOWDIVIDER) {
            viewHolder.divider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        } else {
            return 0;
        }
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView hour;
        ImageView astroCloud;
        ImageView astroSeeing;
        ImageView astroTransparency;
        TextView temp;
        ImageView astroRh;
        ImageView astroRainsnow;
        ImageView astroUnstable;
        ImageView astroWind;
        ImageView divider;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            hour = itemView.findViewById(R.id.hour);
            astroCloud = itemView.findViewById(R.id.astro_cloud);
            astroSeeing= itemView.findViewById(R.id.astro_seeing);
            astroTransparency = itemView.findViewById(R.id.astro_transparency);
            temp = itemView.findViewById(R.id.temp);
            astroRh = itemView.findViewById(R.id.astro_rh);
            astroUnstable = itemView.findViewById(R.id.astro_unstable);
            astroWind = itemView.findViewById(R.id.astro_wind);
            astroRainsnow = itemView.findViewById(R.id.astro_rainsnow);
            divider = itemView.findViewById(R.id.divider);
        }
    }
}
