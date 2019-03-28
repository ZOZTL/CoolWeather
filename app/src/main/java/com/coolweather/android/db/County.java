package com.coolweather.android.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created by zhouyang on 2019/3/28.
 */

public class County extends LitePalSupport {
    private int id;

    private String countName;

    private int cityId;

    private String weatherId;


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }


    public String getCountName()
    {
        return countName;
    }

    public void setCountName(String countName)
    {
        this.countName = countName;
    }


}
