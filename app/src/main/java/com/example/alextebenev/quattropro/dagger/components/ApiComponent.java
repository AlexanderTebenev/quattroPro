package com.example.alextebenev.quattropro.dagger.components;

import com.example.alextebenev.quattropro.MainActivity;
import com.example.alextebenev.quattropro.dagger.modules.GooglePlaceApiModule;
import com.example.alextebenev.quattropro.dagger.modules.OpenWeatherApiModule;
import com.example.alextebenev.quattropro.fragments.CurentWeatherFragment;
import com.example.alextebenev.quattropro.retrofit.GooglePlaceApi;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by alextebenev on 16.08.2016.
 */

@Singleton
@Component(modules = {OpenWeatherApiModule.class, GooglePlaceApiModule.class})

public interface ApiComponent {
    void inject(CurentWeatherFragment curentWeatherFragment);
}
