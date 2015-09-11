package com.latitude.seventimer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.latitude.seventimer.R;

/**
 * Created by yechy on 2015/9/7.
 */
public class OtherFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.other_fragment_layout, container, false);

        return view;
    }
}
