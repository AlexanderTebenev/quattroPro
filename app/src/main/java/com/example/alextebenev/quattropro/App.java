package com.example.alextebenev.quattropro;

import android.app.Application;

import com.example.alextebenev.quattropro.dagger.components.ApiComponent;
import com.example.alextebenev.quattropro.dagger.components.DaggerApiComponent;
import com.example.alextebenev.quattropro.dagger.modules.GooglePlaceApiModule;
import com.example.alextebenev.quattropro.dagger.modules.OpenWeatherApiModule;

/**
 * Created by alextebenev on 16.08.2016.
 */

public class App extends Application {
    ApiComponent component;


    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApiComponent.builder()
                .openWeatherApiModule(new OpenWeatherApiModule())
                .googlePlaceApiModule(new GooglePlaceApiModule())
                .build();
    }

    public ApiComponent getComponent() {
        return component;
    }
}

