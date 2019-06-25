package com.example.weatherprophet20.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by asus on 2019/6/24.
 */

public class HttpUtil {                                 //与服务器进行交互

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

}
