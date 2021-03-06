package com.coolweather.android.util;

import android.text.TextUtils;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;

import com.coolweather.android.gson.HeWeatherBean;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhouyang on 2019/4/2.
 */

public class Utility {

    /**
     * 解析处理服务器返回的省数据
     * @param response 返回的数据
     * @return
     */
    public static boolean handleProvinceResponse(String response)
    {
        if(!TextUtils.isEmpty(response))
        {
            try
            {
                JSONArray allProvinces = new JSONArray(response);
                for (int i=0;i<allProvinces.length();i++)
                {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinveName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }

                return  true;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }


        }
        return false;
    }

    /**
     * 处理服务器返回的市数据
     * @param response  返回的数据
     * @param provinceId  所属的省id
     * @return
     */
    public static boolean handleCityResponse(String response,int provinceId)
    {
        if (!TextUtils.isEmpty(response))
        {
            try
            {
                JSONArray allCity = new JSONArray(response);
                for(int i=0;i<allCity.length();i++)
                {
                    JSONObject cityObject = allCity.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }

                return true;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return  false;
    }

    /**
     * 处理服务器返回的县数据
     * @param response 返回的数据
     * @param cityId 所在城市id
     * @return
     */
    public static boolean handleCountyResponse(String response,int cityId)
    {
        if (!TextUtils.isEmpty(response))
        {
            try
            {
                JSONArray allCounty = new JSONArray(response);
                for (int i=0;i<allCounty.length();i++)
                {
                    JSONObject countyObject = allCounty.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }

                return  true;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }


        }
        return false;
    }

    /**
     * 处理收到的天气json数据
     * @param response
     * @return HeWeatherBean
     */
    public static HeWeatherBean handleWeatherResponse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,HeWeatherBean.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
