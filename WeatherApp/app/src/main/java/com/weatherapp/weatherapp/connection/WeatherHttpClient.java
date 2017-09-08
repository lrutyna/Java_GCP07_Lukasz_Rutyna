package com.weatherapp.weatherapp.connection;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherHttpClient {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String API_KEY = "61cb6cb8541446675c4905ed308e82b1";
    private static String LOCATION_URL = "http://api.openweathermap.org/data/2.5/find?mode=json";
    private static String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?mode=json&lang=pl&units=metric&q=";

    public String getWeatherData(String location) {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {

            con = (HttpURLConnection) ( new URL(BASE_URL + location + "&appid=" + API_KEY + "&lang=pl")).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();

            //test
            Log.d("I", buffer.toString());

            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }

    public byte[] getImage(String code) {
        HttpURLConnection con = null ;
        InputStream is = null;
        try {
            con = (HttpURLConnection) ( new URL(IMG_URL + code + ".png")).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();

            Log.d("I", "OBRAZEK");
            Log.d("I", String.valueOf(con.getResponseCode()));
            Log.d("I", con.getRequestMethod());

            // Let's read the response
            is = con.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ( is.read(buffer) != -1)
                baos.write(buffer);


            return baos.toByteArray();
        }
        catch(Throwable t) {
            Log.d("E", "Error in connection");
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }

    public String getCityList(String pattern){
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try{
            connection = (HttpURLConnection) (new URL(LOCATION_URL + "&q=" + pattern.trim() + "&appid=" + API_KEY + "&lang=pl")).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            StringBuffer buffer = new StringBuffer();
            inputStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            return buffer.toString();

        }catch (Throwable t){
            t.printStackTrace();
        }finally {
           try{
               inputStream.close();
           }catch (Exception e){
               e.printStackTrace();
           }
            connection.disconnect();
        }

        return null;
    }

    public String getForecast(String city){
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            con = (HttpURLConnection) ( new URL(FORECAST_URL + city + "&appid=" + API_KEY )).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();

            // test print
            Log.d("FORECAST", buffer.toString());

            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;
    }
}
