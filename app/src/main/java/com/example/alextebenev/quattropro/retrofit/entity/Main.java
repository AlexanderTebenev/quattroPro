package com.example.alextebenev.quattropro.retrofit.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;

/**
 * Created by alextebenev on 02.06.2016.
 */
@Getter
public class Main implements Serializable {

    @SerializedName("temp")
    String temp;

    @SerializedName("temp_max")
    String temp_max;

    @SerializedName("temp_min")
    String temp_min;

    @SerializedName("pressure")
    String pressure;

    @SerializedName("humidity")
    String humidity;
}
