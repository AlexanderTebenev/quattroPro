package com.example.alextebenev.quattropro.retrofit;

import com.example.alextebenev.quattropro.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alextebenev on 26.05.2016.
 */

public class RetrofitHolder {

    private static RetrofitHolder instance;

    RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.create();
    Gson gson=new GsonBuilder().create();
    private Retrofit retrofitWeather=new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxAdapter)
            .baseUrl(Utils.URL_WEATHER)
            .build();

    private Retrofit retrofitGoogleApi=new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxAdapter)
            .baseUrl(Utils.URL_GOOGLE_API)
            .build();

    private OpenWeatherApi openWeatherApi =retrofitWeather.create(OpenWeatherApi.class);
    private GooglePlaceApi googlePlaceApi =retrofitGoogleApi.create(GooglePlaceApi.class);

    public static synchronized RetrofitHolder getInstance() {

        if (instance == null) {
            instance = new RetrofitHolder();
        }
        return instance;
    }


    public OpenWeatherApi getOpenweatherMapInterface() {
        return openWeatherApi;
    }
    public GooglePlaceApi getGoogleApiInterface() {
        return googlePlaceApi;
    }
}
