package com.latitude.seventimer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.latitude.seventimer.R;


public class YYCommonItemLayout extends ConstraintLayout {

    private View topBorder;
    private View bottomBorder;

    public YYCommonItemLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public YYCommonItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public YYCommonItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        if (getBackground() == null) {
            setBackgroundResource(R.drawable.mx_item_background);
        }

        int defaultBorderColor = context.getColor(R.color.mxGrey04);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_common_item, this);
        topBorder = view.findViewById(R.id.mxprofile_item_top_border);
        bottomBorder = view.findViewById(R.id.mxprofile_item_bottom_border);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.YYCommonItemLayout, defStyle, 0);
        if (a.hasValue(R.styleable.YYCommonItemLayout_mx_border_color)) {
            int borderColor = a.getColor(R.styleable.YYCommonItemLayout_mx_border_color, defaultBorderColor);
            topBorder.setBackgroundColor(borderColor);
            bottomBorder.setBackgroundColor(borderColor);
        }
        boolean showTopBorder = a.getBoolean(R.styleable.YYCommonItemLayout_mx_top_border, true);
        boolean showBottomBorder = a.getBoolean(R.styleable.YYCommonItemLayout_mx_bottom_border, true);
        topBorder.setVisibility(showTopBorder ? VISIBLE : GONE);
        bottomBorder.setVisibility(showBottomBorder ? VISIBLE : GONE);

        a.recycle();
    }

    public void showTopBorder(boolean showTopBorder) {
        if (showTopBorder != topBorder.isShown()) {
            topBorder.setVisibility(showTopBorder ? VISIBLE : GONE);
        }
    }

    public void showBottomBorder(boolean showBottomBorder) {
        if (showBottomBorder != bottomBorder.isShown()) {
            bottomBorder.setVisibility(showBottomBorder ? VISIBLE : GONE);
        }
    }
}
