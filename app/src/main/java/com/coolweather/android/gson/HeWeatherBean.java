package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author 周洋 zhouyang081
 * @date 2019-07-05 23:33
 * @desc
 */
public class HeWeatherBean {
  @SerializedName("status")
  public String statsu;
  @SerializedName("basic")
  public BasicBena basicInfo;
  @SerializedName("aqi")
  public AQIBean aqiInfo;
  @SerializedName("now")
  public NowBean nowInfo;
  @SerializedName("suggestion")
  public SuggestionBean suggestionInfo;
  @SerializedName("daily_forecast")
  public List<DaliyforecastBean> dailyForecastInfo;

  public class BasicBena {
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String cityId;
    @SerializedName("update")
    public UpdateBean update;

    public class UpdateBean
    {
      @SerializedName("loc")
      public String updataTime;
    }

  }

  public class AQIBean {
    @SerializedName("city")
    public CityBean city;

    public class CityBean
    {
      @SerializedName("aqi")
      public String api;

      @SerializedName("pm25")
      public String pm25;
    }
  }

  public class NowBean {
    @SerializedName("tmp")
    public String temperature;
    @SerializedName("cond")
    public CondBean cond;

    public class CondBean
    {
      @SerializedName("txt")
      public String txt;
    }
  }

  public class SuggestionBean {
    @SerializedName("comf")
    public COMFBean comfort;
    @SerializedName("cw")
    public CWBean carWash;
    @SerializedName("sport")
    public SPORTBean sport;

    public class COMFBean{

      @SerializedName("txt")
      public String txt;
    }
    public class CWBean{
      @SerializedName("txt")
      public String txt;
    }
    public class SPORTBean{
      @SerializedName("txt")
      public String txt;
    }
  }

  public class DaliyforecastBean {
    @SerializedName("date")
    public String date;
    @SerializedName("cond")
    public CondBean cond;
    @SerializedName("tmp")
    public TmpBean temperature;

    public class CondBean
    {
      @SerializedName("txt_d")
      public String txt;
    }
    public class TmpBean
    {
      @SerializedName("max")
      public String max;
      @SerializedName("min")
      public String min;
    }
  }
}
