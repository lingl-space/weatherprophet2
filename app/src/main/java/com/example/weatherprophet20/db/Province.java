package com.example.weatherprophet20.db;

import org.litepal.crud.DataSupport;

/**
 * Created by asus on 2019/6/24.
 */

public class Province extends DataSupport {

    private int provinceId;

    private int provinceCode;

    private String provinceName;

    public Province(){

    }

    public void setProvinceId(int provinceId){
        this.provinceId = provinceId;
    }

    public int getProvinceId(){
        return provinceId;
    }

    public void setProvinceCode(int provinceCode){
        this.provinceCode = provinceCode;
    }

    public int getProvinceCode(){
        return provinceCode;
    }

    public void setProvinceName(String provinceName){
        this.provinceName = provinceName;
    }

    public String getProvinceName(){
        return provinceName;
    }

}
