package com.develandoo.task.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.develandoo.task.helpers.NetworkHelper;
import com.develandoo.task.models.User;
import com.develandoo.task.utlis.Constants;
import com.develandoo.task.utlis.RequestConnector;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<User>> usersLiveData;
    private Realm realm;
    private int page = 1;
    private NetworkHelper.RequestHandler requestHandler = new NetworkHelper.RequestHandler() {
        @Override
        public void onRequest(JSONObject response, Exception error) {
            if (error == null) {
                final List<User> usersFromNet = new ArrayList<>();
                try {
                    JSONArray responnseArray = response.getJSONArray(Constants.JsonData.RESULTS);
                    Gson gson = new Gson();
                    for (int i = 0; i < responnseArray.length(); i++) {
                        User user = gson.fromJson(responnseArray.getString(i), User.class);
                        user.setRealmId();
                        user.setPage(page);
                        usersFromNet.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                usersLiveData.setValue(usersFromNet);
                page++;
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.insert(usersFromNet);
                    }
                });
            }

            RequestConnector.getInstance().onResponse();
        }
    };

    public LiveData<List<User>> getUsersLiveData() {
        if (usersLiveData == null) {
            usersLiveData = new MutableLiveData<>();
            realm = Realm.getDefaultInstance();
            loadUsers();
        }
        return usersLiveData;
    }

    public void loadUsers() {
        RequestConnector.getInstance().onRequest();
        RealmResults<User> realmResults = realm.where(User.class).equalTo("page", page).findAll();
        if (!realmResults.isEmpty()) {
            List<User> usersFromDb = new ArrayList<>();
            for (int i = 0; i < realmResults.size(); i++) {
                User realmObject = realmResults.get(i);
                if (realmObject != null)
                    usersFromDb.add(realm.copyFromRealm(realmObject));
            }
            usersLiveData.setValue(usersFromDb);
            page++;
            RequestConnector.getInstance().onResponse();
        } else {
            loadUsersFromNetwork();
        }
    }

    private void loadUsersFromNetwork() {
        NetworkHelper.getInst().doRequest(page, requestHandler);

    }
}
