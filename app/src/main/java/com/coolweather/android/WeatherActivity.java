package com.coolweather.android;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.coolweather.android.gson.HeWeatherBean;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author 周洋 zhouyang081
 * @date 2019-07-05 23:58
 * @desc
 */
public class WeatherActivity extends AppCompatActivity {
  final String hfKey = "69f6fe25e9704f86b1d0d9213a5a8ac7";

  private ScrollView weatherLayout;

  private TextView titleCity;//标题

  private TextView titleUpdateTime;//天气信息更新时间

  private TextView degreeText;//温度

  private TextView weatherInfoText;//天气信息

  private LinearLayout forecastLayout;//预报

  private TextView aqiText;//空气质量

  private TextView pm25Text;//pm2.5

  private TextView comfortText;//舒适度

  private TextView carWashText;//洗车指数

  private TextView sportText;//运动

  private ImageView bingBgImg;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_weather);
    //初始化控件
    weatherLayout = findViewById(R.id.weather_layput);
    titleCity = findViewById(R.id.title_city);
    titleUpdateTime = findViewById(R.id.title_update_time);
    degreeText = findViewById(R.id.tv_degree);
    weatherInfoText = findViewById(R.id.tv_weather_info);
    forecastLayout = findViewById(R.id.ll_forecast_layout);
    aqiText = findViewById(R.id.aqi_text);
    pm25Text = findViewById(R.id.pm25_text);
    comfortText = findViewById(R.id.comfort_text);
    carWashText = findViewById(R.id.car_wash_text);
    sportText = findViewById(R.id.sport_text);
    bingBgImg = findViewById(R.id.bing_bg_img);


    //获取天气数据
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    String weatherString = preferences.getString("weather",null);
    String bingImg = preferences.getString("bing_img",null);

    //加载背景图
    if(bingImg!=null){
      Glide.with(this).load(bingImg).into(bingBgImg);
    } else {
      loadBingImg();
    }

    String weatherId = getIntent().getStringExtra("weather_id");
    //缓存中有数据则读取缓存数据
    if (weatherString!=null){
      HeWeatherBean weatherBean = Utility.handleWeatherResponse(weatherString);

      if(weatherBean.basicInfo.cityId.equals(weatherId)||weatherId.equals("-1")) {
        showWeatherInfo(weatherBean);
      } else {
        weatherLayout.setVisibility(View.INVISIBLE);
        requsetWeather(weatherId);
      }

    } else {
        weatherLayout.setVisibility(View.INVISIBLE);
        requsetWeather(weatherId);
    }
  }

  /**
   * 根据天气id获取天气信息
   * @param weatherId
   */
  public void requsetWeather(final String weatherId){
    String weatherUrl = "http://guolin.tech/api/weather?cityid="+ weatherId +
        "&key=" + hfKey;
    HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              Toast.makeText(WeatherActivity.this,getString(R.string.getWeatherFailed)
                  ,Toast.LENGTH_SHORT).show();
            }
          });
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
          final String responseText = response.body().string();
          final HeWeatherBean weatherBean = Utility.handleWeatherResponse(responseText);
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              if(weatherBean!=null&&weatherBean.statsu.equals("ok")){
                SharedPreferences.Editor editor = PreferenceManager.
                    getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("weather",responseText);
                editor.apply();
                showWeatherInfo(weatherBean);
              } else{
                Toast.makeText(WeatherActivity.this,getString(R.string.getWeatherFailed)
                ,Toast.LENGTH_SHORT).show();
              }
            }
          });
      }
    });
    loadBingImg();
  }

  public void showWeatherInfo(HeWeatherBean weather)
  {
      String cityName = weather.basicInfo.cityName;
      String updateTime = weather.basicInfo.update.updataTime.split(" ")[1];
      String degree = weather.nowInfo.temperature + "°C";
      String weatherInfo = weather.nowInfo.cond.txt;

      titleCity.setText(cityName);
      titleUpdateTime.setText(updateTime);
      degreeText.setText(degree);
      weatherInfoText.setText(weatherInfo);

      forecastLayout.removeAllViews();

      //添加预报信息
      for(HeWeatherBean.DaliyforecastBean forecast : weather.dailyForecastInfo){
          View view = LayoutInflater.from(this).inflate(R.layout.ll_forecast_item,forecastLayout,false);
          TextView dateText = view.findViewById(R.id.date_text);
          TextView infoText = view.findViewById(R.id.info_text);
          TextView maxText = view.findViewById(R.id.max_text);
          TextView minText = view.findViewById(R.id.min_text);

          dateText.setText(forecast.date);
          infoText.setText(forecast.cond.txt);
          maxText.setText(forecast.temperature.max);
          minText.setText(forecast.temperature.min);

          forecastLayout.addView(view);
      }

      if(weather.aqiInfo!=null){
        aqiText.setText(weather.aqiInfo.city.api);
        pm25Text.setText(weather.aqiInfo.city.pm25);
      }

      comfortText.setText(getString(R.string.comport)+":"+weather.suggestionInfo.comfort.txt);
      carWashText.setText(getString(R.string.car_wash)+":"+weather.suggestionInfo.carWash.txt);
      sportText.setText(getString(R.string.sport)+":"+weather.suggestionInfo.sport.txt);

      weatherLayout.setVisibility(View.VISIBLE);
  }

  private void loadBingImg(){
    String weatherUrl = "http://guolin.tech/api/bing_pic";
    HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        e.printStackTrace();
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        final String bingImg = response.body().string();
        SharedPreferences.Editor editor = PreferenceManager.
            getDefaultSharedPreferences(WeatherActivity.this).edit();
        editor.putString("bing_img",bingImg);
        editor.apply();
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
              Glide.with(WeatherActivity.this).load(bingImg).into(bingBgImg);
          }
        });
      }
    });
  }
}
