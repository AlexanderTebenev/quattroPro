package com.example.alextebenev.quattropro.retrofit.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;

/**
 * Created by alextebenev on 03.06.2016.
 */
@Getter
public class Weather implements Serializable {

    @SerializedName("description")
    String description;

}
