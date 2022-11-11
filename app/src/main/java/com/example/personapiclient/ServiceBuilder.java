package com.example.personapiclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {

    private static final String URL="http://10.131.204.249:8686/WebApiProject/api/";
    //private static final String URL="http://192.168.0.15:8686/WebApiProject/api/";
    private static Retrofit retrofit =
            new Retrofit.Builder().baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();



    public static <S> S buildService(Class<S> serviceType)
    {
        return retrofit.create(serviceType);
    }
}

