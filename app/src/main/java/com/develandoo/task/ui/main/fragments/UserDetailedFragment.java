package com.develandoo.task.ui.main.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.develandoo.task.R;
import com.develandoo.task.helpers.NetworkHelper;
import com.develandoo.task.models.User;
import com.develandoo.task.ui.main.UserDetailedViewModel;

import static com.develandoo.task.utlis.Constants.Intent.ID;

public class UserDetailedFragment extends BaseFragment {

    private NetworkImageView imageView;
    private TextView lblName, lblGender, lblAddress, lblUserName, lblEmail;

    private UserDetailedViewModel mViewModel;
    private int userId;


    public static BaseFragment newInstance(int userId) {
        Bundle args = new Bundle();
        args.putInt(ID, userId);
        BaseFragment fr = new UserDetailedFragment();
        fr.setArguments(args);
        return fr;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_detailed_fragment, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        userId = getArguments().getInt(ID);
        imageView = view.findViewById(R.id.imgUser);
        lblName = view.findViewById(R.id.lblNameValue);
        lblGender = view.findViewById(R.id.lblGenderValue);
        lblAddress = view.findViewById(R.id.lblAddressValue);
        lblUserName = view.findViewById(R.id.lblUserNameValue);
        lblEmail = view.findViewById(R.id.lblEmailValue);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserDetailedViewModel.class);
        mViewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                setData(user);
            }
        });
        mViewModel.getUser(userId);

    }

    private void setData(User user) {
        imageView.setImageUrl(user.getPicture().getLarge(), NetworkHelper.getInst().getImageLoader());
        lblName.setText(user.getName().toString());
        lblGender.setText(user.getGender());
        lblAddress.setText(user.getLocation().toString());
        lblUserName.setText(user.getLogin().getUsername());
        lblEmail.setText(user.getEmail());
    }

}
