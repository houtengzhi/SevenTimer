package com.latitude.seventimer.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.latitude.seventimer.R;
import com.latitude.seventimer.model.AstroWeather;

import java.util.List;

/**
 * Created by yechy on 2015/9/7.
 */
public class AstroWeatherAdapter extends ArrayAdapter<AstroWeather> {
    private int resourceId;

    public AstroWeatherAdapter(Context context, int textViewResourceId, List<AstroWeather> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        AstroWeather astroWeather = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView) view.findViewById(R.id.date);
            viewHolder.hour = (TextView) view.findViewById(R.id.hour);
            viewHolder.astroCloud = (ImageView) view.findViewById(R.id.astro_cloud);
            viewHolder.astroSeeing= (ImageView) view.findViewById(R.id.astro_seeing);
            viewHolder.astroTransparency = (ImageView) view.findViewById(R.id.astro_transparency);
            viewHolder.temp = (TextView) view.findViewById(R.id.temp);
            viewHolder.astroRh = (ImageView) view.findViewById(R.id.astro_rh);
            viewHolder.astroUnstable = (ImageView) view.findViewById(R.id.astro_unstable);
            viewHolder.astroWind = (ImageView) view.findViewById(R.id.astro_wind);
            viewHolder.astroRainsnow = (ImageView) view.findViewById(R.id.astro_rainsnow);
            viewHolder.divider = (ImageView) view.findViewById(R.id.divider);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
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

        return view;
    }

    class ViewHolder {
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
    }
}
