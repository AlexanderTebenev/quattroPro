package com.example.alextebenev.quattropro.retrofit.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * Created by alextebenev on 05.06.2016.
 */
@Getter
public class PlaceObject {

    @SerializedName("name")
    String name;
}
