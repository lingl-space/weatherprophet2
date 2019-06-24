package com.example.weatherprophet20.db;

import org.litepal.crud.DataSupport;

/**
 * Created by asus on 2019/6/24.
 */

public class City extends DataSupport {

    private int cityId;

    private int provinceId;

    private int cityCode;

    private String cityName;

    public City(){

    }

    public void setCityId(int cityId){
        this.cityId = cityId;
    }

    public int getCityId(){
        return cityId;
    }

    public void setProvinceId(int provinceId){
        this.provinceId = provinceId;
    }

    public int getProvinceId(){
        return provinceId;
    }

    public void setCityCode(int cityCode){
        this.cityCode = cityCode;
    }

    public int getCityCode(){
        return cityCode;
    }

    public void setCityName(String cityName){
        this.cityName = cityName;
    }

    public String getCityName(){
        return cityName;
    }

}
