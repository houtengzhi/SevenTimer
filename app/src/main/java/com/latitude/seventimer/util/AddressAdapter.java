package com.latitude.seventimer.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.latitude.seventimer.R;
import com.latitude.seventimer.model.Address;

import java.util.List;

/**
 * Created by yechy on 2015/9/6.
 */
public class AddressAdapter extends ArrayAdapter<Address> {
    private int resourceId;

    public AddressAdapter(Context context, int textViewResourceId, List<Address> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Address address = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        } else {
            view = convertView;
        }
        ImageView weatherImage = (ImageView) view.findViewById(R.id.weather_image);
        TextView addressName = (TextView) view.findViewById(R.id.address_name);
        weatherImage.setImageResource(address.getImageId());
        addressName.setText(address.getAddress());
        return view;


    }
}
