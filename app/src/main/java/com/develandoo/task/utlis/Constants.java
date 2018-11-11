package com.develandoo.task.utlis;

public interface Constants {
    interface Connection {
        String BASE_URl = "https://randomuser.me/api/";
        String PARAMS = "?results=10&seed=abc&";
        String URL = BASE_URl + PARAMS;
        String PAGE = "&page=";
    }



    interface JsonData {
        String RESULTS = "results";
    }


    interface Intent {
        String ID = "id";
    }
}
