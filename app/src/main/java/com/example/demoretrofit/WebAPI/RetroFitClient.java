package com.example.demoretrofit.WebAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {
    private static RetroFitClient instance = null;
    private RetroAPI myApi;

    private RetroFitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RetroAPI.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(RetroAPI.class);
    }

    public static synchronized RetroFitClient getInstance() {
        if (instance == null) {
            instance = new RetroFitClient();
        }
        return instance;
    }

    public RetroAPI getMyApi() {
        return myApi;
    }
}
