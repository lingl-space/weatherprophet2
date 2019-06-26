package com.example.weatherprophet20.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2019/6/25.
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {

        @SerializedName("txt")
        public String info;

    }
}
