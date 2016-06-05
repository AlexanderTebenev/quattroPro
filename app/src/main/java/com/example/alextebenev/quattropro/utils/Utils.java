package com.example.alextebenev.quattropro.utils;

/**
 * Created by alextebenev on 24.05.2016.
 */
public class Utils {

    public final static String IMAGE_CACHE_FOLDER="/sdcard/quattroPro/";
    public final static String URL_WEATHER="http://api.openweathermap.org/data/2.5/";
    public final static String URL_GOOGLE_API="https://maps.googleapis.com/maps/api/place/";


    private static String apiKeyGoogle="AIzaSyDRPugSjQV01UeC7sJVWf0QV_0IMFD0PYc";
    private static String apiKey="537b840edb3991fb348bc1ea915f1f6f";

    public static String getApiKey() {
        return apiKey;
    }
    public static String getGoogleApiKey() {
        return apiKeyGoogle;
    }
}
