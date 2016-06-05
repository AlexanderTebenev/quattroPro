package com.example.alextebenev.quattropro.adapter;

import android.content.Context;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.alextebenev.quattropro.R;
import com.example.alextebenev.quattropro.retrofit.entity.WeatherListElement;
import com.example.alextebenev.quattropro.utils.WeatherParser;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.Getter;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by alextebenev on 24.05.2016.
 */
public class WeatherFiveDaysAdapter extends RecyclerView.Adapter<WeatherFiveDaysAdapter.ViewHolder> {

    private final static int LAYOUT = R.layout.item_weather_list;
    private final List<WeatherListElement> items = new ArrayList<>();
    private final LayoutInflater inflater;
    private static WeatherParser parser;
    private RecyclerView recyclerView;


    public WeatherFiveDaysAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        parser=new WeatherParser(context);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = inflater.inflate(LAYOUT, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WeatherListElement item = items.get(position);
        holder.setData(item);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @UiThread
    public void update(List<WeatherListElement> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.temp)
        TextView temp;
        @InjectView(R.id.pressure)
        TextView pressure;
        @InjectView(R.id.humidity)
        TextView humidity;
        @InjectView(R.id.descrition)
        TextView descrition;
        @InjectView(R.id.date)
        TextView date;

        public ViewHolder(View root) {
            super(root);
            ButterKnife.inject(this, root);
        }

        void setData(WeatherListElement weather) {
            parser.setWeather(weather);
            descrition.setText(parser.getDescription());
            temp.setText(parser.getTemp());
            pressure.setText(parser.getPresure());
            humidity.setText(parser.getHumidity());
            date.setText(parser.getDate());
        }
    }
}
