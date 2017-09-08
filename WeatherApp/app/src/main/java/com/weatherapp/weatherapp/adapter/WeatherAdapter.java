package com.weatherapp.weatherapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weatherapp.weatherapp.R;
import com.weatherapp.weatherapp.model.ForecastPerHour;

import java.util.List;

public class WeatherAdapter extends ArrayAdapter<ForecastPerHour>{

    private Context context;

    public WeatherAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ForecastPerHour> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_layout, null);
        }

        ForecastPerHour forecast = getItem(position);

        if(forecast != null){

            TextView hour = (TextView) convertView.findViewById(R.id.hour);
            TextView description = (TextView) convertView.findViewById(R.id.description);
            TextView temperature = (TextView) convertView.findViewById(R.id.temp);
            TextView pressure = (TextView) convertView.findViewById(R.id.pressure);
            TextView wind = (TextView) convertView.findViewById(R.id.wind);
            TextView humidity = (TextView) convertView.findViewById(R.id.humidity);
            ImageView weatherIcon = (ImageView) convertView.findViewById(R.id.weatherIcon);

            // set up forecast data
            hour.setText(String.valueOf(forecast.getDate().getHourOfDay() + ":00"));
            description.setText(forecast.getDescription());
            temperature.setText(String.valueOf(forecast.getTemperature()) + " °C");
            pressure.setText(String.valueOf(forecast.getPressure()) + " hPa");
            wind.setText(String.valueOf(forecast.getWindSpeed()) + " m/s");
            humidity.setText(String.valueOf(forecast.getHumidity()) + " %");
            // set up image icon
            String fileName = "r" + forecast.getIcon();
            weatherIcon.setImageDrawable(context.getResources().getDrawable(getResourceDrawableIdByName(fileName)));

        }
        return convertView;
    }

    private int getResourceDrawableIdByName(String name){
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
}
