package com.example.weatherprophet20.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2019/6/25.
 */

public class Daily_Forecast {

    public String date;

    @SerializedName("cond")
    public More more;

    public class More {

        @SerializedName("txt_d")
        public String info;

    }

    @SerializedName("tmp")
    public Temperature temperature;

    public class Temperature {

        public String max;

        public String min;

    }

}
