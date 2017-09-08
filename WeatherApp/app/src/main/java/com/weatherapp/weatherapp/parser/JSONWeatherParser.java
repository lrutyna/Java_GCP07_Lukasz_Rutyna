package com.weatherapp.weatherapp.parser;

import android.util.Log;

import com.weatherapp.weatherapp.model.City;
import com.weatherapp.weatherapp.model.ForecastPerHour;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JSONWeatherParser {

    private DateTime dateChecker;

    public static List<City> getCityList(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray("list");

        List<City> cityList = new ArrayList<>();

        for(int i=0; i<jsonArray.length(); i++){
            JSONObject object = jsonArray.getJSONObject(i);
            String name = object.getString("name");
            String id = object.getString("id");

            JSONObject sys = object.getJSONObject("sys");
            String country = sys.getString("country");

            cityList.add(new City(id, name, country));
        }

        return cityList;
    }

    public  List getForecastWeather(String data) throws JSONException{
        List<ArrayList> forecastDaysList = new ArrayList<ArrayList>();

        try{
            JSONObject object = new JSONObject(data);
            JSONArray forecastDays = object.getJSONArray("list");

            // set date checker - first dateTime object from JSON
            // before using checkDateDifference method
            dateChecker = parseWeatherObject(forecastDays.getJSONObject(0)).getDate();

            // initialize list
            for(int i=0; i<5; i++){
                forecastDaysList.add(new ArrayList<ForecastPerHour>());
            }

            Log.d("SIZE:", String.valueOf(forecastDays.length()));

            // get hours/days
            for(int i=0; i<forecastDays.length(); i++){
                ForecastPerHour forecast = parseWeatherObject(forecastDays.getJSONObject(i));
                // set up weather object
                Log.d("LOG", forecast.toString());
                // check day and add to list
                switch (checkDateDifference(forecast)){
                    case 0:
                        forecastDaysList.get(0).add(forecast);
                        break;
                    case 1:
                        forecastDaysList.get(1).add(forecast);
                        break;
                    case 2:
                        forecastDaysList.get(2).add(forecast);
                        break;
                    case 3:
                        forecastDaysList.get(3).add(forecast);
                        break;
                    case 4:
                        forecastDaysList.get(4).add(forecast);
                        break;
                    case 5:
                        // never used but it stays if we want to have 6 days forecast
                        if(forecastDaysList.size() == 6){
                            forecastDaysList.get(5).add(forecast);
                        }
                        break;
                }
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return forecastDaysList;
    }

    // create forecastWeather object for one hour from JSON
    private ForecastPerHour parseWeatherObject(JSONObject object){
        ForecastPerHour forecast = new ForecastPerHour();
        try {
            JSONObject main = object.getJSONObject("main");
            JSONArray weatherArray = object.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);
            JSONObject wind = object.getJSONObject("wind");
            // get forecast info
            forecast.setTemperature(main.getDouble("temp"));
            forecast.setPressure(main.getDouble("pressure"));
            forecast.setHumidity(main.getInt("humidity"));
            // set first letter to uppercase
            String description = weather.getString("description").substring(0,1).toUpperCase() + weather.getString("description").substring(1);
            forecast.setDescription(description);
            forecast.setIcon(weather.getString("icon"));
            forecast.setWindSpeed(wind.getDouble("speed"));
            // get forecast date
            String datePattern = object.getString("dt_txt");
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            forecast.setDate(formatter.parseDateTime(datePattern));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return forecast;
    }

    private int checkDateDifference(ForecastPerHour weather){
        MutableDateTime dateTime = new MutableDateTime(DateTimeZone.forID("Europe/Warsaw"));
        DateTime today = new DateTime(DateTimeZone.forID("Europe/Warsaw"));

        // after 11 p.m. API returns forecast for next day but not for actual day
        // check if it is after today and push date to next day
        if(dateChecker != null){
            if(today.plusDays(1).toLocalDate().isEqual(dateChecker.toLocalDate())){
                dateTime.addDays(1);
            }
        }

        DateTime timeCounter = new DateTime(dateTime);

        return Days.daysBetween(timeCounter.withTimeAtStartOfDay(), weather.getDate().withTimeAtStartOfDay()).getDays();
    }
}