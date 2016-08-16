package com.example.alextebenev.quattropro.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alextebenev.quattropro.R;
import com.example.alextebenev.quattropro.adapter.WeatherFiveDaysAdapter;
import com.example.alextebenev.quattropro.retrofit.OpenWeatherApi;
import com.example.alextebenev.quattropro.retrofit.entity.WeatherFiveDaysResponse;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by alextebenev on 02.06.2016.
 */
public class FiveDaysWeatherFragment extends Fragment {

    @InjectView(R.id.weather_list)
    RecyclerView weatherList;
    @InjectView(R.id.cityNameHeader)
    TextView cityNameHeader;

    String city;
    WeatherFiveDaysResponse weatherFiveDaysResponse;
    private WeatherFiveDaysAdapter adapter;

    @Inject
    OpenWeatherApi openWeatherApi;

    private CompositeSubscription compositeSubscription  = new CompositeSubscription();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_fragment_second, container, false);
        ButterKnife.inject(this, root);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        weatherList.setLayoutManager(layout);
        weatherList.setAdapter(adapter);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new WeatherFiveDaysAdapter(getActivity());
    }


    public void getWeatherForFiveDays(String city, String apiKey, String lang) {
        this.city=city;
        Observable<WeatherFiveDaysResponse> call= openWeatherApi.getWeatherFiveDays(city, apiKey, lang);
        Subscription subscription = call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        response -> {
                            updateWeatherList(response);
                        },
                        Throwable::printStackTrace);
        compositeSubscription.add(subscription);
    }


    private void updateWeatherList(WeatherFiveDaysResponse wFiveDaysResponse) {
        weatherFiveDaysResponse=wFiveDaysResponse;
        cityNameHeader.setText(city);
        adapter.update(weatherFiveDaysResponse.getWeatherObjectsList());
    }


    @Override
    public void onDestroy() {
        compositeSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("city", city);
        outState.putSerializable("weatherFiveDaysResponse", weatherFiveDaysResponse);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            city=savedInstanceState.getString("city");
            cityNameHeader.setText(city);
            weatherFiveDaysResponse= (WeatherFiveDaysResponse) savedInstanceState.getSerializable("weatherFiveDaysResponse");
            if(weatherFiveDaysResponse!=null) {
               adapter.update(weatherFiveDaysResponse.getWeatherObjectsList());
            }
        }

        super.onViewStateRestored(savedInstanceState);
    }
}
