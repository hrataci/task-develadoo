package com.develandoo.task.ui.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.develandoo.task.R;
import com.develandoo.task.ui.main.fragments.BaseFragment;
import com.develandoo.task.utlis.RequestConnector;
import com.develandoo.task.utlis.RequestListener;

public abstract class BaseActivity extends AppCompatActivity implements RequestListener {
    protected FragmentManager mFragmentManager;
    protected boolean hasTwoPlane;
    private Dialog inProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RequestConnector.getInstance().registerListener(this);
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestConnector.getInstance().removeListener(this);
    }

    public void replaceFragment(BaseFragment fragment, int containerId, boolean addToBackStack) {
        if (isFinishing())
            return;
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment);
        if (addToBackStack)
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        else {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public void onBackPressed() {
        if (!hasTwoPlane) {
            super.onBackPressed();
            enableHomeButton(false);
        } else {
            finish();
        }
    }

    public void initProgressBar() {
        inProgressBar = new Dialog(this, android.R.style.Theme_Translucent);
        View view = getLayoutInflater().inflate(R.layout.progress, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ProgressBar progressBar = view.findViewById(R.id.progress_bar);
            int colorCodeDark = ContextCompat.getColor(this, R.color.colorPrimary);
            progressBar.setIndeterminateTintList(ColorStateList.valueOf(colorCodeDark));
        }

        inProgressBar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        inProgressBar.setTitle(null);
        inProgressBar.setContentView(view);
        inProgressBar.setCancelable(true);
        inProgressBar.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    inProgressBar.dismiss();
                    onBackPressed();
                }
                return false;
            }
        });

    }

    private void showProgressDialog() {
        if (inProgressBar != null && !inProgressBar.isShowing()) {
            inProgressBar.show();
        }
    }

    private void hideProgressDialog() {
        if (inProgressBar != null && inProgressBar.isShowing()) {
            inProgressBar.dismiss();
        }
    }

    public void enableHomeButton(boolean enable) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
    }

    @Override
    public void onRequest() {
        showProgressDialog();
    }

    @Override
    public void onResponse() {
        hideProgressDialog();
    }

    public abstract void openUserPage(int userId);

}
