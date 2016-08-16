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
public class OpenWeatherApiModule {

        @Provides
        @Singleton
        public OpenWeatherApi provideGooglePlaceApi(){
            RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.create();
            Gson gson=new GsonBuilder().create();
             Retrofit retrofitWeather=new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(rxAdapter)
                    .baseUrl(Utils.URL_WEATHER)
                    .build();

            OpenWeatherApi openWeatherApi =retrofitWeather.create(OpenWeatherApi.class);
            return openWeatherApi;
        }
    }


