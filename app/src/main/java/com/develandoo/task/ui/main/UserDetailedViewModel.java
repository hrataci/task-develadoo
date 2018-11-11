package com.develandoo.task.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.develandoo.task.models.User;

import io.realm.Realm;

public class UserDetailedViewModel extends ViewModel {
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void getUser(int userId) {
        Realm realm = Realm.getDefaultInstance();
        User realmUser = realm.where(User.class).equalTo("realmId", userId).findFirst();
        userLiveData.setValue(realm.copyFromRealm(realmUser));
    }

}
