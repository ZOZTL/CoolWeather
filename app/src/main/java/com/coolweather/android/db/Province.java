package com.coolweather.android.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created by zhouyang on 2019/3/25.
 */

public class Province extends LitePalSupport {

    private int id;//省份id

    private String provinveName;//省份名称

    private int provinceCode;//省份代号

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getProvinveName()
    {
        return  provinveName;
    }

    public void setProvinveName(String provinveName)
    {
        this.provinveName = provinveName;
    }

    public int getProvinceCode()
    {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode)
    {
        this.provinceCode = provinceCode;
    }

}
