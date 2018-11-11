package com.develandoo.task.ui.main.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.develandoo.task.R;
import com.develandoo.task.models.User;
import com.develandoo.task.ui.adapters.UsersAdapter;
import com.develandoo.task.ui.main.MainViewModel;
import com.develandoo.task.widgets.swipyrefreshlayout.SwipyRefreshLayout;
import com.develandoo.task.widgets.swipyrefreshlayout.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment {

    private MainViewModel mViewModel;
    private UsersAdapter adapter;

    public static BaseFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        init(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.getUsersLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                adapter.addUsers(users);
            }
        });
    }

    private void init(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerUsers);
        adapter = new UsersAdapter(activity,new ArrayList<User>());
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        SwipyRefreshLayout swipy = view.findViewById(R.id.swipyRefreshLayout);
        swipy.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                mViewModel.loadUsers();
            }
        });
    }

}
