package com.example.alextebenev.quattropro.utils;

import android.content.Context;

import com.example.alextebenev.quattropro.R;
import com.example.alextebenev.quattropro.retrofit.entity.WeatherListElement;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alextebenev on 05.06.2016.
 */
public class WeatherParser {

    private WeatherListElement weather;
    private Context ctx;

    public WeatherParser ( Context ctx) {
        this.ctx=ctx;
    }

    public void setWeather(WeatherListElement weather){
        this.weather=weather;
    }

    public String getTemp() {
        double tempKelvin = Double.parseDouble(weather.getMain().getTemp());
        double tempCelsius = tempKelvin - 273.15;
        int tempCelsiusInt = (int) Math.round(tempCelsius);

        StringBuffer builderTemp = new StringBuffer();
        builderTemp.append(ctx.getString(R.string.temp));
        builderTemp.append(" ");
        builderTemp.append(Integer.toString(tempCelsiusInt));
        builderTemp.append(" ");
        builderTemp.append("°C");
        return builderTemp.toString();
    }


    public String getPresure() {
        double presure = Double.parseDouble(weather.getMain().getPressure());
        double presureMMRT = presure * 0.75;
        int presureInt = (int) Math.round(presureMMRT);

        StringBuffer builderPresure = new StringBuffer();
        builderPresure.append(ctx.getString(R.string.pressure));
        builderPresure.append(" ");
        builderPresure.append(Integer.toString(presureInt));
        builderPresure.append(" ");
        builderPresure.append(ctx.getString(R.string.pressureUnits));
        return  builderPresure.toString();
    }


    public String getHumidity() {
        StringBuffer builderHumidity = new StringBuffer();
        builderHumidity.append(ctx.getString(R.string.humidity));
        builderHumidity.append(" ");
        builderHumidity.append(weather.getMain().getHumidity());
        builderHumidity.append(" ");
        builderHumidity.append("%");
        return  builderHumidity.toString();
    }

    public String getDescription() {
        return  weather.getWeatherList().get(0).getDescription();
    }

    public String getDate() {
        Format formatter = new SimpleDateFormat("dd MMMM HH:mm", new Locale("ru","RU"));
        String dateStr = formatter.format(new Date(weather.getDate()* 1000));
        return dateStr;
    }
}
