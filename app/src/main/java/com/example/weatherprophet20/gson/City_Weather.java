package com.example.weatherprophet20.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus on 2019/6/25.
 */

public class City_Weather {

    public String status;

    public Basic basic;

    public Aqi aqi;

    public Now now;

    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Daily_Forecast> dailyForecastList;

}
