package com.weatherapp.weatherapp.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.weatherapp.weatherapp.R;
import com.weatherapp.weatherapp.adapter.WeatherAdapter;
import com.weatherapp.weatherapp.model.ForecastPerHour;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ForecastDayFragment extends android.support.v4.app.Fragment {

    private List forecastList;
    private TextView textView;
    private ListView listView;

    public ForecastDayFragment(){}

    public static final ForecastDayFragment newInstance(List list){
        ForecastDayFragment f = new ForecastDayFragment();
        // set up list if not null
        if(list != null){
            Bundle bundle = new Bundle(1);
            bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
            f.setArguments(bundle);
        }
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = (View) inflater.inflate(R.layout.forecast_day, container, false);

        // get JSON data
        try{
            forecastList = getArguments().getParcelableArrayList("list");
            textView = (TextView) rootView.findViewById(R.id.week_day);

            // weekday
            Locale pl = new Locale("pl");
            DateTime date = ((ForecastPerHour) forecastList.get(0)).getDate();
            String weekday = date.dayOfWeek().getAsText(pl) + ", " + date.dayOfMonth().getAsText() + " " + date.monthOfYear().getAsText(pl);
            textView.setText(weekday.substring(0,1).toUpperCase() + weekday.substring(1));

            // set up listview
            listView = rootView.findViewById(R.id.forecast_list);

            // set up list adapter
            WeatherAdapter adapter = new WeatherAdapter(getActivity(), R.layout.row_layout, forecastList);
            listView.setAdapter(adapter);

        }catch (IndexOutOfBoundsException e){

            e.printStackTrace();
        }

        return rootView;
    }

}


