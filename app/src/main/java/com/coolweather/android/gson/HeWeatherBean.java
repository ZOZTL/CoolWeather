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
    String cityName;
    @SerializedName("id")
    String cityId;
    @SerializedName("update")
    UpdateBean update;

    public class UpdateBean
    {
      @SerializedName("loc")
      String updataTime;
    }

  }

  public class AQIBean {
    @SerializedName("city")
    CityBean city;

    public class CityBean
    {
      @SerializedName("api")
      String api;

      @SerializedName("pm25")
      String pm25;
    }
  }

  public class NowBean {
    @SerializedName("tmp")
    String temperature;
    @SerializedName("cond")
    CondBean cond;

    public class CondBean
    {
      @SerializedName("txt")
      String txt;
    }
  }

  public class SuggestionBean {
    @SerializedName("comf")
    COMFBean comfort;
    @SerializedName("cw")
    CWBean carWash;
    @SerializedName("sport")
    SPORTBean sport;

    public class COMFBean{

      @SerializedName("txt")
      String txt;
    }
    public class CWBean{
      @SerializedName("txt")
      String txt;
    }
    public class SPORTBean{
      @SerializedName("txt")
      String txt;
    }
  }

  public class DaliyforecastBean {
    @SerializedName("date")
    String date;
    @SerializedName("cond")
    CondBean cond;
    @SerializedName("tmp")
    TmpBean temperature;

    public class CondBean
    {
      @SerializedName("txt")
      String txt;
    }
    public class TmpBean
    {
      @SerializedName("max")
      String max;
      @SerializedName("min")
      String min;
    }
  }
}
