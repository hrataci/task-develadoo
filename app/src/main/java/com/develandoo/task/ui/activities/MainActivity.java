package com.develandoo.task.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.develandoo.task.R;
import com.develandoo.task.ui.main.fragments.MainFragment;
import com.develandoo.task.ui.main.fragments.UserDetailedFragment;
import com.develandoo.task.utlis.RequestConnector;

public class MainActivity extends BaseActivity {
    private int detailedContainerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initProgressBar();
        RequestConnector.getInstance().registerListener(this);

        detailedContainerId = R.id.container;
        if (findViewById(R.id.containerDetail) != null) {
            hasTwoPlane = true;
            detailedContainerId = R.id.containerDetail;
        }
        if (savedInstanceState == null) {
            replaceFragment(MainFragment.newInstance(), R.id.container, false);
        }
    }


    @Override
    public void openUserPage(int userId) {
        enableHomeButton(!hasTwoPlane);
        replaceFragment(UserDetailedFragment.newInstance(userId),detailedContainerId, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
