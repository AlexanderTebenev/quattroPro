package com.example.alextebenev.quattropro.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alextebenev.quattropro.R;
import com.example.alextebenev.quattropro.retrofit.GooglePlaceApi;
import com.example.alextebenev.quattropro.retrofit.OpenWeatherApi;

import com.example.alextebenev.quattropro.retrofit.entity.PlaceInformationResponce;
import com.example.alextebenev.quattropro.retrofit.RetrofitHolder;

import com.example.alextebenev.quattropro.retrofit.entity.PlaceObjectsResponse;
import com.example.alextebenev.quattropro.retrofit.entity.WeatherListElement;
import com.example.alextebenev.quattropro.utils.Utils;
import com.example.alextebenev.quattropro.utils.WeatherParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by alextebenev on 02.06.2016.
 */
public class CurentWeatherFragment extends Fragment {

    @InjectView(R.id.roolLayout)
    RelativeLayout roolLayout;
    @InjectView(R.id.temp)
    TextView temp;
    @InjectView(R.id.pressure)
    TextView pressure;
    @InjectView(R.id.humidity)
    TextView humidity;
    @InjectView(R.id.descrition)
    TextView descrition;
    @InjectView(R.id.cityName)
    EditText cityName;
    @InjectView(R.id.cityNameTab1)
    TextView cityNameTab1;
    @InjectView(R.id.progress)
    ProgressBar progress;


    Bitmap bmp;
    String city;
    WeatherListElement weatherObject;


    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private OnFragmentInteractionListener mListener;
    private OpenWeatherApi openWeatherApi = RetrofitHolder.getInstance().getOpenweatherMapInterface();
    private GooglePlaceApi googlePlaceApi = RetrofitHolder.getInstance().getGoogleApiInterface();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_first, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setCity();
    }

    @OnClick(R.id.changeCity)
    public void changeCityClick() {
        setCity();
    }

    private void setCity() {
        if (city == null) {
            getLocation();
        } else {
            getWeatherData(cityName.getText().toString());
        }
    }

    public void getCurrentWeather(String city, String apiKey, String lang) {
        Observable<WeatherListElement> call = openWeatherApi.getCurrentWeather(city, apiKey, lang);
        Subscription subscription = call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        weather -> {
                            setWeatherData(weather);
                        },
                        Throwable::printStackTrace);
        compositeSubscription.add(subscription);
    }

    public void getCityByLocation(String location) {
        Observable<PlaceObjectsResponse> call = googlePlaceApi.getCityName(location, 1000, "cities","ru",Utils.getGoogleApiKey());
        Subscription subscription = call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                        subscribe(
                                city -> {
                                    getWeather(city);
                                },
                                Throwable::printStackTrace);
        compositeSubscription.add(subscription);
    }

    private void getWeather(PlaceObjectsResponse place) {
        getWeatherData(place.getPlaceObjects().get(0).getName());
    }

    public void getCityPhoto(String cityName) {
        File quattroProDirectory = new File(Utils.IMAGE_CACHE_FOLDER);
        File cityPhoto = new File(quattroProDirectory, getImageName());
        if (cityPhoto.exists()) {
            bmp = BitmapFactory.decodeFile(cityPhoto.getAbsolutePath());
            setBitmapToBackground();
            return;
        }

        progress.setVisibility(View.VISIBLE);
        Observable<PlaceInformationResponce> call = googlePlaceApi.getPlaceInformation(cityName, Utils.getGoogleApiKey());
        Subscription subscription = call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        place -> {
                            getBitmap(place);
                        },
                        Throwable::printStackTrace);
        compositeSubscription.add(subscription);
    }

    public void getBitmap(PlaceInformationResponce place) {
        String link = place.getPictureInformationObjects().get(0).getPhotoObjectList().get(0).getPhotoReferences();
        Observable<ResponseBody> call = googlePlaceApi.getCityPhoto("1000", "1000", link, Utils.getGoogleApiKey());
        Subscription subscription = call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        response -> {
                            bmp = BitmapFactory.decodeStream(response.byteStream());
                            setBitmapToBackground();
                            executeBitmapCache();
                        },
                        Throwable::printStackTrace);
        compositeSubscription.add(subscription);
    }


    private void executeBitmapCache() {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(cacheBitmap());
                subscriber.onCompleted();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                            Log.i("IMG_CACHED", result);
                        },
                        Throwable::printStackTrace);
        compositeSubscription.add(subscription);
    }

    private void setBitmapToBackground() {
        BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
        roolLayout.setBackgroundDrawable(ob);
        progress.setVisibility(View.GONE);
    }

    private String cacheBitmap() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);


        File quattroProDirectory = new File(Utils.IMAGE_CACHE_FOLDER);
        if (!quattroProDirectory.exists()) {
            quattroProDirectory.mkdirs();
        }

        File f = new File(quattroProDirectory, getImageName());
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    private void getLocation() {
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                if(city==null) {
                    StringBuffer locationBuffer = new StringBuffer();
                    locationBuffer.append(location.getLatitude());
                    locationBuffer.append(",");
                    locationBuffer.append(location.getLongitude());
                    getCityByLocation(locationBuffer.toString());
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    public interface OnFragmentInteractionListener {
        void onCityChanged(String city);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        compositeSubscription.unsubscribe();
        super.onDestroy();
    }

    private void getWeatherData(String city) {
        this.city = city;
        cityName.setText(city);
        cityNameTab1.setText(city);
        getCurrentWeather(city, Utils.getApiKey(), "ru");
        getCityPhoto(city);
        mListener.onCityChanged(city);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("city", city);
        outState.putParcelable("bitmap", bmp);
        outState.putSerializable("weatherObject", weatherObject);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            city = savedInstanceState.getString("city");
            bmp = (Bitmap) savedInstanceState.getParcelable("bitmap");
            weatherObject = (WeatherListElement) savedInstanceState.getSerializable("weatherObject");
            cityName.setText(city);
            cityNameTab1.setText(city);
            setBitmapToBackground();
            setWeatherData(weatherObject);
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
        super.onViewStateRestored(savedInstanceState);
    }

    private void setWeatherData(WeatherListElement weatherObject) {
        if(weatherObject==null){
            return;
        }
        this.weatherObject = weatherObject;
        WeatherParser parser=new WeatherParser(getActivity());
        parser.setWeather(weatherObject);
        descrition.setText(parser.getDescription());
        temp.setText(parser.getTemp());
        pressure.setText(parser.getPresure());
        humidity.setText(parser.getHumidity());
    }

    private String getImageName(){
        StringBuffer builderImageName = new StringBuffer();
        builderImageName.append(city);
        builderImageName.append(".jpg");
        return builderImageName.toString();
    }

}
