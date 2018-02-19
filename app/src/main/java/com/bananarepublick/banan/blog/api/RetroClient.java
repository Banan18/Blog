package com.bananarepublick.banan.blog.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Banan on 16.02.2018.
 */

public class RetroClient {

    private static final String ROOT_URL = "https://my-json-server.typicode.com/Banan18/JSON/";

    private static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService(){
        return getRetrofit().create(ApiService.class);
    }

}
