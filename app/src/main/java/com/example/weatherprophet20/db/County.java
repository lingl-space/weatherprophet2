package com.example.weatherprophet20.db;

import org.litepal.crud.DataSupport;

/**
 * Created by asus on 2019/6/24.
 */

public class County extends DataSupport {

    private int countyId;

    private String countyName;

    private String weatherId;

    private int cityId;

    public County(){

    }

    public void setCountyId(int countyId){
        this.countyId = countyId;
    }

    public int getCountyId(){
        return countyId;
    }

    public void setCountyName(String countyName){
        this.countyName = countyName;
    }

    public String getCountyName(){
        return countyName;
    }

    public void setWeatherId(String weatherId){
        this.weatherId = weatherId;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setCityId(int cityId){
        this.cityId = cityId;
    }

    public int getCityId(){
        return cityId;
    }

}
