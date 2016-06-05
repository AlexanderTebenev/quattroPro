package com.example.alextebenev.quattropro.retrofit;

import com.example.alextebenev.quattropro.retrofit.entity.PlaceObjectsResponse;
import com.example.alextebenev.quattropro.retrofit.entity.PlaceInformationResponce;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alextebenev on 06.05.2016.
 */
public interface GooglePlaceApi {



    @GET("textsearch/json?")
    Observable<PlaceInformationResponce> getPlaceInformation(@Query("query") String cityName,
                                                             @Query("key") String apiKey);


    @GET("photo?")
    Observable<ResponseBody> getCityPhoto(@Query("maxwidth") String maxwidth,
                                          @Query("minheight") String minheight,
                                          @Query("photoreference") String photoreference,
                                          @Query("key") String apiKey);


    @GET("nearbysearch/json?")
    Observable<PlaceObjectsResponse> getCityName(@Query("location") String location,
                                                    @Query("radius") long radius,
                                                    @Query("types") String types,
                                                    @Query("language") String language,
                                                    @Query("key") String apiKey);
}
