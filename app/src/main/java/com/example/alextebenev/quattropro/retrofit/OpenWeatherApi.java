package com.example.alextebenev.quattropro.retrofit;

import com.example.alextebenev.quattropro.retrofit.entity.WeatherFiveDaysResponse;
import com.example.alextebenev.quattropro.retrofit.entity.WeatherListElement;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alextebenev on 06.05.2016.
 */
public interface OpenWeatherApi {

    @GET("weather?")
    Observable<WeatherListElement> getCurrentWeather(@Query("q") String city,
                                                     @Query("appid") String apiKey,
                                                     @Query("lang") String lang);

    @GET("forecast?")
    Observable<WeatherFiveDaysResponse> getWeatherFiveDays(@Query("q") String city,
                                                           @Query("appid") String apiKey,
                                                           @Query("lang") String lang );
}
