package com.develandoo.task.ui.main.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.develandoo.task.ui.activities.BaseActivity;

public class BaseFragment extends Fragment {
    protected BaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }
}
