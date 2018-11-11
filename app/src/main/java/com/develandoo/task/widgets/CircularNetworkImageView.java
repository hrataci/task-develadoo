package com.develandoo.task.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;
import com.develandoo.task.utlis.BitmapUtils;


public class CircularNetworkImageView extends NetworkImageView {
    Context mContext;

    public CircularNetworkImageView(Context context) {
        super(context);
        mContext = context;
    }

    public CircularNetworkImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
    }

    public CircularNetworkImageView(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        if (bm == null) return;
        setImageDrawable(new BitmapDrawable(mContext.getResources(),
                BitmapUtils.getCircularBitmap(bm)));
    }


}