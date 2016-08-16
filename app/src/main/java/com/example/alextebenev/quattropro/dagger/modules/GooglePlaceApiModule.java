package com.example.alextebenev.quattropro.dagger.modules;

import com.example.alextebenev.quattropro.retrofit.GooglePlaceApi;
import com.example.alextebenev.quattropro.retrofit.OpenWeatherApi;
import com.example.alextebenev.quattropro.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alextebenev on 16.08.2016.
 */

@Module
public class GooglePlaceApiModule {

    @Provides
    @Singleton
    public GooglePlaceApi provideGooglePlaceApi(){
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.create();
        Gson gson=new GsonBuilder().create();
        Retrofit retrofitWeather=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .baseUrl(Utils.URL_GOOGLE_API)
                .build();

        GooglePlaceApi googlePlaceApi =retrofitWeather.create(GooglePlaceApi.class);
        return googlePlaceApi;
    }
}
