package com.weatherapp.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

public class ForecastPerHour implements Parcelable {

    private DateTime date;
    private String description;
    private double pressure;
    private double temperature;
    private int humidity;
    private double windSpeed;
    private String icon;

    public ForecastPerHour() {}

    protected ForecastPerHour(Parcel in) {
        description = in.readString();
        pressure = in.readDouble();
        temperature = in.readDouble();
        humidity = in.readInt();
        windSpeed = in.readDouble();
        icon = in.readString();
    }

    public static final Creator<ForecastPerHour> CREATOR = new Creator<ForecastPerHour>() {
        @Override
        public ForecastPerHour createFromParcel(Parcel in) {
            return new ForecastPerHour(in);
        }

        @Override
        public ForecastPerHour[] newArray(int size) {
            return new ForecastPerHour[size];
        }
    };

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "ForecastPerHour{" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", pressure=" + pressure +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", icon='" + icon + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeDouble(pressure);
        parcel.writeDouble(temperature);
        parcel.writeInt(humidity);
        parcel.writeDouble(windSpeed);
        parcel.writeString(icon);
    }
}
