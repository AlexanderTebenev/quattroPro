package com.example.alextebenev.quattropro.retrofit.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;

/**
 * Created by alextebenev on 02.06.2016.
 */

@Getter
public class WeatherListElement implements Serializable {

    @SerializedName("main")
    Main main;

    @SerializedName("weather")
    List<Weather> weatherList;

    @SerializedName("dt")
    long date;
}
