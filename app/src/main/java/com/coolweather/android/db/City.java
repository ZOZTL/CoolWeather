package com.coolweather.android.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created by zhouyang on 2019/3/26.
 */

public class City extends LitePalSupport {
    private int id;//城市id

    private String cityName;//城市名称

    private int cityCode;//城市代号

    private int provinceId;//省份id

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCityName()
    {
        return  cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public int getCityCode()
    {
        return cityCode;
    }

    public void setCityCode(int CityCode)
    {
        this.cityCode = CityCode;
    }
    public int getProvinceId()
    {
        return provinceId;
    }

    public void setProvinceId(int provinceId)
    {
        this.provinceId = provinceId;
    }
}
