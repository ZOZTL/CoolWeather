package com.coolweather.android.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by zhouyang on 2019/3/28.
 */

public class HttpUtil {

    /**
     * 使用okhttp框架进行查询请求
     * @param address url地址
     * @param callback 回调
     */
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
