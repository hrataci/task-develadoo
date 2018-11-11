package com.develandoo.task.utlis;

import java.util.ArrayList;
import java.util.List;

public class RequestConnector implements RequestListener{
    private List<RequestListener> listenerList = new ArrayList<>();
    private static final RequestConnector ourInstance = new RequestConnector();
    public static RequestConnector getInstance() {
        return ourInstance;
    }


    private RequestConnector() {
    }


    public void registerListener(RequestListener listener){
        listenerList.add(listener);
    }

    public void removeListener(RequestListener listener){
        listenerList.remove(listener);
    }


    @Override
    public void onRequest() {
        for(RequestListener l :listenerList){
            l.onRequest();
        }
    }



    @Override
    public void onResponse() {
        for(RequestListener l :listenerList){
            l.onResponse();
        }
    }
}
