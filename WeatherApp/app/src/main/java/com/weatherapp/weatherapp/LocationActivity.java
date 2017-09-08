package com.weatherapp.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.weatherapp.weatherapp.connection.WeatherHttpClient;
import com.weatherapp.weatherapp.model.City;
import com.weatherapp.weatherapp.parser.JSONWeatherParser;

import net.danlew.android.joda.JodaTimeAndroid;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class LocationActivity extends Activity{

    private EditText editText;
    private ListView listView;
    private ArrayAdapter<City> cityArrayAdapter;
    private Context context;
    private ImageView imageView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        context = this;

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.weather2));

        // initialize Joda-Time lib
        JodaTimeAndroid.init(this);

        cityArrayAdapter = new ArrayAdapter<City>(this, android.R.layout.simple_list_item_1, new ArrayList<City>());

        editText = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.list_view);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    String pattern = editText.getEditableText().toString();
                    JSONLocationTask task = new JSONLocationTask();

                    // run location job
                    task.execute(pattern);
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, ScreenSlidePagerForecast.class);
                City city = (City) listView.getItemAtPosition(i);
                intent.putExtra("CITY_NAME", city.getName() + "," + city.getCountry());
                startActivity(intent);
            }
        });
    }

    private class JSONLocationTask extends AsyncTask<String, Void, List<City>> {

        @Override
        protected List<City> doInBackground(String... strings) {
            List<City> cityList = null;
            String data = (new WeatherHttpClient()).getCityList(strings[0]);
            try{
                cityList = JSONWeatherParser.getCityList(data);
            }catch (JSONException e){
                e.printStackTrace();
            }
            return cityList;
        }

        @Override
        protected void onPostExecute(List<City> cityList) {
            super.onPostExecute(cityList);

            cityArrayAdapter.clear();
            cityArrayAdapter.addAll(cityList);
            listView.setAdapter(cityArrayAdapter);
        }
    }
}
