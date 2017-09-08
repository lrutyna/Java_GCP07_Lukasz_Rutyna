package com.weatherapp.weatherapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.weatherapp.weatherapp.connection.WeatherHttpClient;
import com.weatherapp.weatherapp.fragments.ForecastDayFragment;
import com.weatherapp.weatherapp.parser.JSONWeatherParser;

import org.json.JSONException;

import java.util.List;

public class ScreenSlidePagerForecast extends FragmentActivity{

    private ViewPager mPager;
    private PageAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_slide_forecast);

        // instantiate pageviewer and adapter
        mPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new PageAdapter(getSupportFragmentManager());

        // pass city name to task
        ForecastTask task = new ForecastTask();
        task.execute(getIntent().getStringExtra("CITY_NAME"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // adapter to viewpager
    private class PageAdapter extends FragmentStatePagerAdapter{

        private List forecastDaysList;

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        public List getForecastDaysList() {
            return forecastDaysList;
        }

        public void setForecastDaysList(List forecastDaysList) {
            this.forecastDaysList = forecastDaysList;
        }

        @Override
        public Fragment getItem(int position) {
            ForecastDayFragment fragment = null;
            switch (position){
                case 0:
                    fragment = ForecastDayFragment.newInstance((List) forecastDaysList.get(0));
                    break;
                case 1:
                    fragment = ForecastDayFragment.newInstance((List) forecastDaysList.get(1));
                    break;
                case 2:
                    fragment = ForecastDayFragment.newInstance((List) forecastDaysList.get(2));
                    break;
                case 3:
                    fragment = ForecastDayFragment.newInstance((List) forecastDaysList.get(3));
                    break;
                case 4:
                    fragment = ForecastDayFragment.newInstance((List) forecastDaysList.get(4));
                    break;
                case 5:
                    // not used but left in case if we want to have 6 days forecast
                    fragment = ForecastDayFragment.newInstance((List) forecastDaysList.get(5));
                    break;
            }
            return fragment;

        }

        @Override
        public int getCount() {
            return forecastDaysList.size();
        }

    }

    private class ForecastTask extends AsyncTask<String, Void, List>{

        @Override
        protected List doInBackground(String... params) {
            String data = (new WeatherHttpClient()).getForecast(params[0]);
            JSONWeatherParser parser = new JSONWeatherParser();
            try {
                return parser.getForecastWeather(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List list) {
            mAdapter.setForecastDaysList(list);
            mPager.setAdapter(mAdapter);
        }
    }
}
