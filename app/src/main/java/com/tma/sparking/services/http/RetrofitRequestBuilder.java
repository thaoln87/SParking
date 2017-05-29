package com.tma.sparking.services.http;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pkimhuy on 5/26/2017.
 */

class RetrofitRequestBuilder {
    private static final String BASE_URL = "http://api.thingspeak.com/";

    static <S> S createRequest(Class<S> requestClass, Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(requestClass);
    }
}
