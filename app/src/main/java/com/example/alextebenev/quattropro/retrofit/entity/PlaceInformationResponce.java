package com.example.alextebenev.quattropro.retrofit.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Created by alextebenev on 02.06.2016.
 */
@Getter
public class PlaceInformationResponce {

    @SerializedName("results")
    List<PictureInformationObject> pictureInformationObjects;

}
